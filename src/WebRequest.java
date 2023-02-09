import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class WebRequest {
	String urlString;
	String data;
	URL url;
	public WebRequest(String urlString){
		this.urlString = urlString;
		makeUrl();
	}
	private void makeUrl() {
		try {
			URL url = new URL(urlString);
			this.url = url;
		} catch (MalformedURLException e) {
			System.out.println("WebRequest.makeUrl() error: " + e.getMessage());
		}
	}
	
	private void getData() {
		if(this.url != null) {
			try {
				StringBuilder gottenData = new StringBuilder();
				Scanner input = new Scanner(url.openStream());
				while(input.hasNext()) {
					gottenData.append(input.next());
				}
				this.data = gottenData.toString();
			} catch (Exception e) {
				// TODO: handle this.
			}
		}
		
	}
	
}
