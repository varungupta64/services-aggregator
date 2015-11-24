package com.exclusively.aggregator.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.exclusively.aggregator.entities.Cart;
import com.exclusively.aggregator.entities.CartView;
import com.exclusively.aggregator.entities.CompactProduct;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * CArt controller, fetches Cart info from the microservice
 * 
 * @author Anshul Chauhan
 */
@Component
@Service
public class CartAggregatorService {

	public static String ID = "id";
	public static String IS_GUEST = "id";
	public static String PRODUCT_ID = "id";
	public static String QUANTITY = "quantity";
	public static String SEPERATOR = "/";

	@HystrixCommand
	private CartView defaultFallBack() {
		CartView cart = new CartView();
		cart.setUpdated("Error");
		return cart;
	}

	@HystrixCommand
	private CartView getCartFallBack(String id) {
		CartView cart = new CartView();
		cart.setUpdated("Error");
		return cart;
	}

	@HystrixCommand(commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, fallbackMethod = "getCartFallBack")
	public CartView getCart(String id) {
		// String result = restTemplate.getForObject(serviceUrl + "/cart");
		CartView cartView = new CartView();
		String urlParams = "getCart/id/" + id;
		String result = getCartInfo(urlParams);
		Type cartType = new TypeToken<Cart>() {
		}.getType();
		Cart cart = new Gson().fromJson(result.toString(), cartType);

		List<CompactProduct> products = getSkuInfo(Lists.newArrayList(cart.getProductQuantityMapping().keySet()));
		float totalPrice = 0;

		for (CompactProduct compactProduct : products) {
			String sku = compactProduct.get_id();
			Integer originalQuantity = cart.getProductQuantityMapping().get(sku);
			Integer availbleQuantity = Integer.parseInt(compactProduct.getQty());
			if (availbleQuantity != 0) {
				if (originalQuantity >= availbleQuantity) {
					totalPrice += originalQuantity * Float.parseFloat(compactProduct.getMsrp());
					cartView.getProductQuantityMapping().put(compactProduct, originalQuantity);
				} else {
					totalPrice += originalQuantity * Float.parseFloat(compactProduct.getMsrp());
					cartView.getProductQuantityMapping().put(compactProduct, originalQuantity);
				}
			}
		}
		cartView.setTotalPrice(totalPrice);
		cartView.setDiscountApplied(0);
		cartView.setNetPrice(totalPrice);
		cartView.setCreated(cart.getCreated());
		cartView.setUpdated(cart.getUpdated());
		cartView.setGuest(cart.isGuest());
		cartView.setId(id);
		return cartView;
	}

	private String getCartInfo(String urlParams) {

		try {
			String url = "http://localhost:8080/cart/";
			url = url + urlParams;

			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);

			// // add request header
			// request.addHeader("User-Agent", USER_AGENT);

			HttpResponse response = client.execute(request);

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			return result.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private List<CompactProduct> getSkuInfo(List<String> skus) {

		String url = "http://10.11.22.10:3001/catalog/fetch/sku/product";

		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);

			// add header
			// post.setHeader("User-Agent", USER_AGENT);

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

			urlParameters.add(new BasicNameValuePair("sku", new Gson().toJson(skus)));
			post.setEntity(new UrlEncodedFormEntity(urlParameters));

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
			Type listType = new TypeToken<ArrayList<CompactProduct>>() {
			}.getType();
			List<CompactProduct> yourClassList = new Gson().fromJson(result.toString(), listType);
			return yourClassList;
		} catch (Exception e) {
			System.out.println("Exception Occoured" + e);
			throw new RuntimeException(e);
		}
	}

	public String addProductToCart(String id, String isGuest, String sku, int quantity) {
		List<String> skus = new ArrayList<String>();
		skus.add(sku);
		List<CompactProduct> products = getSkuInfo(skus);
		if (CollectionUtils.isNotEmpty(products)) {
			CompactProduct product = products.get(0);
			int availableQty = Integer.parseInt(product.getQty());
			if (availableQty >= quantity) {
				String urlParams = "addProduct/" + ID + SEPERATOR + id + SEPERATOR + IS_GUEST + SEPERATOR + isGuest
						+ SEPERATOR + PRODUCT_ID + SEPERATOR + sku + SEPERATOR + QUANTITY + SEPERATOR + quantity;
				String sendGet = getCartInfo(urlParams);
				return sendGet;
			}
		}
		return HttpStatus.BAD_REQUEST.name();
	}

	public String removeProduct(String id, String sku) {
		String urlParams = "removeProduct/" + ID + SEPERATOR + "productId" + SEPERATOR + sku;
		String sendGet = getCartInfo(urlParams);
		return sendGet;
	}

	public CartView clearCart(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}
