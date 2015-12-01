package com.exclusively.aggregator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String emailId;

	private String firstName;
	private String lastName;
	private String gender;
	private String ssoProvider;
	@Column(name = "password_hash", nullable = false)
	private String password;
	private String dob;
	private String phoneNumber;

	private short isActive = 1;
	private String country;
	private String createDate;
	
}
