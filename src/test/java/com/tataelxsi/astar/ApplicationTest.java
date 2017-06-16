package com.tataelxsi.astar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

/**
 * 
 */

/**
 * @author amaresh 15-Jun-2017
 */
public class ApplicationTest {
	String fileName = "src//test//resources//config//large.txt";

	// Test Happy scenario for input reader file for small text file
	@Test()
	@Parameters(name = "Test Happy scenario for input reader file size for small text file")
	public void test_case1() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		int lines = 0;
		while (reader.readLine() != null)
			lines++;
		reader.close();
		Assert.assertEquals(InputFileReader.readLargeMapFile(fileName).size(), lines);

	}

	// Test Happy scenario for input reader file for large text file
	@Test()
	@Parameters(name = "Test Happy scenario for input reader file size for large text file")
	public void test_case2() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		int lines = 0;
		while (reader.readLine() != null)
			lines++;
		reader.close();
		Assert.assertEquals(InputFileReader.readLargeMapFile(fileName).size(), lines);
	}
	
	
	@Test()
	@Parameters(name = "Test path trace from source to dest of large file")
	public void test_case3() throws IOException {
		Application app=new Application();
		app.readAndCalculateScore(fileName);
		app.drawPathToDestination();
		
	}

	@Test()
	@Parameters(name = "Test source and destination as @ and X are present in file")
	public void test_case4() throws IOException {

		int source = 0;
		int endPoint = InputFileReader.readLargeMapFile(fileName).size() - 1;
		Assert.assertEquals(InputFileReader.readLargeMapFile(fileName).get(source).contains("@"), true);

		Assert.assertEquals(InputFileReader.readLargeMapFile(fileName).get(endPoint).contains("X"), true);
		
	}

}
