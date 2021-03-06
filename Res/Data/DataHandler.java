package Res.Data;

import java.util.*;
import java.awt.Color;
import java.util.zip.DataFormatException;
import java.io.*;

import Res.Bin.*;

/**
 * DataRead class helps you read data from saved data file which contains your events you need to attend to, or your plannings.
 *
 * @author SZKVAAT.SZE
 * @author ZODVAAT.SZE
 */

public class DataHandler{
	
	/**
	 * holds the save file extension
	 */
	public static final String calendarExtension="cal";
	
	/**
	 * Handling special characters.
	 * @param str Given string to be converted.
	 * @author ZODVAAT.SZE
	 */
	private static String escape(String str){
		return str.replaceAll("/","//").replaceAll(",","/c").replaceAll("\n","/n");
	}
	/**
	 * Handling special characters.
	 * @param str Given string to be decripted.
	 * @author ZODVAAT.SZE
	 */
	private static String deescape(String str){
		char[] ch=str.toCharArray();
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<ch.length;i++){
			if(ch[i]=='/'){
				i++;
				switch(ch[i]){
					case 'c':sb.append(','); break;
					case 'n':sb.append('\n'); break;
					case '/':sb.append('/'); break;
				}
			} else {
				sb.append(ch[i]);
			}
		}
		return sb.toString();
	}
	/**
	 * Getting data from given string sample.
	 * @param sampleText Given string to be handled.
	 * @throws DataFormatException Exception cause by corrupted or misformatted data.
	 * @author SZKVAAT.SZE
	 * @author ZODVAAT.SZE
	 */
	private static String[] getFields(String sampleText) throws DataFormatException{
		
		String separates=",";
		StringTokenizer st=new StringTokenizer(sampleText, separates);
		
		if(st.countTokens()!=7){
			throw new DataFormatException();
		}
		String[] ret=new String[7];
		for (int i=0;i<ret.length;i++){
			ret[i]=st.nextToken();
		}
		return ret;
	}
	/**
	 * Reading data from given file converting it to handlable list of data.
	 * @param fileName The given file's name.
	 * @param data List for read data.
	 * @throws IOException Exception for data, which cannot be handled.
	 * @throws DataFormatException Exception for corrupted or misformatted data.
	 * @author ZODVAAT.SZE
	 * @author SZKVAAT.SZE
	 */
	public static void readData(String fileName,List<CalendarEntry> data) throws IOException, DataFormatException{
		BufferedReader inPut=null;
		try{
			inPut=new BufferedReader(new FileReader(fileName));
			
			Interval ival=null;
			String type=null;
			String title=null;
			Color foreColor=null;
			Color backColor=null;
			String desc=null;
			
			String line;
			List<String> typeList=new LinkedList<String>();
			String[] defaults=DataModel.getDefaultTypeArray();
			for (int i=0;i<defaults.length;i++){
				typeList.add(defaults[i]);
			}
			
			while((line=inPut.readLine())!= null){
				String[] fields=getFields(line);
				try{
					long start=Long.parseLong(fields[0]);
					long end=Long.parseLong(fields[1]);
					ival=new Interval(start,end);
					
					type=deescape(fields[2]);
					String typeCopy=type;
					for (String str:typeList){
						if(str.equals(type)){
							type=str;
							break;
						}
					}
					if (type==typeCopy){
						typeList.add(type);
					}
					
					title=deescape(fields[3]);
					foreColor=new Color(Integer.parseInt(fields[4]));
					backColor=new Color(Integer.parseInt(fields[5]));
					desc=deescape(fields[6]);
				} catch(NumberFormatException e){
					throw new DataFormatException();
				}
				
				data.add(new CalendarEntry(ival,type,title,foreColor,backColor,desc));
			}
			
			DataModel.getTypeList().clear();
			DataModel.getTypeList().addAll(typeList);
		}catch(IOException e){
			throw e;
		}finally{
			if (inPut!=null){
				inPut.close();
			}
		}
	}
	/**
	 * Writing data into given file converting a specific list of data into a handlable data model.
	 * @param fileName The given file's name.
	 * @param data List for read data.
	 * @throws IOException Exception for data, which cannot be handled.
	 * @author ZODVAAT.SZE
	 * @author SZKVAAT.SZE
	 */
	public static void writeData(String fileName,EventedList<CalendarEntry> data) throws IOException{
		
		File f=new File(fileName);
		if(!f.exists()){
			f.createNewFile();
		}
		
		PrintWriter out=new PrintWriter(new FileWriter(fileName));
		
		for(CalendarEntry entry:data){
			Interval ival=entry.getInterval();
			out.print(ival.getStart()+",");
			out.print(ival.getEnd()+",");
			out.print(escape(entry.getType())+",");
			out.print(escape(entry.getTitle())+",");
			out.print((entry.getForegroundColor()).getRGB()+",");
			out.print((entry.getBackgroundColor()).getRGB()+",");
			out.print(escape(entry.getDescription()));
			out.println();
		}
		
		out.close();
	}
}
