package com.tataelxsi.astar;
/**
 * 
 */

/**
 * @author amaresh
 * 15-Jun-2017
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputFileReader {
	static int lineCount;
	static char tempMatrix[][];
	/**
	 * @return The lineCount of the input file
	 */
	public static int getLineCount() {
		return lineCount;
	}

	/**
	 * @param lineCount the lineCount to set
	 */
	public static void setLineCount(int lineCount) {
		InputFileReader.lineCount = lineCount;
	}
	
public static List<String> readLargeMapFile(String filename){	
	BufferedReader br=null;
		FileReader fr=null;
		List<String> records = new ArrayList<String>();
		try
		  {
			fr=new FileReader(filename);
		    br = new BufferedReader(fr);
		    String line;
		    
		    while ((line = br.readLine()) != null)
		    {
		    	lineCount++;
		      records.add(line);
		    }
		   
		    setLineCount(lineCount);
		    
		    br.close();
		    return records;
		  }catch (Exception e){
		    System.err.format("Exception occurred trying to read '%s'.", filename);
		    
		    e.printStackTrace();
		    return null;
		  }
		}
	
	protected Map <String,Coordinates> getLocationPoint(List<String> records, char target,String key){		
		Map <String,Coordinates> multiplePoints=new HashMap<String,Coordinates>();		
		String temp="";
		char str=0;
		for(int i=0;i<records.size();i++){			
			    temp=records.get(i);
			    for(int j=0;j<temp.length();j++){
			    	str=temp.charAt(j);
			    	if(str== target){			    		        
			    		 multiplePoints.put(key, new Coordinates(i, j));
			    	}
			    }	
			    
		}		
		return multiplePoints;
	}
	
	//It will convert each grid to with score resp.
	public void convertGridValueToWeight(List<String> records, Utility util){	
		System.out.println("Input file for processing for best path ");
		String temp="";
		char str=0;	
		//before conversion take place  backed up for trace of path source to destination 
		tempMatrix = new char[getLineCount()][getLineCount()];
		for (int i = 0; i < records.size(); i++) {
			temp=records.get(i);
			for (int j = 0; j < temp.length(); j++) {
				tempMatrix[i][j]=temp.charAt(j);
			}
		}
		for(int i=0;i<records.size();i++){	
			    temp=records.get(i);
			    System.out.println(temp);
			    for(int j=0;j<temp.length();j++){
			    	str=temp.charAt(j);
			    	if(str== '@' ||str=='X'||str=='.'){				    		 
			    		 util.grid[i][j]=1;
			    	}else if(str=='*'){			    		
			    		util.grid[i][j]=2;
			    	}else if(str=='^'){
			    		util.grid[i][j]=3;
			    	}else if(str=='~'){
			    		util.grid[i][j]=0;
			    	}else{
			    		util.grid[i][j]=-1;
			    	}
			    		
			    }		
			    
		}	
		
		
	}
 }


