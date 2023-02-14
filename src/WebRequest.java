import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Uses Scanner to read the data from a URL
 */
public class WebRequest {
	String urlString;
	String data;
	URL url;

	public URL getUrl() {
		return this.url;
	}

	public String getData() {
		return this.data;
	}

	public String getUrlString() {
		return this.urlString;
	}

	/**
	 * creates a URL from urlString, and sets the response to data.
	 * 
	 * @param urlString: String - a website url ex: wwww.example.com
	 */
	public WebRequest(String urlString) {
		this.urlString = urlString;
		this.url = makeUrl();
		this.data = getRequestData();
	}

	/**
	 * Creates a URL from this.urlString
	 * 
	 * @return URL
	 */
	private URL makeUrl() {
		try {
			URL url = new URL(urlString);
			return url;
		} catch (MalformedURLException e) {
			System.out.println("WebRequest.makeUrl() error: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Creates a URL from input String
	 * 
	 * @param urlString: String - a website url ex: wwww.example.com
	 * @return URL
	 */
	public static URL urlFactory(String urlString) {
		try {
			URL url = new URL(urlString);
			return url;
		} catch (MalformedURLException e) {
			System.out.println("WebRequest.urlFactory() error: " + e.getMessage());
			return null;
		}
	}

	/**
	 * uses Scanner to get the data from the this.url
	 * 
	 * @return String - the response string from the URL
	 */
	private String getRequestData() {
		if (this.url != null) {
			try {
				StringBuilder gottenData = new StringBuilder();
				Scanner input = new Scanner(url.openStream());
				while (input.hasNextLine()) {
					gottenData.append(input.nextLine());
				}

				input.close();
				return gottenData.toString();
			} catch (Exception e) {
				System.out.println("WebRequest.getData() error: " + e.getMessage());
				return null;

			}
		} else {
			return null;
		}
	}

	/**
	 * Uses Scanner to get the string response from a URL
	 * 
	 * @param url: URL
	 * @return String - the response string from the URL
	 */
	public static String getDataFromUrl(URL url) {
		if (url != null) {
			try {
				StringBuilder gottenData = new StringBuilder();
				Scanner input = new Scanner(url.openStream());
				while (input.hasNext()) {
					gottenData.append(input.next()); 
				}
				input.close();
				System.out.println("gottenData: ");
				System.out.println(gottenData);
				System.out.println("");
				return gottenData.toString();
			} catch (Exception e) {
				System.out.println("WebRequest.getData() error: " + e.getMessage());
				return null;

			}
		} else {
			return null;
		}
	}

}
