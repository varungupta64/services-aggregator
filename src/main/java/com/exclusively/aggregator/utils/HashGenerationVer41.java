package com.exclusively.aggregator.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.gson.JsonObject;


public class HashGenerationVer41 {
	private static final String key = "gtKFFx";// "gtKFFx";//put your key
	private static final String salt = "eCwWELxi";// "eCwWELxi";//put your salt
	private static final String PAYMENT_HASH = "payment_hash";
	private static final String GET_MERCHANT_IBIBO_CODES_HASH = "get_merchant_ibibo_codes_hash";
	private static final String VAS_FOR_MOBILE_SDK_HASH = "vas_for_mobile_sdk_hash";
	private static final String PAYMENT_RELATED_DETAILS_FOR_MOBILE_SDK_HASH = "payment_related_details_for_mobile_sdk_hash";
	private static final String DELETE_USER_CARD_HASH = "delete_user_card_hash";
	private static final String GET_USER_CARDS_HASH = "get_user_cards_hash";
	private static final String EDIT_USER_CARD_HASH = "edit_user_card_hash";
	private static final String SAVE_USER_CARD_HASH = "save_user_card_hash";
	private static final String CHECK_OFFER_STATUS_HASH = "check_offer_status_hash";
	private static final String CHECK_ISDOMESTIC_HASH = "check_isDomestic_hash";

	public static JsonObject getHashes(String txnid, String amount, String productInfo, String firstname, String email,
			String user_credentials, String udf1, String udf2, String udf3, String udf4, String udf5, String offerKey,
			String cardBin){
		JsonObject response = new JsonObject();

		String ph = checkNull(key) + "|" + checkNull(txnid) + "|" + checkNull(amount) + "|" + checkNull(productInfo)
				+ "|" + checkNull(firstname) + "|" + checkNull(email) + "|" + checkNull(udf1) + "|" + checkNull(udf2)
				+ "|" + checkNull(udf3) + "|" + checkNull(udf4) + "|" + checkNull(udf5) + "||||||" + salt;
		String paymentHash = getSHA(ph);
		response.addProperty(PAYMENT_HASH, paymentHash);
		response.addProperty(GET_MERCHANT_IBIBO_CODES_HASH, generateHashString("get_merchant_ibibo_codes", "default"));
		response.addProperty(VAS_FOR_MOBILE_SDK_HASH, generateHashString("vas_for_mobile_sdk", "default"));
		response.addProperty(PAYMENT_RELATED_DETAILS_FOR_MOBILE_SDK_HASH,
				generateHashString("payment_related_details_for_mobile_sdk", "default"));

		if (!checkNull(user_credentials).isEmpty()) {
			response.addProperty(DELETE_USER_CARD_HASH, generateHashString("delete_user_card", user_credentials));
			response.addProperty(GET_USER_CARDS_HASH, generateHashString("get_user_cards", user_credentials));
			response.addProperty(EDIT_USER_CARD_HASH, generateHashString("edit_user_card", user_credentials));
			response.addProperty(SAVE_USER_CARD_HASH, generateHashString("save_user_card", user_credentials));
			response.addProperty(PAYMENT_RELATED_DETAILS_FOR_MOBILE_SDK_HASH,
					generateHashString("payment_related_details_for_mobile_sdk", user_credentials));
		}

		// check_offer_status
		if (!checkNull(offerKey).isEmpty()) {
			response.addProperty(CHECK_OFFER_STATUS_HASH, generateHashString("check_offer_status", offerKey));
		}

		// check_isDomestic
		if (!checkNull(cardBin).isEmpty()) {
			response.addProperty(CHECK_ISDOMESTIC_HASH, generateHashString("check_isDomestic", cardBin));
		}

		return response;

	}

	private static String generateHashString(String command, String var1) {
		return getSHA(key + "|" + command + "|" + var1 + "|" + salt);
	}

	private static String checkNull(String value) {
		if (value == null) {
			return "";
		} else {
			return value;
		}
	}

	private static String getSHA(String str) {

		MessageDigest md;
		String out = "";
		try {
			md = MessageDigest.getInstance("SHA-512");
			md.update(str.getBytes());
			byte[] mb = md.digest();

			for (int i = 0; i < mb.length; i++) {
				byte temp = mb[i];
				String s = Integer.toHexString(new Byte(temp));
				while (s.length() < 2) {
					s = "0" + s;
				}
				s = s.substring(s.length() - 2);
				out += s;
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return out;

	}

}


