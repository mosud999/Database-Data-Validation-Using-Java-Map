package com.datavalidation.using.java.map;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;

public class DataValidationTest {

	@Test
	public void executingDataValidationTestScript() {
		
		// Test Execution will be started based on the below method using TestNG @Test annotation;
		expectedAndActualDataValidation();
	}
	
	public void expectedAndActualDataValidation() {

		// Calling expectedMap() and actualMapDB() and storing into map;
		Map expected_result_map = expectedMap();
		Map actual_result_map = actualMapDB();

		// Traversing expected and actual elements using Iterator;
		Iterator expected_iterator = expected_result_map.entrySet().iterator();
		Iterator actual_iterator = actual_result_map.entrySet().iterator();

		// Validating expected and actual result one by one;
		while (expected_iterator.hasNext() && actual_iterator.hasNext()) {
			Map.Entry expected_pair = (Map.Entry) expected_iterator.next();
			Map.Entry actual_pair = (Map.Entry) actual_iterator.next();

			if ((expected_pair.getKey()).equals((actual_pair.getKey()))) {
				if ((expected_pair.getValue()).equals((actual_pair.getValue()))) {
					System.out.println(
							actual_pair.getKey() + " = " + actual_pair.getValue() + ", Matched With Expected Result.");
				} else {
					System.out.println("!!!!!! " + actual_pair.getKey() + " = " + (actual_pair.getValue())
							+ ", Shoe Price Not Matched With Expected Result!!!!!!");
				}
			} else {
				System.out.println(
						"!!!!!! Shoe Name =  " + actual_pair.getKey() + ", Not Matched With Expected Result!!!!!!");
			}
		}
	}

	// Expected Result (shoe_name and shoe_price);
	public Map<String, Double> expectedMap() {

		Map<String, Double> expected_result_map = new LinkedHashMap<String, Double>();
		
		expected_result_map.put("Red meerkat", 4923.23);
		expected_result_map.put("Hoopoe, eurasian", 4884.13);
		expected_result_map.put("Turkey vulture", 4839.56);
		expected_result_map.put("Javan gold-spotted mongoose", 4838.07);
		expected_result_map.put("Pied cormorant", 4831.59);
		expected_result_map.put("Sally lightfoot crab", 4259.43);
		expected_result_map.put("Raccoon, common", 4104.94);
		expected_result_map.put("Violet-crested turaco", 3406.61);
		expected_result_map.put("Sloth, two-toed tree", 3302.98);
		expected_result_map.put("Wallaby, river", 3176.38);
		expected_result_map.put("Peregrine falcon", 3083.00);
		expected_result_map.put("Butterfly, tropical buckeye", 3066.71);

		return expected_result_map;
	}

	// Actual Result (shoe_name and shoe_price from Database);
	public Map<String, Double> actualMapDB() {

		Map<String, Double> actual_result_map = new LinkedHashMap<String, Double>();

		try {
			// Registering driver class;
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Establishing Database Connection using Database Name = "stadium_good", User = "stadium", Password = "good";
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stadium_good", "stadium", "good");
			Statement stmt = con.createStatement();
			
			// SQL Query to be executed;
			String sql = "SELECT shoes.shoe_name, shoes.shoe_price "
					+ "FROM shoes "
					+ "WHERE shoes.shoe_size = 7 AND shoes.date LIKE '2020%'  "
					+ "ORDER BY shoes.shoe_price DESC "
					+ "LIMIT 12";
			
			// Executing SQL query and storing into ResultSet Interface;
			ResultSet rs = stmt.executeQuery(sql);
			
			// Loop through on ResultSet, getting the data and storing into map;
			while (rs.next()) {
				String actual_shoe_name = rs.getString("shoe_name");
				Double actual_shoe_price = rs.getDouble("shoe_price");
				actual_result_map.put(actual_shoe_name, actual_shoe_price);
			}
			
			// Checking and closing connection;
			if (con != null) { 
				con.close(); 
				}

		} catch (Exception e) {
			System.out.println(e);
		}

		return actual_result_map;
	}
}
