package com.virtualproject.virtualDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;

import com.google.common.collect.ObjectArrays;

public class TestHelper  {
	static JSONObject map;
	
	public static Object[][]  ReadInputData() throws IOException {

		String[][] Data = null;
		Object[][] Data1 = null;
		Map<String, Object> map = new HashMap<String, Object>();
		List<String[]> rowList = new ArrayList<String[]>();
		int k = 0;
		
		
		XSSFSheet ExcelWSheet;

		XSSFWorkbook ExcelWBook;

		

		try {
				File dataFile = new File(System.getProperty("user.dir")+ "/test-data.xlsx");
				FileInputStream fisDataFile = new FileInputStream(dataFile);
				ExcelWBook = new XSSFWorkbook(fisDataFile);
				ExcelWSheet = ExcelWBook.getSheetAt(0);
				XSSFRow Row = ExcelWSheet.getRow(0); // get my Row which start
														// from 0
				int RowNum = ExcelWSheet.getPhysicalNumberOfRows();// count my
				int ColNum = 6;// Row.getLastCellNum(); // get last ColNum
				
				Data = new String[RowNum - 1][ColNum]; // pass my count data in
				// System.out.println("===> " + Data.length);
				DataFormatter formatter = new DataFormatter();
				for (int i = 0; i < RowNum - 1; i++) // Loop work for Rows
				{
					XSSFRow row = ExcelWSheet.getRow(i + 1);

					for (int j = 0; j < ColNum; j++) // Loop work for colNum
					{
						if (row == null)
							Data[i][j] = "";
						else {
							XSSFCell cell = row.getCell(j);
							if (cell == null)
								Data[i][j] = ""; // if it get Null value it pass
													// no data
							else {
								String value = formatter.formatCellValue(cell);
								
								Data[i][j] = value; 
								//System.out.println("i :" + i + " J :" + j);
								//System.out.println("Valures :: " + Data[i][j]);
							}
						}

					}
					
					//System.out.println("Valures :: " + Data[i]);
					 rowList.add((String[])Data[i]);
					map.put(String.valueOf(k), Data[i]);
					k++;
				
				}

			
			
			  System.out.println("Data length" + rowList.size());
			  int dynamicSize = rowList.size();
			  Data1 = new Object[dynamicSize][6];
			int u = 0;
			 for (String[] row : rowList) {
					 System.out.println("Row = " + Arrays.toString(row));
					 System.out.println("Row = " + Arrays.toString(row).length());
				       for(int y =0; y<row.length;y++){
				    	 //  System.out.println("Row = " + row[y].toString());
				    	  // System.out.println("u : "+ u + "y : " + y);
				    	   Data1[u][y]=row[y].toString();
				       }
				    u++;
			    } // prints:
			}

		catch (FileNotFoundException e) {

			System.out.println("Could not read the Excel sheet");

			e.printStackTrace();

		}

		catch (IOException e) {

			System.out.println("Could not read the Excel sheet");

			e.printStackTrace();

		}

		return Data1;

	}

	public static String getOperationName(String operationXmlName ) {
		String opXmlName = operationXmlName;
		String opName[] = operationXmlName.split("_");
		return opName[1];
	}
	
	public static JSONObject setJsonFormat( String full_name, String company, String job_title, String email , String message ) {
		 map = new JSONObject();
		 map.put("apiAction", "userRegistration");
		 map.put("full_name", full_name);
		 map.put("email", email);
		 map.put("company", company);
		 map.put("message", message);
		 map.put("job_title", job_title);
		 return map;
	}
	
	public static ResultSet dataBasePreparation() throws SQLException, ClassNotFoundException {
		String dbUrl ="jdbc:mysql://127.0.0.1:3306/ionic_db_api";
		String username ="root";
		String password ="";
		Connection con = DriverManager.getConnection(dbUrl,username,password);
		Class.forName("com.mysql.jdbc.Driver");
		String query = "select *  from registration;";
		
		Statement stmt = con.createStatement();
		ResultSet rs= stmt.executeQuery(query);
		con.close();
		
		return rs;
	}
	
}
