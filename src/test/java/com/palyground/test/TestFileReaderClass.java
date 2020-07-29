package com.palyground.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import org.junit.Test;

import com.playground.file.FileReaderWrite;

/**
 * 
 * @author Kuldeep.Jha
 *
 */
public class TestFileReaderClass {

	FileReaderWrite fileReaderClass = new FileReaderWrite();
	private String fileName = "patientLastName.txt";

	@Test
	public void fileReaderTest() {
		assertNotNull(fileReaderClass.readFile());
	}

	@Test
	public void fileFromResourcesTest() {
		assertNotNull(getFileFromResources());
	}

	@Test
	public void bufferedReadeTtest() {
		BufferedReader bufferedReader = fileReaderClass.readFile();
		assertEquals(bufferedReader.getClass().getName(), readFile().getClass().getName());
	}

	/**
	 * 
	 * @return
	 */
	private BufferedReader readFile() {
		FileInputStream inputStream = getFileFromResources();
		return new BufferedReader(new InputStreamReader(inputStream));
	}

	/**
	 * 
	 * @return
	 */
	private FileInputStream getFileFromResources() {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File configFile = new File(classLoader.getResource(fileName).getFile());
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(configFile);
			return inputStream;
		} catch (FileNotFoundException e) {
			e.getMessage();
		}
		return inputStream;
	}

}