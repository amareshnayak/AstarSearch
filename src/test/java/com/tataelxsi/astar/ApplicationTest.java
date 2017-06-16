package com.tataelxsi.astar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */

/**
 * @author amaresh 15-Jun-2017
 */
public class ApplicationTest {
	String smallFilename = "src//main//resources//config//small.txt";
	String largeFilename = "src//main//resources//config//large.txt";

	// Test Happy scenario for input reader file for small text file
	@Test
	public void testInputFileReader() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(smallFilename));
		int lines = 0;
		while (reader.readLine() != null)
			lines++;
		reader.close();
		Assert.assertEquals(InputFileReader.readLargeMapFile(smallFilename).size(), lines);
	}

	

}
