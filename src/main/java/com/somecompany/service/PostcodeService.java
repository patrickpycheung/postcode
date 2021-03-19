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
