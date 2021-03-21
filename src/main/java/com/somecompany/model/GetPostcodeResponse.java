package com.somecompany.model;

import java.util.List;

import lombok.Data;

/**
 * The model of a response to a request calling the endpoint to get a list of postcodes matching the suburb.
 * 
 * @author patrick
 */
@Data
public class GetPostcodeResponse {

	private List<String> postcodes;
}
