//import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.*;
import java.sql.Statement;
import java.sql.ResultSet;
public class javaMySqlConnector{
    static int count;
 public static Connection getConnection() throws Exception{
  try{
   String driver = "com.mysql.cj.jdbc.Driver";
   String url = "jdbc:mysql://localhost/Library";
   String username = "root";
   String password;
   {
       password = "Aryan@2002";
   };
   Class.forName(driver);
   
   Connection conn = DriverManager.getConnection(url,username,password);
   return conn;
  } catch(Exception e){System.out.println(e);}
  
  
  return null;
 }

 public synchronized static void updateDatabase(String sqlQuery)throws Exception{
        System.out.println("Query Started : " + count);
        Connection conn = javaMySqlConnector.getConnection();
        Statement st = conn.createStatement();
        st.executeUpdate(sqlQuery);
        System.out.println("Query Ended : " + count++);
        conn.close();
 }

 public synchronized static ResultSet getQuery(String sqlQuery)throws Exception{
    Connection conn = javaMySqlConnector.getConnection();
    Statement st = conn.createStatement();
    ResultSet rt = st.executeQuery(sqlQuery);
    return rt;
 }
}