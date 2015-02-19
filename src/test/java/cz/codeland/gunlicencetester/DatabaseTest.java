package cz.codeland.gunlicencetester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;

public class DatabaseTest
{

  private Database database;

  @Before
  public void setUp() throws Exception
  {
    database = new Database();
  }

  @After
  public void tearDown() throws Exception
  {
    database.close();
  }

  @Test
  public void generateDatabase_Scenario_ExpectedBehavior() throws Exception
  {
    // When
    database.generateDatabase();
    // Then
    ResultSet rs = database.getConnection().createStatement().executeQuery("SELECT * FROM question");
    while (rs.next()) {
      System.out.println(rs.getInt("id") + ") " + rs.getString("question"));
    }
  }
}
