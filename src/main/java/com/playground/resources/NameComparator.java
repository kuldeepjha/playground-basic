package com.playground.resources;

import java.util.Comparator;

import org.hl7.fhir.r4.model.Patient;

/**
 * 
 * @author Kuldeep.Jha
 *
 */
class NameComparator implements Comparator<Patient> {

	/**
	 * 
	 */
	@Override
	public int compare(Patient patient1, Patient patient2) {

		if (patient2.getName().get(0).getGivenAsSingleString() == null) {
			return (patient1.getName().get(0).getGivenAsSingleString() == null) ? 0 : -1;
		}
		if (patient1.getName().get(0).getGivenAsSingleString() == null) {
			return 1;
		}
		return patient1.getName().get(0).getGivenAsSingleString()
				.compareTo(patient2.getName().get(0).getGivenAsSingleString());
	}
}