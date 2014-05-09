package Res.Data;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

/**
 * DataWrite class helps you save a data file which contains your events you need to attend to, or your plannings.
 *
 * @author Szatmari Koppany Gergo
 */

public class DataWrite{
	/**
	 * The given file's name.
	 */
	private String fileName="";
	/**
	 * The given list of data.
	 */
	private List<String> data=new ArrayList<String>();
	
	/**
	 * Writing data
	 * @param fileName The given file's name.
	 * @param data List to save data.
	 */	
	public DataWrite(String fileName,List<String> data){
		try{
			FileOutputStream fileStream=new FileOutputStream(fileName);
			PrintStream printStream=new PrintStream(fileStream);
			
			Iterator it=data.iterator();
			while(it.hasNext()){
			  String value=(String)it.next();
			  printStream.print(value);
			  printStream.println();
			}
			printStream.close();
		} catch(FileNotFoundException e){}
	}
}
