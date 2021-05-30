package sdetEssentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 1. Create a DB connection
 * 2. Create statement/query
 * 3. Execute query, then store the results
 * 4. Print the results
 */

public class RetrieveDataFromDB_JDBC {
	
	public static void main(String[] args) {
		
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306"
					+ "/ClassicModels","root","Calgary_08");
			Statement stmt = con.createStatement();
			String query = "select * from CustomerInfo limit 1";
			ResultSet result = stmt.executeQuery(query);
			
			while(result.next()){
				String bookName = result.getString("BookName");
				String purchaseDate = result.getString("PurchaseDate");
				String location = result.getString("Location");
				int amount = result.getInt("Amount");
				
				System.out.println(bookName+"		"+purchaseDate+"		"+amount+"	"+location);
			}
			
			con.close();
					
					
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	
		
	}
}
