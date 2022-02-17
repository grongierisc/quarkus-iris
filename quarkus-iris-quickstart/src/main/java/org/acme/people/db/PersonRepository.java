package org.acme.people.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;

import org.acme.core.db.PersistenceException;
import org.acme.people.model.Person;

@ApplicationScoped
// @Singleton
public class PersonRepository {

  private static final String FIND_ALL = "SELECT * FROM people";
  private static final String FIND_ALL_BY_ID = "SELECT * FROM people WHERE id = ?";
  private static final String INSERT = "INSERT INTO people (id, name, age) VALUES (?, ?, ?)";
  private static final String UPDATE = "UPDATE people SET name = ?, age = ? WHERE id = ?";
  private static final String DELETE = "DELETE FROM people WHERE id = ?";
  private static final String MAXID = "SELECT MAX(id) as id FROM people";

  private final DataSource dataSource;

  public PersonRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public List<Person> findAll() {
    List<Person> result = new ArrayList<>();
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_ALL);
        ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        result.add(
            new Person(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("age")));
      }
    } catch (SQLException e) {
      throw new PersistenceException("boom", e.getCause());
    }
    return result;
  }

  public Person findById(int id) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_ID)) {
      statement.setObject(1, id);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return new Person(
              resultSet.getInt("id"),
              resultSet.getString("name"),
              resultSet.getInt("age"));
        }
      }
    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
    return null;
  }

  public Person insert(Person person) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(INSERT)) {
      statement.setInt(1, person.getId());
      statement.setString(2, person.getName());
      statement.setInt(3, person.getAge());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
    return person;
  }

  public Person update(Person person) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE)) {
      statement.setString(1, person.getName());
      statement.setInt(2, person.getAge());
      statement.setInt(3, person.getId());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
    return person;
  }

  public boolean deleteById(int id) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE)) {
      statement.setInt(1, id);
      return statement.executeUpdate() == 1;
    } catch (SQLException e) {
      throw new PersistenceException(e);
    }
  }

  public int maxId() {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(MAXID)) {
          try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
              return resultSet.getInt("id");
            }
          }
        } catch (SQLException e) {
          throw new PersistenceException(e);
        }
    return 0;
  }
  
}
