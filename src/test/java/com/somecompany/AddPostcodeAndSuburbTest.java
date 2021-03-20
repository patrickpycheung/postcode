package com.somecompany;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.somecompany.dao.PostcodeRepository;
import com.somecompany.model.Postcode;
import com.somecompany.service.PostcodeService;

@SpringBootTest
@ActiveProfiles("test")
public class AddPostcodeAndSuburbTest {

	@Autowired
	private PostcodeService postcodeService;

	@Autowired
	private PostcodeRepository postcodeRepository;

	@Test
	public void shouldBeAbleToAddPostcodeAndSuburb() {

		// Actual result

		Postcode postcode = new Postcode();
		postcode.setPostcode("1234");
		postcode.setSuburb("Test");

		postcodeService.addPostcodeAndSuburb(postcode);

		// Assertions

		assertEquals(7, postcodeRepository.count());

		assertEquals("1234", postcodeRepository.findBySuburbContaining("Test").get(0).getPostcode());
		assertEquals("Test", postcodeRepository.findByPostcodeContaining("1234").get(0).getSuburb());
	}
}
