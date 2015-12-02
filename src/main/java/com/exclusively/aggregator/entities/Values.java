
package com.exclusively.aggregator.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Values {

	private Object image;
	private Object thumbnail;
	private Object smallImage;

}
