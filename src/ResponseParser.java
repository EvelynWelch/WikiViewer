import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;  
// This is tailored specifically to the responses from http://api.zippopotam.us/us/{ZIPCODE}
// {
//	"post code": "90210", 
//	"country": "United States",
//	"country abbreviation": "US",
//	"places": [{
//		"place name": "Beverly Hills",
//		 "longitude": "-118.4065", 
//		 "state": "California", 
//		 "state abbreviation": "CA", 
//		 "latitude": "34.0901"
//		}
//	 ]
//}
// the actual file has not new lines in it. it all is on a single line.
public class ResponseParser {
	String responseString;
	HashMap<String, String> parsedData;
	
	public ResponseParser(String responseString) {
		this.responseString = responseString;
		this.parsedData = processResponse(this.responseString);
	}
	
	public String getResponseString() {
		return responseString;
	}
	public Object getParsedData() {
		return parsedData;
	}
	public static HashMap<String, String> processResponse(String responseString) {
		// NOTE: this hits an array that has 1 element in it, it just ignore the that it's an array of objects and
		// 		 adds the key, value pairs of the object to the the HashMap. This should do this for all object in the array
		//		 If it hits a [][] it will run into a logic error.
		String splitData[] = responseString.split(",");
		ArrayList<String[]> data = new ArrayList<String[]>();
		HashMap<String, String> procData = new HashMap<String, String>();
		// clean up the data
		for(int i = 0; i < splitData.length; i++) {
			String[] d = splitData[i].split(":");
			for(int j = 0; j < d.length; j++) {
				d[j] = strStripper(d[j]);
			}
			data.add(i, d);
		}
		// add the key, value pairs to the HashMap
		for(String[] sa : data) {
			if(sa.length == 2) {
				procData.put(sa[0], sa[1]);
			} else if(sa.length == 3) {
				// this one will be [0] == places [1] == place name [2] == {VAL}
				procData.put(sa[1], sa[2]);
			}
		}
		return procData;
	}
	
	public static String strStripper(String str) {
		String newStr = str;	
		newStr = newStr.replace("\"", "");
		newStr = newStr.replace("{", "");
		newStr = newStr.replace("}", "");
		newStr = newStr.replace("[", "");
		newStr = newStr.replace("]", "");
		newStr = newStr.stripTrailing();
		newStr = newStr.stripLeading();
		return newStr;
	}	
}
