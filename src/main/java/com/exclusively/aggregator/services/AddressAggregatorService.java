package com.exclusively.aggregator.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.exclusively.aggregator.entities.Address;
import com.exclusively.aggregator.entities.CompactProduct;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class AddressAggregatorService {

	public static String ID = "id";
	public static String IS_GUEST = "id";
	public static String ADDRESS_ID = "addressId";
	public static String EMAIL = "email";
	public static String SEPERATOR = "/";

	public String saveAddress(Address address) {
		String url = "http://localhost:8080/address/saveAddress";

		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

			urlParameters.add(new BasicNameValuePair("addressJson", new Gson().toJson(address)));
			//post.set
			post.setHeader("Content-Type", "application/json");
			//post.setEntity(new UrlEncodedFormEntity(urlParameters));
			post.setEntity(new StringEntity(new Gson().toJson(address)));
			

			HttpResponse response = client.execute(post);
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + post.getEntity());
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);

			}

			return result.toString();
		} catch (Exception e) {
			System.out.println("Exception Occoured" + e);
			throw new RuntimeException(e);
		}
	}

	public String deleteAddress(String addressId) {
		//System.out.println(addressId);
		String urlParams = "/address/deleteAddress/addressId/" + addressId;
		String result = resultFromApi(urlParams);
		return result;
	}

	public String resultFromApi(String urlparams) {

		try {

			String url = "http://localhost:8080";
			url = url + urlparams;
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			return result.toString();

		} catch (ClientProtocolException e) {
			return "Deletion Failed";
		} catch (IOException e) {
			return "Deletion Failed";
		}

	}

	public String getAddress(String email) {
		String urlparams = "/address/getAddress/?email="+email;
		String addressesForEmail = resultFromApi(urlparams);
		return addressesForEmail;

	}

	public String updateAddress(Address address) {
		String url = "http://localhost:8080/address/updateAddress";
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

			urlParameters.add(new BasicNameValuePair("addressJson", new Gson().toJson(address)));
			//post.set
			post.setHeader("Content-Type", "application/json");
			//post.setEntity(new UrlEncodedFormEntity(urlParameters));
			post.setEntity(new StringEntity(new Gson().toJson(address)));
			

			HttpResponse response = client.execute(post);
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + post.getEntity());
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);

			}

			return result.toString();
		} catch (Exception e) {
			System.out.println("Exception Occoured" + e);
			throw new RuntimeException(e);
		}
	}

}