import java.sql.SQLOutput;
import java.util.*;

public class LibraryManagement {

    public static void printPortalFrontPage(){
        System.out.println("+---------------------------------------------------------------+");
        System.out.println("|\t\t\t\t      WELCOME TO LIBRARY PORTAL     \t\t\t|");
        System.out.println("|\t\t\t\t Input \"A\" to access ADMIN        \t\t\t\t|");
        System.out.println("|\t\t\t\t Input \"S\" to access STUDENT      \t\t\t\t|");
        System.out.println("|\t\t\t\t Input \"E\" to terminate the portal\t\t\t\t|");
        System.out.println("+---------------------------------------------------------------+");
    }
    public static void printAdminFrontPage(){
        System.out.println("+---------------------------------------------------------------+");
        System.out.println("|\t\t\t         WELCOME TO ADMIN PORTAL                \t|");
        System.out.println("|\t\t\t   Press \"1\" to Add a Student                   \t|");
        System.out.println("|\t\t\t   Press \"2\" to Remove a Student                \t|");
        System.out.println("|\t\t\t   Press \"3\" to Add a Book                       \t|");
        System.out.println("|\t\t\t   Press \"4\" to Remove a Book                    \t|");
        System.out.println("+---------------------------------------------------------------+");
    }

    public static void printStudentPortal(){
        System.out.println("+---------------------------------------------------------------+");
        System.out.println("|\t\t        WELCOME TO STUDENT PORTAL           \t\t\t|");
        System.out.println("|\t\t    Press \"1\" to Display All Books        \t\t\t\t|");
        System.out.println("|\t\t    Press \"2\" to Search for a Book        \t\t\t\t|");
        System.out.println("|\t\t    Press \"3\" to Get Dues                 \t\t\t\t|");
        System.out.println("|\t\t    Press \"4\" to Get Books on Hold        \t\t\t\t|");
        System.out.println("|\t\t    Press \"5\" to Issue a Book             \t\t\t\t|");
        System.out.println("|\t\t    Press \"6\" to Reissue a Book           \t\t\t\t|");
        System.out.println("|\t\t    Press \"7\" to Return a Book            \t\t\t\t|");
        System.out.println("+---------------------------------------------------------------+");
    }

    public static String getLogin(){
        System.out.println("+---------------------------------------------------------------+");
        System.out.println("|\t\t       WELCOME TO LOGIN PORTAL                  \t\t|");
        System.out.println("|\t\t   Press \"1\" to Login                         \t\t\t|");
        System.out.println("|\t\t   Press \"2\" to Register                      \t\t\t|");
        System.out.println("|\t\t   Press \"3\" to Reset Passwords               \t\t\t|");
        System.out.println("+---------------------------------------------------------------+");
        Scanner sc = new Scanner(System.in);
        int temp = Integer.parseInt(sc.nextLine());
        String email = "";
        if(temp == 1 || temp ==2 || temp == 3 ){
            System.out.print("Input Email Address : ");
            email = sc.nextLine();
        }
        switch(temp) {
            case 1:
                System.out.print("Input Password : ");
                String password = sc.nextLine();
                return UserDetails.Login(email,password);
            case 2:
                System.out.print("Input First Name : ");
                String fname = sc.nextLine();
                System.out.print("Input Last Name : ");
                String lname = sc.nextLine();
                System.out.print("Input Password : ");
                String pssword = sc.nextLine();
                System.out.print("Confirm Password : ");
                String pssword2 = sc.nextLine();
                System.out.print("Input Student ID :");
                String student_id = sc.nextLine();
                UserDetails.Register(fname,lname,email,pssword,pssword2,student_id);
                return getLogin();
            case 3:
                System.out.print("Input OLD Password : ");
                String oldPassword = sc.nextLine();
                System.out.print("Input NEW Password : ");
                String newPassword = sc.nextLine();
                System.out.print("Confirm NEW Password : ");
                String confirmPassword = sc.nextLine();
                UserDetails.UpdatePassword(email,oldPassword,newPassword,confirmPassword);
                return getLogin();
            default :
                System.out.println("Unexpected Output, Please enter a valid number.");
        }
        return null;
    }

    public static String[] getAdminQueryData(int t){
        String[] queryData = new String[4];
        Scanner sc = new Scanner(System.in);
        switch(t) {
            case 1:
                System.out.print("Input First Name : ");
                queryData[0] = sc.next();
                System.out.print("Input Last Name : ");
                queryData[1] = sc.next();
                System.out.print("Input Student ID : ");
                queryData[2] = sc.next();
                System.out.print("Input Email : ");
                queryData[3] = sc.next();
                break;
            case 2:
                System.out.println("Input how to remove Student (\"NAME\", \"ID\",\"EMAIL\") : ");
                        queryData[0] = sc.nextLine();
                        switch(queryData[0]) {
                            case "ID":
                                System.out.print("Input student id : ");
                                queryData[1] = sc.nextLine();
                                break;
                            case "EMAIL":
                                System.out.print("Input email address : ");
                                queryData[1] = sc.nextLine();
                                break;
                            case "NAME":
                                System.out.print("Input first name : ");
                                queryData[1] = sc.nextLine();
                                System.out.print("Input last name : ");
                                queryData[2] = sc.nextLine();
                                break;
                            default:
                                System.out.println("Input Correct Values");
                        }

                break;
            case 3:
                System.out.print("Input Book ID : ");
                queryData[0] = sc.nextLine();
                System.out.print("Input Book Name : ");
                queryData[1] = sc.nextLine();
                System.out.print("Input Author Name : ");
                queryData[2] = sc.nextLine();
                break;
            case 4:
                System.out.println("Enter \"ID\" if you want to remove book based on Book ID, enter \"NAME\" if you want to remove book based on Book Name: ");
                queryData[1] = sc.nextLine();
                if(queryData[1].equals("ID"))
                    System.out.print("\nEnter the ID of the book: ");
                else if(queryData[1].equals("NAME"))
                    System.out.print("\nEnter the Name of the book: ");
                queryData[0] = sc.nextLine();
                break;
            default:
                return null;
        }
        return queryData;
    }
    public static String[] getStudentQueryData(int t){
        String[] queryData = new String[2];
        if(t==1|| t==3||t==4)
            return queryData;
        Scanner sc = new Scanner(System.in);
        switch(t) {
            case 2 :
                System.out.print("Enter 1 to search by Book ID, 2 to search by Book Name, 3 to search by Book Author: ");
                queryData[1] = sc.next();
                if(queryData[1].equals("1"))
                    System.out.print("\nEnter the ID of the book: ");
                else if(queryData[1].equals("2"))
                    System.out.print("\nEnter the Name of the book: ");
                else
                    System.out.print("\nEnter the Author of the book: ");
                queryData[0] = sc.next();
                sc.close();
                break;
            case 5:
                System.out.print("Enter the Book ID to be issued: ");
                queryData[0] = sc.next();
                System.out.println("Enter the Date of issue: ");
                queryData[1] = sc.next();
                sc.close();
                break;
            case 6:
                queryData = new String[1];
                System.out.print("Enter the Book ID to be reissued: ");
                queryData[0] = sc.next();
                sc.close();
                break;
            case 7:
                queryData = new String[1];
                System.out.print("Enter the Book ID to be Returned: ");
                queryData[0] = sc.next();
                sc.close();
                break;
            default:
                return null;

        }
        return queryData;
    }
    public static void main(String[] args) {
            printPortalFrontPage();
            Scanner sc = new Scanner(System.in);
            String [] queryData;
            switch(sc.nextLine()){
                case "A":
                    printAdminFrontPage();
                    int t = Integer.parseInt(sc.nextLine());
                    queryData = getAdminQueryData(t);
                    if (queryData == null){
                        System.out.println("Invalid Admin Command");
                        break;
                    }
                    AdminQuery thread = new AdminQuery("0000", t, queryData);
                    break;
                case "S":
                    String studentId = getLogin();
                    if(studentId == null){
                        System.out.println("Invalid Login Details");
                        break;
                    }
                    printStudentPortal();
                    int n = Integer.parseInt(sc.nextLine());
                    queryData = getStudentQueryData(n);
                    if (queryData == null){
                        System.out.println("Invalid Query Data");
                        break;
                    }
                    UserQuery thread2= new UserQuery(studentId,n,queryData);

                case "E":
                    System.out.println("Terminating program");
                    break;
                default:
                    System.out.println("Input Correct ");

            }
        }
}
