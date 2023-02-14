import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A class that is used as a cache with DataStore
 */
class CacheData implements Serializable {
	private static final long serialVersionUID = 1L;
	HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String, String>>();

	public boolean hasZipCode(String zipCode) {
		return data.containsKey(zipCode);
	}

	public HashMap<String, String> getZipCode(String zipCode) {
		return data.get(zipCode);
	}

	public void addZipCode(String zipCode, HashMap<String, String> zipCodeData) {
		data.put(zipCode, zipCodeData);
	}

	public void deleteZipCode(String zipCode) {
		data.remove(zipCode);
	}

	public void printZipCode(String zipCode) {
		if (hasZipCode(zipCode)) {
			HashMap<String, String> zipCodeData = getZipCode(zipCode);
			zipCodeData.forEach((key, value) -> {
				int tabLength = 25;
				tabLength = tabLength - key.length();
				StringBuilder tab = new StringBuilder();
				for(int i = 0; i < tabLength; i++) {
					tab.append(" ");
				}
				System.out.printf("%s:%s %s %n", key, tab, value);

			});
		} else {
			System.out.printf("Zip code: %s not found", zipCode);
		}
	}
}

/**
 * Uses WebRequest to query http://api.zippopotam.us/us/{ZIPCODE} then processes
 * the data with ResponseParser finally maintains a local cache with DataStore
 */
public class ZipCodeQuerier {
	/**
	 * Main loop for the program to run.
	 */
	public static void main(String[] args) {
		// api endpoint
		String baseUrlString = "http://api.zippopotam.us/us/";
		// local cache file path
		String cacheFilePath = "./ZipCodeCache";

		// instantiate a DataStore
		DataStore ds = new DataStore(cacheFilePath);
		// open up the cache, creating a new one if it doens't exist
		CacheData cache = (CacheData) ds.read();
		if (cache == null) {
			System.out.println("Cache not found. Creating new cache at: " + cacheFilePath);
			System.out.println("");
			cache = new CacheData();
		}
		// instantiate Scanner to get input from users.
		Scanner input = new Scanner(System.in);
		// Get a zip code from the user.
		System.out.print("Enter a zip code: ");
		String zipCode = input.nextLine();
		System.out.println("");
		// check if the zip code has been cached.
		if (cache.hasZipCode(zipCode)) {
			System.out.println("found a cached response");
			System.out.println("");
			cache.printZipCode(zipCode);
		} else {
			// if it hasn't been cached make a web request.
			String urlString = baseUrlString + zipCode;
			WebRequest wr = new WebRequest(urlString);
			String responseData = wr.getData();
			// make sure you get a valid response from the end point.
			if (responseData.equals("{}")) {
				System.out.printf("Zip code: %s was not found", zipCode);
			} else {
				// Turn the response into a HashMap and add it to the cache.
				HashMap<String, String> parsedData = ResponseParser.processResponse(responseData);
				cache.addZipCode(zipCode, parsedData);
				cache.printZipCode(zipCode);
				ds.create(cache);
			}
		}
		// close Scanner
		input.close();
	}
}
