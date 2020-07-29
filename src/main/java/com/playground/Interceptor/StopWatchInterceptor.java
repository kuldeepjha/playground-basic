package com.playground.Interceptor;

import java.io.IOException;

import ca.uhn.fhir.rest.client.api.IClientInterceptor;
import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;
import ca.uhn.fhir.util.StopWatch;

/**
 * 
 * @author Kuldeep.Jha
 *
 */
public class StopWatchInterceptor implements IClientInterceptor {

	StopWatch stopWatch;
	long duration;

	/**
	 * 
	 */
	@Override
	public void interceptRequest(IHttpRequest theRequest) {
		stopWatch = new StopWatch();
	}

	/**
	 * 
	 */
	@Override
	public void interceptResponse(IHttpResponse theResponse) throws IOException {
		duration = stopWatch.getMillis();
	}

	public StopWatch getStopWatch() {
		return stopWatch;
	}

	public void setStopWatch(StopWatch stopWatch) {
		this.stopWatch = stopWatch;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
	
}