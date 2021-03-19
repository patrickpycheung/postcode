package com.somecompany.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.somecompany.dao.PostcodeRepository;
import com.somecompany.model.Postcode;
import com.somecompany.model.PostcodeResponse;
import com.somecompany.model.SuburbResponse;

@Service
public class PostcodeService {

	@Autowired
	private PostcodeRepository postcodeRepository;

	public SuburbResponse getSuburbByPostcode(String postcode) {

		List<Postcode> postcodeList = postcodeRepository.findByPostcodeContaining(postcode);

		// Prepare response

		List<String> suburbs = new ArrayList<>();

		for (Postcode p : postcodeList) {
			suburbs.add(p.getSuburb());
		}

		SuburbResponse suburbResponse = new SuburbResponse();
		suburbResponse.setSuburbs(suburbs);

		return suburbResponse;
	}

	public PostcodeResponse getPostcodeBySuburb(String suburb) {

		List<Postcode> postcodeList = postcodeRepository.findBySuburbContaining(suburb);

		// Prepare response

		List<String> postcodes = new ArrayList<>();

		for (Postcode p : postcodeList) {
			postcodes.add(p.getPostcode());
		}

		PostcodeResponse postcodeResponse = new PostcodeResponse();
		postcodeResponse.setPostcodes(postcodes);

		return postcodeResponse;
	}
}
