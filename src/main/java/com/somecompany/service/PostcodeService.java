package com.somecompany.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.somecompany.dao.PostcodeRepository;
import com.somecompany.model.GetPostcodeResponse;
import com.somecompany.model.GetSuburbResponse;
import com.somecompany.model.Postcode;

@Service
public class PostcodeService {

	@Autowired
	private PostcodeRepository postcodeRepository;

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

	public GetPostcodeResponse getPostcodeBySuburb(String suburb) {

		List<Postcode> postcodeList = postcodeRepository.findBySuburbContaining(suburb);

		// Prepare response

		List<String> postcodes = new ArrayList<>();

		for (Postcode p : postcodeList) {
			postcodes.add(p.getPostcode());
		}

		GetPostcodeResponse getPostcodeResponse = new GetPostcodeResponse();
		getPostcodeResponse.setPostcodes(postcodes);

		return getPostcodeResponse;
	}

	public void addPostcodeAndSuburb(Postcode postcode) {

		// Get all records for the postcode, if any
		List<Postcode> postcodes = postcodeRepository.findByPostcode(postcode.getPostcode());

		if (!postcodes.contains(postcode)) {
			// The combination has not yet been created

			postcodeRepository.save(postcode);
		}
	}
}
