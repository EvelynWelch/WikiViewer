import java.util.ArrayList;
import java.util.HashMap;

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
// }
// the actual file has not new lines in it. it all is on a single line.
/**
 * Processes the text response from http://api.zippopotam.us/us/{ZIPCODE} and
 * turns its into a HashMap<String, String>.
 */
public class ResponseParser {
	String responseString;
	HashMap<String, String> parsedData;

	/**
	 * Constructor
	 * 
	 * @param responseString: String - the text response from
	 *                        http://api.zippopotam.us/us/{ZIPCODE}
	 */
	public ResponseParser(String responseString) {
		this.responseString = responseString;
		this.parsedData = processResponse(this.responseString);
	}

	/**
	 * Getter for ResponseParser.resonponseString
	 * 
	 * @return - String
	 */
	public String getResponseString() {
		return responseString;
	}

	/**
	 * Getter for ResonseParser.parsedData
	 * 
	 * @return - HashMap<String, String>
	 */
	public HashMap<String, String> getParsedData() {
		return parsedData;
	}

	/**
	 * Reads the text response from http://api.zippopotam.us/us/{ZIPCODE} and turns
	 * it into key value pairs. This function will fail if it encounters nested
	 * arrays. It will turn even elements to keys, and odd elements to values.
	 * 
	 * @param responseString: String - plain text response from
	 *                        http://api.zippopotam.us/us/{ZIPCODE}
	 * @return HashMap<String, String> - key value
	 */
	public static HashMap<String, String> processResponse(String responseString) {
		// NOTE: this hits an array that has 1 element in it, it just ignore the that
		// it's an array of objects and
		// adds the key, value pairs of the object to the the HashMap. This should do
		// this for all object in the array
		// If it hits a [][] it will run into a logic error.
//		System.out.println("responseString: ");
//		System.out.println(responseString);
//		System.out.println("");
		String splitData[] = responseString.split(",");
		ArrayList<String[]> data = new ArrayList<String[]>();
		HashMap<String, String> procData = new HashMap<String, String>();
		// clean up the data
		for (int i = 0; i < splitData.length; i++) {
//			System.out.println("splitData: " + splitData[i]);
			String[] d = splitData[i].split(":");
			for (int j = 0; j < d.length; j++) {
//				System.out.println("");
//				System.out.println(d[j]);
				d[j] = strStripper(d[j]);
			}
			data.add(i, d);
		}
		// add the key, value pairs to the HashMap
		for (String[] sa : data) {
			if (sa.length == 2) {
				procData.put(sa[0], sa[1]);
			} else if (sa.length == 3) {
				// this one will be [0] == places [1] == place name [2] == {VAL}
				procData.put(sa[1], sa[2]);
			}
		}
		return procData;
	}

	/**
	 * Removes \ { } [ ] " and trailing / leading spaces from a String.
	 * 
	 * @param str: String
	 * @return String - the cleaned up string.
	 */
	public static String strStripper(String str) {
		String newStr = str;
		newStr = newStr.replace("\"", "");
		newStr = newStr.replace("{", "");
		newStr = newStr.replace("}", "");
		newStr = newStr.replace("[", "");
		newStr = newStr.replace("]", "");
		newStr = newStr.stripLeading();
		newStr = newStr.stripTrailing();
		return newStr;
	}
}
