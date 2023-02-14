import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class ResponseParserTest {
	// testResp.txt is a text file with the response 
	String testRespFilePath = "./testResp.txt";
	String testResp = getTestString(testRespFilePath);
	
	String getTestString(String filePath) {	
			try {
				File f = new File(filePath);
				Scanner input = new Scanner(f);
				StringBuilder fileData = new StringBuilder();
				while(input.hasNext()) {
					fileData.append(input.nextLine());
				}
				input.close();
				return fileData.toString();		
				
			} catch(Exception e) {
				System.out.println("okay D:");
				e.printStackTrace();
			}
			return null;
		}
	
	@Test
	void testStrStripper() {
		String testString = "\"{[ okay ]}";
		String strippedStringSolution = "okay";
		String strippedString = ResponseParser.strStripper(testString);
		assertEquals(strippedString, strippedStringSolution);
	}
	@Test
	void testProcessResponse() {
		HashMap<String, String> testProcessedResponse = ResponseParser.processResponse(testResp);
		assertEquals(false, testProcessedResponse.keySet().contains("places"));
		assertEquals(8,  testProcessedResponse.keySet().toArray().length);
	}

}
