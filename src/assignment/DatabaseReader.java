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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

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

    response = new JTextArea("Welcome!");
    response.setRows(3);
    response.setLineWrap(true);
    response.setWrapStyleWord(true);
    response.setEditable(false);
    
    JScrollPane scroll = new JScrollPane(response);
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    contentPane.add(scroll);
    
    display = new RowDisplay();
    contentPane.add(display);

    JPanel buttonPanel = new JPanel(new GridLayout(5, 2));
    // Top Row
    addButton(buttonPanel, "Create Tables");
    addButton(buttonPanel, "Drop Tables");

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
    this.response.append("\n" + response);
    this.response .setCaretPosition(this.response.getDocument().getLength());

  }

  @Override
  public void actionPerformed(ActionEvent e) {

    if (e.getActionCommand().equals("Create Tables")) {
      if (this.createTableEmployee(conn))
        this.printResponse("Table Created: " + this.tableName);
      if (this.createTableDepartment(conn))
      	this.printResponse("Table Created: Department");
      if (this.createTableProject(conn))
      	this.printResponse("Table Created: Project");

    } else if (e.getActionCommand().equals("Drop Tables")) {
      if (this.dropTables(conn)) {
        this.printResponse("Tables Dropped!");
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
        this.printResponse("Poulated Tables");
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
      this.printResponse("Error: Could not get result set! " + e.getMessage());
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
            
      String insert = "INSERT INTO `" + this.tableName + "` VALUES (" + "'" + ssn + "', " + "'" + name + "', " + "'" + address + "', "
          + "'" + bDate + "', " + "'" + salary + "', " + "'" + sex + "', " + "'" + worksFor + "', " + "'" + manages
          + "', " + "'" + supervises + "')";
      
      this.executeUpdate(conn, insert);
      return true;
    } catch (NumberFormatException e1) {
      this.printResponse("Error reading input. " + e1.getMessage());
    } catch (SQLException e2) {
      this.printResponse("Error: Could not insert. " + e2.getMessage());
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
      
      String update = "UPDATE `" + this.tableName + "` SET " + 
          "`NAME` = '" + name + "'," +
          "`ADDRESS` = '" + address + "'," +
          "`BDATE` = '" + bDate + "'," +
          "`SALARY` = '" + salary + "'," + 
          "`SEX` = '" + sex + "'," +
          "`WORKS_FOR` = '" + worksFor + "'," + 
          "`MANAGES` = '" + manages + "'," +
          "`SUPERVISES` = '" + supervises + "'" +
          "WHERE `" + this.tableName + "`.`SSN` = '" + ssn + "'";
      this.executeUpdate(conn, update);
      return true;
    } catch (NumberFormatException e1) {
      this.printResponse("Error reading input. " + e1.getMessage());
    } catch (SQLException e2) {
      this.printResponse("Error: Could not update. " + e2.getMessage());
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
      String delete = "DELETE FROM `" + this.tableName + "` WHERE `" + this.tableName + "`.`SSN` = '"
          + Integer.parseInt(display.getSsnField()) + "'";
      this.executeUpdate(conn, delete);
      return true;
    } catch (SQLException e) {
      this.printResponse("Error: Could not delete. " + e.getMessage());
      return false;
    }
  }
 
  /**
   * Create the Employee table.
   * 
   * @param conn The connection to the database.
   * @return True is successfully create, false otherwise/ app.run();
   */
  public boolean createTableEmployee(Connection conn) {

    try {
      String create = "CREATE TABLE " + this.tableName + " ( " + 
      		"SSN INTEGER NOT NULL, " +
      		"NAME varchar(80) NOT NULL, " +
      		"ADDRESS varchar(80) NOT NULL, " +
      		"BDATE DATE NOT NULL, " +
      		"SALARY DECIMAL NOT NULL, " +
          "SEX CHAR NOT NULL, " +
      		"WORKS_FOR INTEGER, " +
          "MANAGES INTEGER, " +
      		"SUPERVISES INTEGER, " +
          "PRIMARY KEY (SSN))";

      this.executeUpdate(conn, create);
      return true;
    } catch (SQLException e) {
      this.printResponse("Error: Could not create the table:  " + this.tableName + ". "+ e.getMessage());
      return false;
    }
  }
  
  /**
   * Create the Department table.
   * 
   * @param conn The connection to the database.
   * @return True is successfully create, false otherwise/ app.run();
   */
  public boolean createTableDepartment(Connection conn) {
  	
    try {
      String create = "CREATE TABLE Department ( " +
      		"NAME VARCHAR(100) NOT NULL, " +
      		"NUMBER INTEGER NOT NULL, " +
          "LOCATIONS VARCHAR(100) NOT NULL, " +
          "PRIMARY KEY (NUMBER))";

      this.executeUpdate(conn, create);
      return true;
    } catch (SQLException e) {
      this.printResponse("Error: Could not create the table: Department " + e.getMessage());
      return false;
    }
  }
  
  /**
   * Create the Department table.
   * 
   * @param conn The connection to the database.
   * @return True is successfully create, false otherwise/ app.run();
   */
  public boolean createTableProject(Connection conn) {
  	
    try {
      String create = "CREATE TABLE Project ( " +
      		"NAME VARCHAR(100) NOT NULL, " +
      		"NUMBER INTEGER NOT NULL, " +
          "LOCATIONS VARCHAR(80) NOT NULL, " +
          "CONTROLLED_BY INTEGER NOT NULL, " +
          "PRIMARY KEY (NAME, NUMBER))";

      this.executeUpdate(conn, create);
      return true;
    } catch (SQLException e) {
      this.printResponse("Error: Could not create the table: Project " + e.getMessage());
      return false;
    }
  }
  

  /**
   * Drop the Employee table.
   * 
   * @param conn The connection to the database.
   * @return True if successfully dropped, false otherwise.
   */
  public boolean dropTables(Connection conn) {
    try {
      String dropString = "DROP TABLE " + this.tableName + ", Department, Project";
      this.executeUpdate(conn, dropString);      
      return true;
    } catch (SQLException e) {
      this.printResponse("Error dropping tables! " + e.getMessage());
      return false;
    }
  }
  
  
 
  /**
   * Add some entries to the Table (for testing purposes).
   * 
   * @return True if successfully populated, false otherwise.
   */
  private boolean populate() {
  	
  	// Add some employees
    String employee1 = "INSERT INTO " + this.tableName +
        " VALUES (1, 'Joe Soap', '123 Main Street', '1990-09-23', 40000, 'm', 12, 13, 14)";
    String employee2 = "INSERT INTO " + this.tableName +
        " VALUES (2, 'Jimmy Bloggs', '10 High Street', '1991-08-22', 35000, 'm', 22, 23, 24)";
    
    // Add some departments 
    String department1 = "INSERT INTO Department" +
        " VALUES ('IT', 100, 'Waterford, Dublin')";
    String department2 = "INSERT INTO Department" +
        " VALUES ('Marketing', 101, 'Waterford, Cork')";

    // Add some projects
    String project1 = "INSERT INTO Project" +
        " VALUES ('Paperclip', 9001, 'Waterford', 1)";
    String project2 = "INSERT INTO Project" +
        " VALUES ('Manhattan', 9002, 'Cork', 2)";
    
    try {
      executeUpdate(conn, employee1);
      executeUpdate(conn, employee2);
      executeUpdate(conn, department1);
      executeUpdate(conn, department2);
      executeUpdate(conn, project1);
      executeUpdate(conn, project2);
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
    String readCommand = "SELECT * FROM " + this.tableName;
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
      printResponse("Connected to database");
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