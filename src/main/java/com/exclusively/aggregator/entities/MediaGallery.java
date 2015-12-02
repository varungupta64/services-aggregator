
package com.exclusively.aggregator.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MediaGallery {

	private Values values;
}
