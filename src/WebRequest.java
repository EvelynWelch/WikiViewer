import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

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
	
	public WebRequest(String urlString){
		this.urlString = urlString;
		this.url = makeUrl();
		this.data = getRequestData();
	}
	private URL makeUrl() {
		try {
			URL url = new URL(urlString);
			return url;
		} catch (MalformedURLException e) {
			System.out.println("WebRequest.makeUrl() error: " + e.getMessage());
			return null;
		}
	}
	
	public static URL urlFactory(String urlString) {
		try {
			URL url = new URL(urlString);
			return url;
		} catch (MalformedURLException e) {
			System.out.println("WebRequest.urlFactory() error: " + e.getMessage());
			return null;
		}
	}
	
	private String getRequestData() {
		if(this.url != null) {	
			try {
				StringBuilder gottenData = new StringBuilder();
				Scanner input = new Scanner(url.openStream());
				while(input.hasNext()) {
					gottenData.append(input.next());
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
	
	public static String getDataFromUrl(URL url) {
		if(url != null) {	
			try {
				StringBuilder gottenData = new StringBuilder();
				Scanner input = new Scanner(url.openStream());
				while(input.hasNext()) {
					gottenData.append(input.next());
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
	
}
