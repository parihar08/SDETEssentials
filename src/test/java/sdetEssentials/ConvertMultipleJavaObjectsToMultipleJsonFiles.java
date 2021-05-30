package sdetEssentials;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * This program is for extracting multiple records from the database and 
 * convert it into multiple java objects and convert those objects to multiple json files
 * Multiple Results >Multiple Java Objects > Multiple JSON Files
 * using Jackson API
 * Maven Dependencies: Jackson Core, Jackson Databind, Jackson annotations
 */

public class ConvertMultipleJavaObjectsToMultipleJsonFiles {
	
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306"
					+ "/ClassicModels","root","Calgary_08");
			Statement stmt = con.createStatement();
			String query = "select * from CustomerInfo";
			ResultSet result = stmt.executeQuery(query);
			
			ArrayList<CustomerDetails> al = new ArrayList<CustomerDetails>();
			
			while(result.next()){
				String bookName = result.getString("BookName");
				String purchaseDate = result.getString("PurchaseDate");
				String location = result.getString("Location");
				int amount = result.getInt("Amount");
				
				CustomerDetails cd = new CustomerDetails();
				
				cd.setBookName(bookName);
				cd.setPurchaseDate(purchaseDate);
				cd.setAmount(amount);
				cd.setLocation(location);
				
				al.add(cd);
				
			}
			for(int i=0;i<al.size();i++)
			{
				File jsonFile = new File(System.getProperty("user.dir") + "/customerDetails"+(i+1)+".json");

				ObjectMapper om = new ObjectMapper();
				om.writeValue(jsonFile, al.get(i));
			}
			/*
			 * This will save the results from cd object(containing results from database)
			 *  into multiple customerDetails1/2/3/4/5 json files
			 */
			
			
			con.close();
			
			
			System.out.println("Done!!");
			
					
					
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	
		
	}
}
