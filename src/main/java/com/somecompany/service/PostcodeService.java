package com.somecompany.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.somecompany.dao.PostcodeRepository;
import com.somecompany.model.Postcode;
import com.somecompany.model.SuburbResponse;

@Service
public class PostcodeService {

	@Autowired
	private PostcodeRepository postcodeRepository;

	public SuburbResponse getSuburbByPostcode(String postcode) {

//		// Get matching data
//
//		Postcode inputPostcode = new Postcode();
//		inputPostcode.setPostcode(postcode);
//
//		ExampleMatcher matcher = ExampleMatcher.matchingAll();
//		Example<Postcode> example = Example.of(inputPostcode, matcher);
//		List<Postcode> postcodeList = postcodeRepository.findAll(example);

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
}
