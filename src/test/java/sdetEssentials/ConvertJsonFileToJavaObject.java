package sdetEssentials;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertJsonFileToJavaObject {
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		//using Jackson API
		ObjectMapper om = new ObjectMapper();
		
		File jsonFile = new File(System.getProperty("user.dir") + "/customerDetails.json");
		
		//Convert JSON File to Java object
		CustomerDetails cd = om.readValue(jsonFile, CustomerDetails.class);
		System.out.println(cd.getBookName());
		System.out.println(cd.getPurchaseDate());
		System.out.println(cd.getAmount());
		System.out.println(cd.getLocation());

		
	}
	
	

}
