//import java.sql.*;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.Duration;
import java.time.LocalDate;
//import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
public class Library{
	//book_id(10), book_name(20), book_author(20), book_available(int; 1), issued_date(Date;12), student_id(10) => Structure of Database
	static public boolean search(String searchbook, int searchby) {
		/*if searchby = 1, then search the database by book id
		 * if searchby = 2, then search the database by book name
		 * if searchby = 3, then search the database by book's author
		 * return true if the required stuff is found.
		 */
		try {
			String sqlStatement;
			if(searchby == 1) {
				//Search by the bookid
				sqlStatement = "Select * from Library where book_id = \""+searchbook+"\";";
			}
			else if(searchby == 2) {
				//Search by the book name
				sqlStatement = "Select * from Library where book_name = \""+searchbook+"\";";
			}
			else {
				//search by the book author
				sqlStatement = "Select * from Library where book_author = \""+searchbook+"\";";

			}
			ResultSet rs = javaMySqlConnector.getQuery(sqlStatement);
			//PRINT THE RESULTSET
			String leftAlignFormat = "| %-10s | %-25s | %-20s | %-15s |%n";
			System.out.format("+------------+---------------------------+----------------------+-----------------+%n");
			System.out.format("| Book ID    | Book Name                 |Book Author           |Book Availability|%n");
			System.out.format("+------------+---------------------------+----------------------+-----------------+%n");
			while(rs.next()) {
				System.out.format(leftAlignFormat,rs.getString("book_id"), rs.getString("book_name"), rs.getString("book_author"), rs.getString("book_available") );
			}
			System.out.format("+------------+---------------------------+----------------------+-----------------+%n");
			return true;
		}
		catch(Exception e) {
			System.err.println("Exception at search Function");
			System.err.println(e);
			return false;
		}
	}
	static public void search(){
		try{
			String st = "Select * from Library";
			ResultSet rs = javaMySqlConnector.getQuery(st);
			String leftAlignFormat = "| %-10s | %-25s | %-20s | %-15s |%n";
			System.out.format("+------------+---------------------------+----------------------+-----------------+%n");
			System.out.format("| Book ID    | Book Name                 |Book Author           |Book Availability|%n");
			System.out.format("+------------+---------------------------+----------------------+-----------------+%n");
			while(rs.next()) {
				System.out.format(leftAlignFormat,rs.getString("book_id"), rs.getString("book_name"), rs.getString("book_author"), rs.getString("book_available") );
			}
			System.out.format("+------------+---------------------------+----------------------+-----------------+%n");
		}
		catch(Exception e) {
			System.err.println("Exception at search Function");
			System.err.println(e);
		}
	}
	static public int getDues(String student_id) throws Exception {
		String st = "Select issued_date from Library where student_id = \""+ student_id + "\";";
		ResultSet rs = javaMySqlConnector.getQuery(st);/*get the resultset of issued_dates from concerned method */
		int dues = 0;
		Date current_date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
		while(rs.next()) {
			// current_date - rs.getDate("issued_date")
			Format formatter = new SimpleDateFormat("yyyy-MM-dd");
			String s = formatter.format(current_date);
			String q =formatter.format(rs.getDate("issued_date"));
			LocalDate d1 = LocalDate.parse(q, DateTimeFormatter.ISO_LOCAL_DATE);
			LocalDate d2 = LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
			Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
			long days_between = diff.toDays();

			//int days_between = current_date - rs.getDate("issued_date");
			if(days_between > 15)
				dues += days_between - 15;
		}
		return dues;
	}
	static public int booksHold(String student_id)throws Exception {
		String st = "Select * from Library where student_id = \""+ student_id + "\";";
		ResultSet rs = javaMySqlConnector.getQuery(st);/*get the resultset of issued_dates from concerned method */
		int rowCount = 0;
		while (rs.next()) {
			rowCount++;
		}
		String st2 = "UPDATE User SET num_borrowed = " + rowCount + " where student_id = \""+ student_id + "\";";
		javaMySqlConnector.updateDatabase(st2);
		return rowCount;
	}
	static public boolean issue(String student_id, String issuebookid,String issuedate) {
		/*
		 * check if book_available at book_id = issuebookid is 1 or 0.
		 * If it is 1, then put issuer_id to the row at column issuer_id,
		 * and change book_available at that row to 0.
		 * If book_available is 0 at that row, then say that
		 * if due > 0 -> Tell that due > 0 and return false.
		 * if book on hold > 3 -> Tell that the book has been issued more than 3 times, and return false
		 */
		try {
			//check if the value at book_available = 0 or 1. If it is 1, reduce it to 0 and be happy. if it's 0, tell that it has already been issued by another user
			if(getDues(student_id) > 15) {
				System.out.println("Since the student's dues are uncleared, book cannot be issued to this student.");
				return false;
			}
			if(booksHold(student_id) == 3) {
				System.out.println("Since the student is already holding 3 books, book cannot be issued to this student.");
				return false;
			}
			String st = "Select book_available from Library where book_id = \""+ issuebookid + "\";";
			ResultSet rs = javaMySqlConnector.getQuery(st);/*Send st to method to run and get the concerned value.*/

			while(rs.next()){
				if(rs.getInt("book_available") == 0) {
					System.out.println("Since the book is already issued by another student, this book cannot be issued to this student.");
					return false;
				}
				else {
					String st1 = "Update Library SET book_available = 0, issued_date = \""+/*Current date*/issuedate+"\", student_id = \""+student_id+"\" where book_id =\""+issuebookid +"\";";
					javaMySqlConnector.updateDatabase(st1);
					//Run st1
					return true;
				}}
			return false;
		}
		catch(Exception e){
			System.out.println("Error at Issue Function!");
			System.out.println(e);
			return false;
		}

	}
	static public boolean reissue(String student_id, String issuebookid) {
		/*
		 *
		 */


		try {
			String st = "Select * from Library where student_id = \""+student_id+"\" and book_id = \""+issuebookid+"\";";
			ResultSet rs = javaMySqlConnector.getQuery(st);
			if(!rs.next()){
				System.out.println("Either the book is not in the library or it has been issued by someone else. Cannot Reissue book.");
				return false;
			}
			//check if the value at book_available = 0 or 1. If it is 1, reduce it to 0 and be happy. if it's 0, tell that it has already been issued by another user
			if(getDues(student_id) > 0) {
				System.out.println("Since the student's dues are uncleared, book cannot be issued to this student.");
				return false;
			}
			String st1 = "Update Library SET book_available = 0, issued_date = \""+/*Current date*/LocalDate.now()+"\", student_id = \""+student_id+"\" where book_id =\""+issuebookid +"\";";
			javaMySqlConnector.updateDatabase(st1);
			//Run st1
			return true;

		}
		catch(Exception e){
			System.out.println("Error at Reissue Function!");
			System.out.println(e);
			return false;
		}

	}
	static public boolean bookreturn(String issuebookid) {
		try {
			String st = "Select book_available from Library where book_id = \""+ issuebookid + "\";";
			ResultSet rs = javaMySqlConnector.getQuery(st);/*Send st to method to run and get the concerned value.*/
			while(rs.next()){
				if(rs.getInt("book_available") == 1) {
					System.out.println("Book already present in library; cannot be returned.");
					return false;
				}
				else {
					String st1 = "Update Library SET book_available = 1, issued_date = NULL, student_id = NULL where book_id =\""+issuebookid + "\";";
					javaMySqlConnector.updateDatabase(st1);
					return true;
				}
			}
			return false;



		}
		catch(Exception e){
			System.out.println("Error at Issue Function!");
			System.out.println(e);
			return false;
		}

	}


}
