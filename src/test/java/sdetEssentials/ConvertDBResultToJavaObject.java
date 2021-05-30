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
 * This program is only for extracting one record from the database and convert it into one java object
 */

public class ConvertDBResultToJavaObject {
	
	public static void main(String[] args) {
		
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306"
					+ "/ClassicModels","root","Calgary_08");
			Statement stmt = con.createStatement();
			String query = "select * from CustomerInfo limit 1";
			ResultSet result = stmt.executeQuery(query);
			
			CustomerDetails cd = new CustomerDetails();
			
			while(result.next()){
				String bookName = result.getString("BookName");
				String purchaseDate = result.getString("PurchaseDate");
				String location = result.getString("Location");
				int amount = result.getInt("Amount");
				
				cd.setBookName(bookName);
				cd.setPurchaseDate(purchaseDate);
				cd.setAmount(amount);
				cd.setLocation(location);
				
				System.out.println(cd.getBookName()+"		"+cd.getPurchaseDate()+
						"		"+cd.getAmount()+"	"+cd.getAmount());
			}
			
			con.close();
					
					
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	
		
	}
}
