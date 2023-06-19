import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class AdminManagement{
    void addStudent(String fname, String lname,String studentID,String email){
        String password = "BITS123";
        String query = "INSERT INTO User(first_name, last_name, student_id, email, password, is_Admin, num_borrowed, dues, login_Status)";
        query += " VALUES ('"+fname+"' , '"+lname+"' , '"+studentID+"' , '"+email+ "' , '"+ password+ "' , false, 0, 0, false);";
        try {
            javaMySqlConnector.updateDatabase(query);
        }
        catch(Exception e){
            System.out.println("Exception on adding Student to the Book database");
            System.out.println(e.getMessage());
            for (var s: e.getStackTrace())
                System.out.println(s);
        }

    }

    void removeStudent(String... keys){ /*The first parameter of the keys array must be either ID, NAME or EMAIL (How you want to remove the User), the next values in keys must be the Data the query requires*/
        String query = "DELETE FROM User WHERE ";
        switch (keys[0]) {
            case "ID" -> query += "student_id ='" + keys[1] + "';"; /*For ID, you only require the student_id (single data)*/
            case "NAME" -> query += "first_name='" + keys[1] + "' AND last_name='" + keys[2] + "';"; /*For NAME, you require both first_name and last_name of the student(two datas)*/
            case "EMAIL" -> query += "email='" + keys[1] + "';"; /*For EMAIL, you only require the email address(single data)*/
            default -> query = null; /*If the keys doesn't match with any of the above cases, a null query is executed which  */
        }
        try{
            javaMySqlConnector.updateDatabase(query);
        }catch(Exception e){
            System.out.println("Exception on removing student from Book database");
            System.out.println(e.getMessage());
        }

    }

    void addBook(String bookId, String bookName, String authorName){
        String query = "INSERT INTO Library(book_id, book_name, book_author, book_available)";
        query += " VALUES ('"+bookId+"' , '"+bookName+"' , '"+authorName+"' , 1);";
        try {
            javaMySqlConnector.updateDatabase(query);
        }catch(Exception e){
            System.out.println("Exception on adding book to the Library database");
            System.out.println(e.getMessage());
            for (var s: e.getStackTrace())
                System.out.println(s);
        }
    }

    void removeBook(String key, String type){
        String query = "DELETE FROM Library WHERE ";
        switch (type) {
            case "ID" -> query += "book_id ='" + key + "';";
            case "NAME" -> query += "book_name='" + key + "';";
            default -> query = null;
        }
        try{
            javaMySqlConnector.updateDatabase(query);
        }catch(Exception e){
            System.out.println("Exception on removing book from Library database");
            System.out.println(e.getMessage());
            for (var s: e.getStackTrace())
                System.out.println(s);
        }

    }
}
