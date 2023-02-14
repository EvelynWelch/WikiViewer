import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;

class CacheData implements Serializable {
	/**
	 * 
	 */
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
		if(hasZipCode(zipCode)) {
			HashMap<String, String> zipCodeData = getZipCode(zipCode);
			zipCodeData.forEach((key, value) -> {
				System.out.printf("%s: %s %n", key, value);
				
			});
		} else {
			System.out.printf("Zip code: %s not found", zipCode);
		}
	}
}


public class ZipCodeQuerier {
//	String baseUrlString = "http://api.zippopotam.us/us/";
//	String cacheFilePath = "./ZipCodeCache";
//	DataStore cache = new DataStore(cacheFilePath);
//	String urlString;
//	String zipCode;
//	WebRequest request;
	
	public static void main(String[] args) {
		String baseUrlString = "http://api.zippopotam.us/us/";
		String cacheFilePath = "./ZipCodeCache";
		
		DataStore ds = new DataStore(cacheFilePath);
		CacheData cache = (CacheData)ds.read();
		if(cache == null) {
			System.out.println("cache is null");
			cache = new CacheData();
		}
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter a zip code: ");
		String zipCode = input.nextLine();
		if(cache.hasZipCode(zipCode)) {
			System.out.println("found a cached response");
			cache.printZipCode(zipCode);
			
		} else {
			// Make a web request.
			String urlString = baseUrlString + zipCode;
			WebRequest wr = new WebRequest(urlString);
			String responseData = wr.getData();
			if(responseData.equals("{}")) {
				System.out.printf("Zip code: %s was not found", zipCode);
			} else {
				HashMap<String, String> parsedData = ResponseParser.processResponse(responseData);
				cache.addZipCode(zipCode, parsedData);
				cache.printZipCode(zipCode);
				ds.create(cache);
			}
		}
		input.close();
		
		
	}

}
