package com.somecompany.model;

import lombok.Data;

/**
 * The model for a request body of a request calling the endpoint to add a new suburb and postcode combination.
 * 
 * @author patrick
 */
@Data
public class AddPostcodeAndSuburbRequest {

	private String postcode;

	private String suburb;
}
