package sdetEssentials;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

/*
 * This program is for extracting multiple records from the database and 
 * convert it into multiple java objects and convert those objects to single json file
 * Multiple Results >Multiple Java Objects > Single JSON File
 * using Jackson API
 * Maven Dependencies: Jackson Core, Jackson Databind, Jackson annotations, GSON, JSON-Simple
 * 
 */

public class ConvertMultipleJavaObjectsToSingleJsonFile {
	
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
			//Get every object from the array list(al) and convert every object into 
			//JSON String. All the JSON Strings should be stored inside the JSON array
			//Finally add the JSON Array into JSON object
			
			JSONArray jsonAr = new JSONArray();
			
			for(CustomerDetails cds: al)
			{
				//Converting Java Object into JSON String
				Gson g = new Gson();
				String jsonString = g.toJson(cds);
				//Add JSON String to JSON Array
				jsonAr.add(jsonString);
			}
			
			//Adding the JSON Array into JSON object
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("data", jsonAr);
			
			System.out.println(jsonObj.toJSONString()); 
			System.out.println("-------------------------------");
			//By default it will add slashes("\") and double quotes("") to output Json String
			//We need to remove the unwanted slashes("\") and double quotes("")
			
			//Removing escape characters
			String jsonFormattedString = jsonObj.toJSONString().replace("\\\"", "\"");
			System.out.println(jsonFormattedString); 
			System.out.println("-------------------------------");
			
			//Removing double quotes from both the sides
			String finalJsonString = jsonFormattedString.replace("\"{", "{").replace("}\"", "}");
			System.out.println(finalJsonString);
			System.out.println("-------------------------------");
			
			//Write to file (Method 1)
			PrintWriter out = null;
			try {
			    out = new PrintWriter(new FileWriter
			    		(System.getProperty("user.dir") + "/multipleCustomerDetails1.json"));
			    out.write(finalJsonString);
			} catch (Exception ex) {
			    System.out.println("error: " + ex.toString());
			}finally{
				out.close();
			}
			
			//Write to file (Method 2)
			FileWriter file1 = new FileWriter
					(System.getProperty("user.dir") + "/multipleCustomerDetails.json");
            file1.write(finalJsonString);
            file1.close();
			
			con.close();
			
			System.out.println("Done!!");
			
					
					
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	
		
	}
}
