package com.palyground.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;

/**
 * 
 * @author Kuldeep.Jha
 *
 */
public class SampleClientTest {

	IGenericClient client = null;

	/**
	 * 
	 */
	@Before
	public void RestfulGenericClient() {

		FhirContext fhirContext = FhirContext.forR4();
		client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
		client.registerInterceptor(new LoggingInterceptor(false));
	}

	/**
	 * 
	 */
	@Test
	public void test() {
		assertNotNull(client);
	}

}
