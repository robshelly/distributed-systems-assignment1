package assignment;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class DatabaseReader implements ActionListener {
  
  // DB Info
  private final String userName = "root";
  private final String password = "";
  private final String serverName = "localhost";
  private final int portNumber = 3306;
  private final String dbName = "assignment1";
  private final String tableName = "Employee";

  // Required Global Vars
  private ResultSet resultSet = null;
  private Connection conn = null;

  // GUI Stuff
  private JFrame frame;
  private JTextArea response;
  private RowDisplay display;

  // TODO Delete this
  // Handy global vars for testing
  private int ssn = 999;
  private String name = "John Doe";
  private String address = "100 Second Street";
  private String bDate = "2017-09-23";
  private char sex = 'm';
  private int salary = 37000;
  private int worksFor = 32;
  private int manages = 33;
  private int supervises = 33;

  private String ssn2 = "Jane Doe";
  private String address2 = "200 First Street";
  private String bDate2 = "1998-08-23";
  private char sex2 = 'f';
  private int salary2 = 42000;
  private int worksFor2 = 55;
  private int manages2 = 56;
  private int supervises2 = 57;

  /**
   * Constructor for DatebaseCreator
   */
  public DatabaseReader() {
    makeFrame();
    frame.setVisible(true);
  }

  /**
   * Build the UI.
   */
  private void makeFrame() {

    frame = new JFrame(this.tableName);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    JPanel contentPane = (JPanel) frame.getContentPane();
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
    contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

    response = new JTextArea("Welcome");
    response.setRows(3);
    response.setLineWrap(true);
    response.setWrapStyleWord(true);
    response.setEditable(false);
    contentPane.add(response);
    
    
    display = new RowDisplay();
    contentPane.add(display);

    JPanel buttonPanel = new JPanel(new GridLayout(5, 2));
    // Top Row
    addButton(buttonPanel, "Create Table");
    addButton(buttonPanel, "Drop Table");

    addButton(buttonPanel, "Read Next");
    addButton(buttonPanel, "Read Previous");

    addButton(buttonPanel, "Populate");
    addButton(buttonPanel, "Insert");

    addButton(buttonPanel, "Update");
    addButton(buttonPanel, "Delete");
    
    addButton(buttonPanel, "Clear Fields");
    addButton(buttonPanel, "Exit");

    contentPane.add(buttonPanel);
    frame.pack();
  }

  /**
   * Add a button to the button panel.
   */
  private void addButton(Container panel, String buttonText) {
    JButton button = new JButton(buttonText);
    button.addActionListener(this);
    panel.add(button);
  }

  /**
   * Print a String to the response text area.
   * 
   * @param response The String to print.
   */
  private void printResponse(String response) {
    this.response.setText(response);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    if (e.getActionCommand().equals("Create Table")) {
      if (this.createTable(conn))
        this.response.setText("Table Created");

    } else if (e.getActionCommand().equals("Drop Table")) {
      if (this.dropTable(conn)) {
        this.printResponse("Table Dropped!");
        display.clearFields();
      }

    } else if (e.getActionCommand().equals("Read Next")) {
      if (this.getNextQuery(resultSet))
        this.printResponse("Read Successful");

    } else if (e.getActionCommand().equals("Read Previous")) {
      if (this.getPreviousQuery(resultSet))
        this.printResponse("Read Successful");
      
    } else if (e.getActionCommand().equals("Populate")) {
      if (this.populate()) {
        this.printResponse("Poulated Table");
        reset();
      }
      
    } else if (e.getActionCommand().equals("Insert")) {
      if (this.insert()) {
        this.printResponse("Row Inserted");
        reset();
      }
      
    } else if (e.getActionCommand().equals("Update")) {
      if (this.update()) {
        this.printResponse("Row Updated");
        reset();
      }
      
    } else if (e.getActionCommand().equals("Delete")) {
      if (this.delete()) {
        this.printResponse("Row Deleted");
        reset();
      }
      
    } else if (e.getActionCommand().equals("Clear Fields")) {
      display.clearFields();
    
    } else if (e.getActionCommand().equals("Exit")) {
      try {
        conn.close();
      } catch (SQLException err) {
        System.out.println("Failed to close connection! " + err.getMessage());
      }
      frame.dispose();
    }
  }

  /**
   * Get a new database connection
   * 
   * @return
   * @throws SQLException
   */
  public Connection getConnection() throws SQLException {
    Connection conn = null;
    Properties connectionProps = new Properties();
    connectionProps.put("user", this.userName);
    connectionProps.put("password", this.password);

    conn = DriverManager.getConnection("jdbc:mysql://" + this.serverName + ":" + this.portNumber + "/" + this.dbName,
        connectionProps);

    return conn;
  }
  

  /**
   * Run an SQl update against the Employee Table.
   * 
   * @param conn The connection to the Table.
   * @param command The query to run.
   * @return True if the update is successful, false otherwise.
   * @throws SQLException
   */
  public boolean executeUpdate(Connection conn, String command) throws SQLException {
    Statement stmt = null;
    try {
      stmt = conn.createStatement();
      stmt.executeUpdate(command); // This will throw a SQLException if it fails
      return true;
    } finally {
      // This will run whether we throw an exception or not
      if (stmt != null) {
        stmt.close();
      }
    }
  }

 
  /**
   * Run an SQL query against the Employee Table.
   * 
   * @param conn
   *          The connection to the Table.
   * @param command
   *          The query to run.
   * @return The result set from the query.
   * @throws SQLException
   */
  public ResultSet executeQuery(Connection conn, String command) {
    Statement stmt = null;
    ResultSet result = null;
    try {
      stmt = conn.createStatement();
      stmt.executeQuery(command); // This will throw a SQLException if it fails
      result = stmt.getResultSet();
    } catch (SQLException e) {
      this.printResponse("ERROR: Could not get result set! " + e.getMessage());
    }
    return result;
  }


  /**
   * Read the next row from a Result Set.
   * 
   * @param resultSet The Result Set to read from.
   * @return True if successful read, false otherwise.
   */
  public boolean getNextQuery(ResultSet resultSet) {
    if (resultSet == null) resultSet = getRows();
    try {
      resultSet.next();
      
      display.displayRow(
          resultSet.getInt("ssn"),
          resultSet.getString("name"),
          resultSet.getString("address"),
          resultSet.getString("bdate"),
          resultSet.getString("sex").charAt(0),
          resultSet.getInt("salary"),
          resultSet.getInt("works_for"),
          resultSet.getInt("manages"),
          resultSet.getInt("supervises"));
      return true;
    } catch (SQLException e) {
      this.printResponse("Error reading from database! " + e.getMessage());
      return false;
    }
  }


  /**
   * Read the previous row from a Result Set.
   * 
   * @param resultSet The Result Set to read from.
   * @return True if successful read, false otherwise.
   */
  public boolean getPreviousQuery(ResultSet resultSet) {
    if (resultSet == null) resultSet = getRows();
    try {
      resultSet.previous();
      
      display.displayRow(
          resultSet.getInt("ssn"),
          resultSet.getString("name"),
          resultSet.getString("address"),
          resultSet.getString("bdate"),
          resultSet.getString("sex").charAt(0),
          resultSet.getInt("salary"),
          resultSet.getInt("works_for"),
          resultSet.getInt("manages"),
          resultSet.getInt("supervises"));
      return true;
    } catch (SQLException e) {
      this.printResponse("Error reading from database! " + e.getMessage());
      return false;
    }
  }


  /**
   * Updates a row in the Employee table.
   * 
   * @return True if successfully inserted, false otherwise.
   */
  public boolean insert() {
    try {
      // Get values from fields
      int ssn = Integer.parseInt(display.getSsnField());
      String name = display.getNameField();
      String address = display.getAddressField();
      String bDate = display.getDobField();
      int salary = Integer.parseInt(display.getSalaryField());
      char sex = display.getSexField().charAt(0);
      int worksFor = Integer.parseInt(display.getWorksForField());
      int manages = Integer.parseInt(display.getManagesField());
      int supervises = Integer.parseInt(display.getSupervisesField());
            
      String insert = "INSERT INTO `Employee` VALUES (" + "'" + ssn + "', " + "'" + name + "', " + "'" + address + "', "
          + "'" + bDate + "', " + "'" + salary + "', " + "'" + sex + "', " + "'" + worksFor + "', " + "'" + manages
          + "', " + "'" + supervises + "')";
      
      this.executeUpdate(conn, insert);
      return true;
    } catch (NumberFormatException e1) {
      this.printResponse("Error reading input. " + e1.getMessage());
    } catch (SQLException e2) {
      this.printResponse("ERROR: Could not insert. " + e2.getMessage());
    }
    return false;
  }
  


  /**
   * Inserts a row into the Employee table.
   * 
   * @param conn The connection to the Table.
   * @param id The id to enter.
   * @param name The name to enter.
   * @param address The address to enter.
   * @param bDate The birthdate to enter.
   * @param salary The salary to enter.
   * @param sex The sex to enter.
   * @param worksFor
   * @param manages
   * @param supervises
   * @return True if successfully inserted, false otherwise.
   */
  public boolean update() {
    try {
       // Get values from fields
      int ssn = Integer.parseInt(display.getSsnField());
      String name = display.getNameField();
      String address = display.getAddressField();
      String bDate = display.getDobField();
      int salary = Integer.parseInt(display.getSalaryField());
      char sex = display.getSexField().charAt(0);
      int worksFor = Integer.parseInt(display.getWorksForField());
      int manages = Integer.parseInt(display.getManagesField());
      int supervises = Integer.parseInt(display.getSupervisesField());
      
      String update = "UPDATE `Employee` SET " + 
          "`NAME` = '" + name + "'," +
          "`ADDRESS` = '" + address + "'," +
          "`BDATE` = '" + bDate + "'," +
          "`SALARY` = '" + salary + "'," + 
          "`SEX` = '" + sex + "'," +
          "`WORKS_FOR` = '" + worksFor + "'," + 
          "`MANAGES` = '" + manages + "'," +
          "`SUPERVISES` = '" + supervises + "'" +
          "WHERE `Employee`.`SSN` = '" + ssn + "'";
      this.executeUpdate(conn, update);
      return true;
    } catch (NumberFormatException e1) {
      this.printResponse("Error reading input. " + e1.getMessage());
    } catch (SQLException e2) {
      this.printResponse("ERROR: Could not update. " + e2.getMessage());
    }
    return false;
  }
  
  /**
   * Delete a row from the Employee table.
   * 
   * @param id
   *          The id of the row to delete.
   * @return True if successfully deleted, false otherwise.
   */
  public boolean delete() {
    try {
      String delete = "DELETE FROM `Employee` WHERE `Employee`.`SSN` = '"
          + Integer.parseInt(display.getSsnField()) + "'";
      this.executeUpdate(conn, delete);
      return true;
    } catch (SQLException e) {
      this.printResponse("ERROR: Could not delete. " + e.getMessage());
      return false;
    }
  }
 
  /**
   * Create the Employee table.
   * 
   * @param conn
   *          The connection to the Table.
   * @return True is successfully create, false otherwise/ app.run();
   */
  public boolean createTable(Connection conn) {

    try {
      String create = "CREATE TABLE " + this.tableName + " ( " + "SSN INTEGER NOT NULL, " + "NAME varchar(80) NOT NULL, "
          + "ADDRESS varchar(80) NOT NULL, " + "BDATE DATE NOT NULL, " + "SALARY DECIMAL NOT NULL, "
          + "SEX CHAR NOT NULL, " + "WORKS_FOR INTEGER, " + "MANAGES INTEGER, " + "SUPERVISES INTEGER, "
          + "PRIMARY KEY (SSN))";

      this.executeUpdate(conn, create);
      return true;
    } catch (SQLException e) {
      this.printResponse("ERROR: Could not create the table. " + e.getMessage());
      return false;
    }
  }
  


  /**
   * Drop the Employee table.
   * 
   * @param conn
   *          The connection to the Table.
   * @return True if successfully dropped, false otherwise.
   */
  public boolean dropTable(Connection conn) {
    try {
      String dropString = "DROP TABLE " + this.tableName;
      this.executeUpdate(conn, dropString);
      return true;
    } catch (SQLException e) {
      this.printResponse("ERROR: Could not drop the table! " + e.getMessage());
      return false;
    }
  }
  
  
 
  /**
   * Add some entries to the Table (for testing purposes).
   * 
   * @return True if successfully populated, false otherwise.
   */
  private boolean populate() {
    String command1 = "INSERT INTO Employee "
        + "VALUES (1, 'Joe Soap', '123 Main Street', '2017-09-23', 40000, 'm', 12, 13, 14)";
    String command2 = "INSERT INTO Employee "
        + "VALUES (2, 'Jimmy Bloggs', '10 High Street', '2017-09-22', 35000, 'm', 22, 23, 24)";
    try {
      executeUpdate(conn, command1);
      executeUpdate(conn, command2);
      return true;
    } catch (SQLException e) {
      System.out.println("Error populating! " + e.getMessage());
      return false;
    }
  }
  


  /**
   * Get all rows in the table.
   * 
   * @return A Result Set containing all rows in the table.
   */
  private ResultSet getRows() {
    String readCommand = "SELECT * FROM Employee";
    return executeQuery(conn, readCommand);
  }
  
 
  /**
   * Clears the display and gets the latest result set.
   */
  private void reset() {
    display.clearFields();
    resultSet=getRows();
  }
  
  /**

   * Connect to MySQL and do some stuff.
   */
  public void run() {

    // Connect to MySQL
    try {
      conn = this.getConnection();
      System.out.println("Connected to database");
    } catch (SQLException e) {
      System.out.println("Error reading from database! " + e.getMessage());
      e.printStackTrace();
      return;
    }

    // Initialise ResultSet
    resultSet = getRows();
  }

 
  /**
   * Connect to the Table and do some stuff
   */
  public static void main(String[] args) {
    DatabaseReader app = new DatabaseReader();
    app.run();
  }
}