package com.playground.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.playground.Interceptor.StopWatchInterceptor;
import com.playground.file.FileReaderWrite;

import ca.uhn.fhir.rest.api.CacheControlDirective;
import ca.uhn.fhir.rest.client.api.IGenericClient;

/**
 * 
 * @author Kuldeep.Jha
 *
 */
public class PatientResource {

	private static final Logger logeer = LoggerFactory.getLogger(PatientResource.class);
	FileReaderWrite fileReaderWrite = new FileReaderWrite();

	/**
	 * 
	 * @param iGenericClient
	 */
	public void getResource(IGenericClient iGenericClient) {

		logeer.info("Create the object of PatientInfo to get last name");
		BufferedReader bufferedReader;
		String lastName;
		List<Long> avgTimeList = new ArrayList<Long>();
		fileReaderWrite.deleteFile();
		try {
			for (int i = 1; i <= 3; i++) {
				bufferedReader = fileReaderWrite.readFile();
				StopWatchInterceptor stopWatchInterceptor = new StopWatchInterceptor();
				iGenericClient.registerInterceptor(stopWatchInterceptor);
				long avgTime = 0;
				while ((lastName = bufferedReader.readLine()) != null) {
					logeer.info("Search for Patient resources");
					Bundle response = null;
					if (i == 2) {
						response = iGenericClient.search().forResource("Patient")
								.cacheControl(new CacheControlDirective().setNoCache(true)).sort()
								.ascending(Patient.NAME).where(Patient.FAMILY.matches().value(lastName))
								.returnBundle(Bundle.class).execute();
					} else {
						response = iGenericClient.search().forResource("Patient")
								.where(Patient.FAMILY.matches().value(lastName)).returnBundle(Bundle.class).execute();
					}

					avgTime = avgTime + stopWatchInterceptor.getDuration();
					logeer.debug("Time Duration:{}", stopWatchInterceptor.getDuration());
					sortpatientList(getPatientList(response));
				}
				avgTimeList.add(avgTime / 20);
			}
			fileReaderWrite.writeFileFromResources(avgTimeList);
		} catch (IOException e) {
			logeer.error("error message:{}", e.getMessage());
		}

	}

	/**
	 * 
	 * @return
	 */
	private List<Patient> getPatientList(Bundle response) {

		logeer.info("store Patient information in a Patient list");
		List<Patient> patientList = new ArrayList<>();
		for (BundleEntryComponent entry : response.getEntry()) {
			Resource res = entry.getResource();
			if (res instanceof Patient) {
				patientList.add((Patient) res);
			}
		}
		logeer.debug("patientList: {}", patientList);
		return patientList;
	}

	private void sortpatientList(List<Patient> patientLists) {

		logeer.debug("sorting Patient information in a Patient list:{}", patientLists);
		Collections.sort(patientLists, new NameComparator());
		try {
			fileReaderWrite.patientWriteFile(patientLists);
		} catch (IOException e) {
			logeer.error(e.getMessage());
		}
	}

}
