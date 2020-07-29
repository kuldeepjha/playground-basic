
package com.playground;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.playground.resources.PatientResource;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;

/**
 * 
 * @author Kuldeep.Jha
 *
 */
public class SampleClient {

	private static final Logger logeer = LoggerFactory.getLogger(SampleClient.class);

	/**
	 * 
	 * @param theArgs
	 */
	public static void main(String[] theArgs) {

		logeer.info("Create a FHIR client");
		FhirContext fhirContext = FhirContext.forR4();
		logeer.debug("RestfulGenericClient: {}", "http://hapi.fhir.org/baseR4");
        IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
        client.registerInterceptor(new LoggingInterceptor(false));

		logeer.info("Create the object of PatientResource to call getResourece");
		PatientResource patientResource = new PatientResource();
		patientResource.getResource(client);

	}

}
