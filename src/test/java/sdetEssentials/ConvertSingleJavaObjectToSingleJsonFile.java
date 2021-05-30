package sdetEssentials;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * This program is for extracting one record from the database and 
 * convert it into single java object and convert that object to single json file 
 * using Jackson API
 * Maven Dependencies: Jackson Core, Jackson Databind, Jackson annotations
 */

public class ConvertSingleJavaObjectToSingleJsonFile {
	
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		
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
				
			}
			File jsonFile = new File(System.getProperty("user.dir")+
					"/customerDetails.json");
			
			ObjectMapper om = new ObjectMapper();
			om.writeValue(jsonFile, cd);
			/*
			 * This will save the results from cd object(containing results from database)
			 *  into customerDetails json file
			 */
			con.close();
			
			System.out.println("Done!!");
			
					
					
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	
		
	}
}
