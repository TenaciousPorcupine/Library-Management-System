import java.sql.ResultSet;
public class UserDetails  {
    
    public static void Register(String first_name,String last_name,String Email,String password, String confirmPassword,String StudentID){
        try{
            if(password.equals(confirmPassword)){
                String sqlStatement = "Insert into User(first_name,last_name,student_id,email,password,is_Admin,num_borrowed,dues,login_Status)"+"values(\""+first_name+"\",\""+last_name+"\",\""+StudentID+"\",\""+Email+"\",\""+password+"\",false,0,0,false);";
                javaMySqlConnector.updateDatabase(sqlStatement);
            }
            else{
                System.out.println("Password and Confirm Password does not match! Try Again");
            }
        }
        catch(Exception e){
            System.err.println("Exception at Register Function");
            System.err.println(e);
        }
    }


    public static String Login(String Email, String password){
        try{
            String sqlStatement = "Select * from User where email = \""+Email+"\";";
            ResultSet rs = javaMySqlConnector.getQuery(sqlStatement);
            while(rs.next()){
                String passwordFromDb = rs.getString("password");
                String userId = rs.getString("student_id");
                if(passwordFromDb.equals(password)){
                    String sqlQuery = "UPDATE User set login_Status = true where email = \""+Email+"\";";
                    javaMySqlConnector.updateDatabase(sqlQuery);
                    return userId;
                }
                else{
                    System.out.println("Incorrect password! Try again.");
                }
            }
        }
        catch(Exception e){
            System.out.println("Error at Login Function!");
            System.out.println(e);
        }
        return null;

    }


    public static void UpdatePassword(String Email, String oldPassword, String newPassword, String confirmNewPassword ){
        if(newPassword.equals(confirmNewPassword)){
            try{
                String sqlStatement = "Select * from User where email = \""+Email+"\";";
                ResultSet rs = javaMySqlConnector.getQuery(sqlStatement);
                while(rs.next()){
                    String passwordFromDb = rs.getString("password");
                    if(passwordFromDb.equals(oldPassword)){
                        String sqlQuery = "UPDATE User set password =\""+newPassword+"\" where email = \""+Email+"\";";
                        javaMySqlConnector.updateDatabase(sqlQuery);
                    }
                    else{
                        System.out.println("Old password is incorrect!");
                    }
                }

            }
            catch(Exception e){
                System.out.println("Password could not be Updated! Please Try Again");
                System.out.println(e);
            }
        }
    }
}
