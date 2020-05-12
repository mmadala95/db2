import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.*; 
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

public class SalaryStdDevD {
	 private Connection con = null;
	 private Statement stmt = null;
	 private PreparedStatement pstmt = null;
 
	 public void setDBConnection(String url, String user, String password) {
	 try {
	 Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
	 ;
	 con = DriverManager.getConnection(url, user, password);
	 } catch (Exception e) {
	 e.printStackTrace();
	 }
	 }

	 public void printQueryResult(String sql) {
	 Statement stmt = null;
	 ResultSet rs = null;
	 //Creating a new statement 
	 try {
	 stmt = con.createStatement();
	 } catch (SQLException e) {
	 e.printStackTrace();
	 }
	 //Executing a query
	 try {
	 rs = stmt.executeQuery(sql);
	 } catch (SQLException e) {
	 e.printStackTrace();
	 }
	 //Printout the results and perform analysis such as standard deviation.	
	 try {
	 double salarySum = 0;
	 double standardDeviation = 0;
	 long count = 0;
	 double tempSalary = 0;
	 ArrayList<Double> mySalary = new ArrayList<Double>();	 
	 
	 //System.out.println("SALARY");
	 while (rs.next()) {
		tempSalary = rs.getDouble("SALARY");
		mySalary.add(tempSalary);
		salarySum += tempSalary;
		count += 1;
	 }
	 double averageSalary = salarySum/count;
	 
	 for(int i = 0; i < count; i++)
	 {
		 standardDeviation += pow(mySalary.get(i) - averageSalary,2)/count;
 	 }
	 standardDeviation = sqrt(standardDeviation);
	 
	 System.out.println(standardDeviation);
	 
	 } catch (SQLException e) {
	 e.printStackTrace();
	 }

	 }
	 public static void main(String[] args) {
		 String dbURL = "jdbc:db2://localhost:50000/";
		 String databasename = args[0];
		 dbURL += databasename;
		 String tablename = args[1];
		 String user = args[2];
		 String password = args[3]; 
		 
		 String sql = "select SALARY from cse532." + tablename + ";";
		 SalaryStdDevD demo = new SalaryStdDevD();
		 demo.setDBConnection(dbURL, user, password); 
		 demo.printQueryResult(sql);
	 }
}


