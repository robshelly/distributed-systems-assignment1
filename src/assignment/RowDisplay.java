package assignment;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class RowDisplay extends JPanel {
  
  //TextFields
  private JTextField txtSsn;
  private JTextField txtName;
  private JTextField txtAddress;
  private JTextField txtDob;
  private JTextField txtSalary;
  private JTextField txtSex;
  private JTextField txtWorksFor;
  private JTextField txtManages;
  private JTextField txtSupervises;

  /**
   * Create the panel.
   */
  public RowDisplay() {
//    
    GridBagLayout gbl_display = new GridBagLayout();
    gbl_display.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
    setLayout(gbl_display);
     
    // SSN
    
    JPanel pnlSsn = new JPanel();
    pnlSsn.setLayout(new BoxLayout(pnlSsn, BoxLayout.PAGE_AXIS));
    GridBagConstraints gbc_pnlSsn = new GridBagConstraints();
    gbc_pnlSsn.insets = new Insets(0, 0, 5, 5);
    gbc_pnlSsn.gridx = 1;
    gbc_pnlSsn.gridy = 0;
    add(pnlSsn, gbc_pnlSsn);
    
    JLabel lblSsn = new JLabel("SSN");
    pnlSsn.add(lblSsn);
    txtSsn = new JTextField();
    txtSsn.setColumns(4);
    pnlSsn.add(txtSsn);
    
    // Name
    
    JPanel pnlName = new JPanel();
    pnlName.setLayout(new BoxLayout(pnlName, BoxLayout.PAGE_AXIS));
    GridBagConstraints gbc_pnlName = new GridBagConstraints();
    gbc_pnlName.insets = new Insets(0, 0, 5, 5);
    gbc_pnlName.gridx = 2;
    gbc_pnlName.gridy = 0;
    add(pnlName, gbc_pnlName);
    
    JLabel lblName = new JLabel("Name");
    pnlName.add(lblName);
    txtName = new JTextField();
    txtName.setColumns(10);
    pnlName.add(txtName);
    
    // Address
    
    JPanel pnlAddress = new JPanel();
    pnlAddress.setLayout(new BoxLayout(pnlAddress, BoxLayout.PAGE_AXIS));
    GridBagConstraints gbc_pnlAddress = new GridBagConstraints();
    gbc_pnlAddress.insets = new Insets(0, 0, 5, 5);
    gbc_pnlAddress.gridx = 3;
    gbc_pnlAddress.gridy = 0;
    add(pnlAddress, gbc_pnlAddress);
    
    JLabel lblAddress = new JLabel("Address");
    pnlAddress.add(lblAddress);
    txtAddress = new JTextField();
    txtAddress.setColumns(10);
    pnlAddress.add(txtAddress);
    
    // DOB
    
    JPanel pnlDob = new JPanel();
    pnlDob.setLayout(new BoxLayout(pnlDob, BoxLayout.PAGE_AXIS));
    GridBagConstraints gbc_pnlDob = new GridBagConstraints();
    gbc_pnlDob.insets = new Insets(0, 0, 5, 5);
    gbc_pnlDob.gridx = 4;
    gbc_pnlDob.gridy = 0;
    add(pnlDob, gbc_pnlDob);
    
    JLabel lblDob = new JLabel("DOB");
    pnlDob.add(lblDob);
    txtDob = new JTextField();
    txtDob.setColumns(10);
    pnlDob.add(txtDob);
    
    // Salary
    
    JPanel pnlSalary = new JPanel();
    pnlSalary.setLayout(new BoxLayout(pnlSalary, BoxLayout.PAGE_AXIS));
    GridBagConstraints gbc_pnlSalary = new GridBagConstraints();
    gbc_pnlSalary.insets = new Insets(0, 0, 5, 5);
    gbc_pnlSalary.gridx = 6;
    gbc_pnlSalary.gridy = 0;
    add(pnlSalary, gbc_pnlSalary);
    
    JLabel lblSalary = new JLabel("Salary");
    pnlSalary.add(lblSalary);
    txtSalary = new JTextField();
    txtSalary.setColumns(5);
    pnlSalary.add(txtSalary);
    
    // Sex
    
    JPanel pnlSex = new JPanel();
    pnlSex.setLayout(new BoxLayout(pnlSex, BoxLayout.PAGE_AXIS));
    GridBagConstraints gbc_pnlSex = new GridBagConstraints();
    gbc_pnlSex.insets = new Insets(0, 0, 5, 5);
    gbc_pnlSex.gridx = 5;
    gbc_pnlSex.gridy = 0;
    add(pnlSex, gbc_pnlSex);
    
    JLabel lblSex = new JLabel("Sex");
    pnlSex.add(lblSex);
    txtSex = new JTextField();
    txtSex.setColumns(1);
    pnlSex.add(txtSex);
 
    // WorksFor
    
    JPanel pnlWorksFor = new JPanel();
    pnlWorksFor.setLayout(new BoxLayout(pnlWorksFor, BoxLayout.PAGE_AXIS));
    GridBagConstraints gbc_pnlWorksFor = new GridBagConstraints();
    gbc_pnlWorksFor.insets = new Insets(0, 0, 5, 5);
    gbc_pnlWorksFor.gridx = 7;
    gbc_pnlWorksFor.gridy = 0;
    add(pnlWorksFor, gbc_pnlWorksFor);
    
    JLabel lblWorksFor = new JLabel("WorksFor");
    pnlWorksFor.add(lblWorksFor);
    txtWorksFor = new JTextField();
    txtWorksFor.setColumns(5);
    pnlWorksFor.add(txtWorksFor);
    
    // Manages
    
    JPanel pnlManages = new JPanel();
    pnlManages.setLayout(new BoxLayout(pnlManages, BoxLayout.PAGE_AXIS));
    GridBagConstraints gbc_pnlManages = new GridBagConstraints();
    gbc_pnlManages.insets = new Insets(0, 0, 5, 5);
    gbc_pnlManages.gridx = 8;
    gbc_pnlManages.gridy = 0;
    add(pnlManages, gbc_pnlManages);
    
    JLabel lblManages = new JLabel("Manages");
    pnlManages.add(lblManages);
    txtManages = new JTextField();
    txtManages.setColumns(5);
    pnlManages.add(txtManages);
    
    // Supervises
    
    JPanel pnlSupervises = new JPanel();
    pnlSupervises.setLayout(new BoxLayout(pnlSupervises, BoxLayout.PAGE_AXIS));
    GridBagConstraints gbc_pnlSupervises = new GridBagConstraints();
    gbc_pnlSupervises.insets = new Insets(0, 0, 5, 5);
    gbc_pnlSupervises.gridx = 9;
    gbc_pnlSupervises.gridy = 0;
    add(pnlSupervises, gbc_pnlSupervises);
    
    JLabel lblSupervises = new JLabel("Supervises");
    pnlSupervises.add(lblSupervises);
    txtSupervises = new JTextField();
    txtSupervises.setColumns(5);
    pnlSupervises.add(txtSupervises);
  }
  
  /**
   * Displays a row in the relevant fields.
   * 
   * @param id The ID of the row.
   * @param name The name of the row.
   * @param address The address of the row.
   * @param dob The DOB of the row.
   * @param sex The sex of the row.
   * @param salary The salary of the row.
   * @param worksFor The worksFor foreign key of the row.
   * @param manages The manages foreign key of the row.
   * @param supervises The supervises foreign key of the row.
   */
  public void displayRow(int ssn, String name, String address, String dob, char sex,
      int salary, int worksFor, int manages, int supervises) {
    setSsnField(ssn);
    setNameField(name);
    setAddressField(address);
    setDobField(dob);
    setSexField(sex);
    setSalaryField(salary);
    setWorksForField(worksFor);
    setManagesField(manages);
    setSupervisesField(supervises);
  }
  

  /**
   * Get SSN for the current row.
   * 
   * return The SSN in the SSN text field.
   */
  public String getSsnField() {
    return txtSsn.getText();
  }
  
  /**
   * Set the text in the SSN field.
   * 
   * @param SSN The SSN to insert into the field.
   */
  private void setSsnField(int ssn) {
    txtSsn.setText(Integer.toString(ssn));
  }
  
  /**
   * Get name for the current row.
   * 
   * return The name in the name text field.
   */
  public String getNameField() {
    return txtName.getText();
  }
  
  /**
   * Set the text in the name field.
   * 
   * @param name The name to insert into the field.
   */
  private void setNameField(String name) {
    txtName.setText(name);
  }
  
  /**
   * Get address for the current row.
   * 
   * return The address in the address text field.
   */
  public String getAddressField() {
    return txtAddress.getText();
  }  
  
  /**
   * Set the text in the address field.
   * 
   * @param address The address to insert into the field.
   */
  private void setAddressField(String address) {
    txtAddress.setText(address);
  }
  
  /**
   * Get DOB for the current row.
   * 
   * return The DOB in the DOB text field.
   */
  public String getDobField() {
    return txtDob.getText();
  }  
  
  /**
   * Set the text in the dob field.
   * 
   * @param dob The dob to insert into the field.
   */
  private void setDobField(String dob) {
    txtDob.setText(dob);
  }
  
  /**
   * Get salary for the current row.
   * 
   * return The salary in the salary text field.
   */
  public String getSalaryField() {
    return txtSalary.getText();
  }
  
  /**
   * Set the text in the salary field
   * 
   * @param salary The salary to insert into the field.
   */
  private void setSalaryField(int salary) {
    txtSalary.setText(Integer.toString(salary));
  }
  
  /**
   * Get sex for the current row.
   * 
   * return The sex in the sex text field.
   */
  public String getSexField() {
    return txtSex.getText();
  }
  
  /**
   * Set the text in the sex field.
   * 
   * @param sex The sex to insert into the field.
   */
  private void setSexField(char sex) {
    txtSex.setText(Character.toString(sex));
  }
  
  /**
   * Get worksFor for the current row.
   * 
   * return The ID in the worksFor text field.
   */
  public String getWorksForField() {
    return txtWorksFor.getText();
  }
  
  /**
   * Set the text in the Works for field.
   * 
   * @param worksFor The id of the department to insert into the field.
   */
  private void setWorksForField(int worksFor) {
    txtWorksFor.setText(Integer.toString(worksFor));
  }
  
  /**
   * Get manages for the current row.
   * 
   * return The ID in the manages text field.
   */
  public String getManagesField() {
    return txtManages.getText();
  }
  
  /**
   * Set the text in the manages field.
   * 
   * @param manages The id of the manages foreign key to insert into the field.
   */
  private void setManagesField(int manages) {
    txtManages.setText(Integer.toString(manages));
  }
  
  /**
   * Get supervises for the current row.
   * 
   * return The ID in the supervises text field.
   */
  public String getSupervisesField() {
    return txtSupervises.getText();
  }
  
  /**
   * Set the text in the supervises for field.
   * 
   * @param worksFor The id of the supervises foreign key to insert into the field.
   */
  private void setSupervisesField(int supervises) {
    txtSupervises.setText(Integer.toString(supervises));
  }
  
  
  /**
   * Clear all the text fields.
   */
  public void clearFields() {
    txtSsn.setText("");
    txtName.setText("");
    txtAddress.setText("");
    txtDob.setText("");
    txtSalary.setText("");
    txtSex.setText("");
    txtWorksFor.setText("");
    txtManages.setText("");
    txtSupervises.setText("");
  }
}
