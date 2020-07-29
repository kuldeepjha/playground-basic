package com.playground.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import org.hl7.fhir.r4.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Kuldeep.Jha
 *
 */
public class FileReaderWrite {

	private static final Logger logeer = LoggerFactory.getLogger(FileReaderWrite.class);
	private String fileName = "patientLastName.txt";
	private String patientDetails = "patientDetails.csv";
	private String avgTimeFile = "avgTimeFile.txt";
	ClassLoader classLoader = this.getClass().getClassLoader();
	private FileWriter csvWriter = null;

	/**
	 * 
	 * @return
	 */
	public BufferedReader readFile() {

		logeer.info("file name: {}", "patientLastName.txt");
		FileInputStream inputStream = getFileFromResources();
		logeer.debug("inputStream: {}", inputStream);
		return new BufferedReader(new InputStreamReader(inputStream));
	}

	/**
	 * 
	 * @return
	 */
	private FileInputStream getFileFromResources() {

		logeer.info("getting ClassLoader object");
		logeer.info("getting resource(file) from class loader");
		File configFile = new File(classLoader.getResource(fileName).getFile());
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(configFile);
			return inputStream;
		} catch (FileNotFoundException e) {
			logeer.error("error message:{}", e.getMessage());
		}
		return inputStream;

	}

	/**
	 * 
	 * @return
	 */
	public void writeFileFromResources(List<Long> avgTimeList) {

		BufferedWriter writer;
		File avgTimeFiles = new File(classLoader.getResource(avgTimeFile).getFile());
		int count = 1;
		try {
			writer = new BufferedWriter(new FileWriter(avgTimeFiles, true));
			writer.newLine();
			for (Long avgTime : avgTimeList) {
				writer.write("***Avg Time for run " + count + " = " + avgTime + " milliseconds ***");
				writer.newLine();
				count++;
			}
			writer.close();
		} catch (IOException e) {
			logeer.error(e.getMessage());
		}
	}

	/**
	 * 
	 * @param list
	 * @throws IOException
	 * @throws Exception
	 */
	public void patientWriteFile(List<Patient> patientLists) throws IOException {

		csvWriter = new FileWriter(patientDetails);
		File patientFile = new File(classLoader.getResource(patientDetails).getFile());

		if (patientFile.exists())
			csvWriter = new FileWriter(patientFile, true);
		else
			csvWriter = new FileWriter(patientFile);
		csvWriter.append("Birth Date:");
		csvWriter.append(",");
		csvWriter.append("First Name:");
		csvWriter.append(",");
		csvWriter.append("Last Name");
		csvWriter.append(",");
		for (Patient patient : patientLists) {
			csvWriter.append("\n");
			if (patient.getBirthDate() != null)
				csvWriter.append(patient.getBirthDate() + "");
			csvWriter.append(",");
			if (patient.getName().get(0).getGivenAsSingleString() != null)
				csvWriter.append(patient.getName().get(0).getGivenAsSingleString());
			csvWriter.append(",");
			if (patient.getName().get(0).getFamily() != null)
				csvWriter.append(patient.getName().get(0).getFamily());
			csvWriter.append(",");

			logeer.info("Birth Date:{}", patient.getBirthDate());
			logeer.info("First Name:{}", patient.getName().get(0).getGivenAsSingleString());
			logeer.info("Last Name:{}", patient.getName().get(0).getFamily());
		}
		csvWriter.append("\n");
		csvWriter.flush();
		csvWriter.close();
	}

	public void deleteFile() {

		try {
			File patientFile = new File(classLoader.getResource(patientDetails).getFile());
			FileWriter fwOb = new FileWriter(patientFile, false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			logeer.error(e.getMessage());
		}
	}
}
