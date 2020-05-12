package Assignment2_madala_112684431;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class SalaryStdDev {


    public static void main(String args[])
    {

        try
        {
        //Creating the connection
            Connection con=null;
            String url = "jdbc:db2://localhost:50000/";
            String TableName=null;
            String user=null;
            String pass=null;
            if(args.length==1){

                con = DriverManager.getConnection(url+args[0]);
            }
            else if(args.length==4){
                 TableName=args[1];
                 user = args[2];
                 pass = args[3];
                con = DriverManager.getConnection(url+args[0],user,pass);
            }
            else{
                throw new Exception("\n Usage: java MyJDBC[,username,password]\n");
            }



            Statement statement=con.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT count(*) as total,sum(SALARY) as salarySum,sum(SALARY*SALARY) as salarySumSquare from "+TableName);
            int num=0;
            float salsum=0;
            float salsumsquare=0;
            while (resultSet.next()) {
                 num= Integer.parseInt(resultSet.getString(1));
                 salsum = Float.parseFloat(resultSet.getString(2));
                 salsumsquare = Float.parseFloat(resultSet.getString(3));
            }


            System.out.println(Math.sqrt((salsumsquare/num)-Math.pow((salsum/num),2)));


            resultSet.close();
            statement.close();
            con.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
