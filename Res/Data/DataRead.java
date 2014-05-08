package Res.Data;

import java.util.List;
import java.util.ArrayList;
import java.io.*;

/**
 * DataRead class helps you read data from saved data file which contains your events you need to attend to, or your plannings.
 *
 * @author Szatmari Koppany Gergo
 */

public class DataRead{
	/**
	 * The given file's name.
	 */
	private String fileName="";
	/**
	 * The given list of data.
	 */
	private List<String> data=new ArrayList<String>();
	
	/**
	 * Reading data
	 * @param fileName The given file's name.
	 * @param data List for read data.
	 */
	public DataRead(String fileName,List<String> data) throws Exception{
		try{
			FileInputStream fileStream=new FileInputStream(fileName);
			BufferedReader inPut=new BufferedReader(new InputStreamReader(fileStream));
			String stringLine;
			stringLine=inPut.readLine();
		
			while(stringLine!=null){
				data.add(stringLine);
			}
			inPut.close();
		} catch (FileNotFoundException e){
		} catch (IOException e){}
		
	}
}
