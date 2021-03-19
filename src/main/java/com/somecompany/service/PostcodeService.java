package com.somecompany.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.somecompany.dao.PostcodeRepository;
import com.somecompany.model.Postcode;
import com.somecompany.model.PostcodePK;
import com.somecompany.model.SuburbResponse;

@Service
public class PostcodeService {

	@Autowired
	private PostcodeRepository postcodeRepository;

	public SuburbResponse getSuburbByPostcode(Integer postcode) {

		// Get matching data

		PostcodePK inputPostcodePK = new PostcodePK();
		inputPostcodePK.setPostcode(postcode);

		Postcode inputPostcode = new Postcode();
		inputPostcode.setPostcodePK(inputPostcodePK);

		ExampleMatcher matcher = ExampleMatcher.matchingAll();
		Example<Postcode> example = Example.of(inputPostcode, matcher);
		List<Postcode> postcodeList = postcodeRepository.findAll(example);

		// Prepare response

		List<String> suburbs = new ArrayList<>();

		for (Postcode p : postcodeList) {
			suburbs.add(p.getPostcodePK().getSuburb());
		}

		SuburbResponse suburbResponse = new SuburbResponse();
		suburbResponse.setSuburbs(suburbs);

		return suburbResponse;
	}
}
