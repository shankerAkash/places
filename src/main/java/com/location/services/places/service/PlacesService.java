package com.location.services.places.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.location.services.places.adapter.PlacesAdapter;
import com.location.services.places.model.PlacesRequest;
import com.location.services.places.model.PlacesResponse;

@Service
public class PlacesService {
	
	private static final Logger logger = LoggerFactory.getLogger(PlacesService.class);
	
	@Autowired
	private PlacesAdapter placesAdapter;
	
	public ArrayList<PlacesResponse> places(PlacesRequest request) throws ClientProtocolException, IOException, URISyntaxException {
		
		logger.debug("validating the request: " + request);
		
		ArrayList<PlacesResponse> response = placesAdapter.places(request);
		
		return response;
	}


}
