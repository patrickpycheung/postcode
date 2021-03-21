package com.somecompany.model;

import java.util.List;

import lombok.Data;

/**
 * The model of a response to a request calling the endpoint to get a list of suburbs matching the postcode.
 * 
 * @author patrick
 */
@Data
public class GetSuburbResponse {

	private List<String> suburbs;
}
