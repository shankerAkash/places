package com.location.services.places.adapter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.location.services.places.model.PlacesRequest;
import com.location.services.places.model.PlacesResponse;

import okhttp3.OkHttpClient;

@Service
public class PlacesAdapter {
	
	private static final String GOOGLE_API_KEY = "AIzaSyDqy4AqMtNwmBfP_-pt86YW8Z5E4L0YCTU";
	
	private final HttpClient client = new DefaultHttpClient();

	public ArrayList<PlacesResponse> places(PlacesRequest request) throws ClientProtocolException, IOException, URISyntaxException {
		
		ArrayList<PlacesResponse> response = performSearch(request); 
		return response;
	}
	
	private ArrayList<PlacesResponse> performSearch(PlacesRequest request) throws ClientProtocolException, IOException, URISyntaxException {
		
		ArrayList<PlacesResponse> resultList = null;
		
		StringBuilder jsonResults = new StringBuilder();
		final URIBuilder builder = new URIBuilder().setScheme("https").setHost("maps.googleapis.com").setPath("/maps/api/place/search/json");

        builder.addParameter("location", request.lat + "," + request.lon);
        builder.addParameter("radius", request.radius);
        builder.addParameter("types", request.types);
        builder.addParameter("sensor", "true");
        builder.addParameter("key", GOOGLE_API_KEY);
        
        final HttpUriRequest urirequest = new HttpGet(builder.build());
        final HttpResponse execute = this.client.execute(urirequest);
        
        InputStreamReader in = new InputStreamReader(execute.getEntity().getContent());
        int read;
        char[] buff = new char[1024];
        while ((read = in.read(buff)) != -1) {
            jsonResults.append(buff, 0, read);
        }
        
		try {
			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			JSONArray predsJsonArray = jsonObj.getJSONArray("results");

			resultList = new ArrayList<PlacesResponse>(predsJsonArray.length());
			for (int i = 0; i < predsJsonArray.length(); i++) {
				PlacesResponse place = new PlacesResponse();
				place.reference = predsJsonArray.getJSONObject(i).getString("reference");
				place.name = predsJsonArray.getJSONObject(i).getString("name");
				place.vicinity = predsJsonArray.getJSONObject(i).getString("vicinity");
				JSONArray typesArray = predsJsonArray.getJSONObject(i).getJSONArray("types");
				List<String> types = new ArrayList<>();
				for(int j = 0; j < typesArray.length(); j++) {
					types.add(typesArray.getString(j));
				}
				place.types = types;
				resultList.add(place);
			}
		} catch (JSONException e) {
			System.out.println("Error processing JSON results" + e.toString());
		}
		
		return resultList;
	}

}
