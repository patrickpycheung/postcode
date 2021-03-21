package com.somecompany.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.somecompany.dao.PostcodeRepository;
import com.somecompany.model.GetPostcodeResponse;
import com.somecompany.model.GetSuburbResponse;
import com.somecompany.model.Postcode;

/**
 * The service performing the actual business logic of the application.
 * 
 * @author patrick
 */
@Service
public class PostcodeService {

	@Autowired
	private PostcodeRepository postcodeRepository;

	/**
	 * Get a list of suburbs matching the postcode.
	 * 
	 * @param postcode The postcode pattern
	 * @return A list of suburbs matching the postcode pattern
	 */
	public GetSuburbResponse getSuburbByPostcode(String postcode) {

		List<Postcode> postcodeList = postcodeRepository.findByPostcodeContaining(postcode);

		// Prepare response

		List<String> suburbs = new ArrayList<>();

		for (Postcode p : postcodeList) {
			suburbs.add(p.getSuburb());
		}

		GetSuburbResponse getSuburbResponse = new GetSuburbResponse();
		getSuburbResponse.setSuburbs(suburbs);

		return getSuburbResponse;
	}

	/**
	 * Get a list of postcodes matching the suburb.
	 * 
	 * @param suburb The suburb pattern
	 * @return A list of postcodes matching the suburb pattern
	 */
	public GetPostcodeResponse getPostcodeBySuburb(String suburb) {

		List<Postcode> postcodeList = postcodeRepository.findBySuburbContainingIgnoreCase(suburb);

		// Prepare response

		List<String> postcodes = new ArrayList<>();

		for (Postcode p : postcodeList) {
			postcodes.add(p.getPostcode());
		}

		GetPostcodeResponse getPostcodeResponse = new GetPostcodeResponse();
		getPostcodeResponse.setPostcodes(postcodes);

		return getPostcodeResponse;
	}

	/**
	 * Add new suburb and postcode combination
	 * 
	 * @param postcode The Postcode object to be added
	 */
	public void addPostcodeAndSuburb(Postcode postcode) {

		// Get all records for the postcode, if any
		List<Postcode> postcodes = postcodeRepository.findByPostcode(postcode.getPostcode());

		// Only add if the combination is new
		if (!postcodes.contains(postcode)) {
			// The combination has not yet been created

			postcodeRepository.save(postcode);
		}
	}
}
