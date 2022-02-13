package com.location.services.places.model;

import javax.validation.constraints.NotNull;

public class PlacesRequest {

	@NotNull
	public String radius;
	@NotNull
	public String types;
	@NotNull
	public String sensor;
	@NotNull
	public String lat;
	@NotNull
	public String lon;

}
