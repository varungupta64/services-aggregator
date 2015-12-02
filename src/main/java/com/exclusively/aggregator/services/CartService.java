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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exclusively.aggregator.entities.Cart;
import com.exclusively.aggregator.entities.CartView;
import com.exclusively.aggregator.entities.CompactProduct;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Component
public class CartService {

	@Autowired
	private ICartService cartService;

	public static String ID = "id";
	public static String IS_GUEST = "isGuest";
	public static String PRODUCT_ID = "productId";
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
		String result = cartService.getCart(id);
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
					compactProduct.setCurrentQuantity(originalQuantity);
					cartView.getCompactProducts().add(compactProduct);
				} else {
					totalPrice += originalQuantity * Float.parseFloat(compactProduct.getMsrp());

					compactProduct.setCurrentQuantity(originalQuantity);
					cartView.getCompactProducts().add(compactProduct);
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

	public boolean addProductToCart(String id, String isGuest, String sku, int quantity) {
		List<String> skus = new ArrayList<String>();
		skus.add(sku);
		List<CompactProduct> products = getSkuInfo(skus);
		if (CollectionUtils.isNotEmpty(products)) {
			CompactProduct product = products.get(0);
			int availableQty = Integer.parseInt(product.getQty());
			if (availableQty >= quantity) {
				String sendGet = cartService.addCartItem(id, Boolean.getBoolean(isGuest), sku, quantity);
				if(sendGet.equals("200")) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean removeProduct(String id, String sku) {
		String sendGet = cartService.removeCartItem(id, sku);
		if(sendGet.equals("200")) {
			return true;
		}
		return false;
	}

	public boolean clearCart(String id) {
		String result = cartService.clearCart(id);
		if(result.equals("200")) {
			return true;
		}
		return false;
	}

	public String getCartCount(String id) {
		return cartService.getCartCount(id);
	}

}
