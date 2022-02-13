package com.location.services.places.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.location.services.places.model.PlacesRequest;
import com.location.services.places.model.PlacesResponse;
import com.location.services.places.service.PlacesService;

@RestController
public class PlacesController {

	private static final String BASE_URI = "/api/v1/locate";
	
	@Autowired
	private PlacesService placesService;
	
	@PostMapping(value = BASE_URI + "/places")
	public ResponseEntity<ArrayList<PlacesResponse>> places(@RequestBody PlacesRequest request) throws IOException, URISyntaxException {
		return ResponseEntity.ok().body(placesService.places(request));
	}

}
