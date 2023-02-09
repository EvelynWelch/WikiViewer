import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class DataStoreTest {
	String testFilePath = "testDataStoreFile.ser";

	// Delete the test file after every test.
	@AfterEach
	public void deleteFile() {
		File testFile = new File(testFilePath);
		if (testFile.exists()) {
			testFile.delete();
		}
	}

	@Test
	void testRead() {
		// Create the test file so it's not reliant on a method from DataStore
		try {
			TestSerializableData testData = new TestSerializableData();
			FileOutputStream dataFile = new FileOutputStream(testFilePath);
			ObjectOutputStream out = new ObjectOutputStream(dataFile);
			out.writeObject(testData);
			out.close();
			dataFile.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			fail();
		}
		DataStore testDataStore = new DataStore(testFilePath);
		TestSerializableData testReadDataObj = (TestSerializableData) testDataStore.read();
		assertEquals(testReadDataObj.getData1(), 1);
		assertEquals(testReadDataObj.getData2(), "Test String");
	}

	@Test
	void testWrite() {
		DataStore testDataStore = new DataStore(testFilePath);
		TestSerializableData testData = new TestSerializableData();
		boolean dataStored = testDataStore.create(testData);
		assertEquals(true, dataStored);
	}

}

class TestSerializableData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8379256339754047819L;
	int data1 = 1;
	String data2 = "Test String";

	public int getData1() {
		return data1;
	}

	public String getData2() {
		return data2;
	}

}
