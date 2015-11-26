package com.exclusively.aggregator.entities;


import java.util.UUID;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Table(name="addresses")
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"addressId",
	"email",
	"name",
	"phoneNo",
	"locality",
	"addressFirstLine",
	"city",
	"state",
	"country",
	"pincode",
	"isActive",
	"isDefault"
})
public class Address {

	@Id
	@JsonProperty
	private String addressId = UUID.randomUUID().toString();
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("phoneNo")
	private String phoneNo;
	
	@JsonProperty("locality")
	private String locality;
	
	@JsonProperty("addressFirstLine")
	private String addressFirstLine;
	
	@JsonProperty("city")
	private String city;
	
	@JsonProperty("state")
	private String state;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("pincode")
	private String pincode;
	
	@JsonProperty("isActive")
	private boolean isActive;
	
	@JsonProperty("isDefault")
	private boolean isDefault;

	/**
	 * 
	 * @return
	 * The email
	 */
	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @param email
	 * The email
	 */
	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 * @return
	 * The name
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 * The name
	 */
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 * The phoneNo
	 */
	@JsonProperty("phoneNo")
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * 
	 * @param phoneNo
	 * The phoneNo
	 */
	@JsonProperty("phoneNo")
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * 
	 * @return
	 * The locality
	 */
	@JsonProperty("locality")
	public String getLocality() {
		return locality;
	}

	/**
	 * 
	 * @param locality
	 * The locality
	 */
	@JsonProperty("locality")
	public void setLocality(String locality) {
		this.locality = locality;
	}

	/**
	 * 
	 * @return
	 * The addressFirstLine
	 */
	@JsonProperty("addressFirstLine")
	public String getAddressFirstLine() {
		return addressFirstLine;
	}

	/**
	 * 
	 * @param addressFirstLine
	 * The addressFirstLine
	 */
	@JsonProperty("addressFirstLine")
	public void setAddressFirstLine(String addressFirstLine) {
		this.addressFirstLine = addressFirstLine;
	}

	/**
	 * 
	 * @return
	 * The city
	 */
	@JsonProperty("city")
	public String getCity() {
		return city;
	}

	/**
	 * 
	 * @param city
	 * The city
	 */
	@JsonProperty("city")
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 
	 * @return
	 * The state
	 */
	@JsonProperty("state")
	public String getState() {
		return state;
	}

	/**
	 * 
	 * @param state
	 * The state
	 */
	@JsonProperty("state")
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * 
	 * @return
	 * The country
	 */
	@JsonProperty("country")
	public String getCountry() {
		return country;
	}

	/**
	 * 
	 * @param country
	 * The country
	 */
	@JsonProperty("country")
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * 
	 * @return
	 * The pincode
	 */
	@JsonProperty("pincode")
	public String getPincode() {
		return pincode;
	}
	
	@JsonProperty("pincode")
	public void setPincode(String pincode) {
		this.pincode=pincode;
	}

	@JsonProperty("addressId")
	public String getAddressId() {
		return addressId;
	}
	
	@JsonProperty("isActive")
	public void setIsActive(boolean isActive) {
		this.isActive=isActive;
	}

	@JsonProperty("isActive")
	public boolean getIsActive() {
		return isActive;
	}
	
	@JsonProperty("isDefault")
	public void setIsDefault(boolean isDefault) {
		this.isDefault=isDefault;
	}

	@JsonProperty("isDefault")
	public boolean getIsDefault() {
		return isDefault;
	}

}