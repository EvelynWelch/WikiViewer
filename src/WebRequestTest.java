import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;

import org.junit.jupiter.api.Test;

class WebRequestTest {
	String testUrlString = "http://api.zippopotam.us/us/90210";

	@Test
	void testCreateWebRequest() {
		WebRequest testRequest = new WebRequest(testUrlString);
		assertEquals(testRequest.getUrlString(), testUrlString);
		// These return null if there is a problem getting the data, if they aren't null
		// this test assumes they successfully got the data.
		assertNotEquals(testRequest.getUrl(), null);
		assertNotEquals(testRequest.getData(), null);
	}
	@Test
	void testUrlFactory() {
		URL testUrl = WebRequest.urlFactory(testUrlString);
		assertNotEquals(testUrl, null);
		
	}
	@Test
	void testGetDataFromUrl() {
		URL testUrl = WebRequest.urlFactory(testUrlString);
		String requestData = WebRequest.getDataFromUrl(testUrl);
		assertNotEquals(requestData, null);
	}

}
