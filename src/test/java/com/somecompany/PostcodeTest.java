package com.somecompany;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.somecompany.dao.PostcodeRepository;
import com.somecompany.model.Postcode;
import com.somecompany.model.PostcodeResponse;
import com.somecompany.model.SuburbResponse;
import com.somecompany.service.PostcodeService;

@SpringBootTest
@ActiveProfiles("test")
public class PostcodeTest {

	@Autowired
	private PostcodeService postcodeService;

	@Autowired
	private PostcodeRepository postcodeRepository;

	@Test
	public void shouldBeAbleToGetSuburbByPostCodeExactMatch() {

		// Actual result

		SuburbResponse result = postcodeService.getSuburbByPostcode("3121");

		// Assertions

		assertEquals(2, result.getSuburbs().size());

		assertEquals("BURNLEY, VIC", result.getSuburbs().get(0));
		assertEquals("RICHMOND, VIC", result.getSuburbs().get(1));
	}

	@Test
	public void shouldBeAbleToGetSuburbByPostCodePartialMatch() {

		// Actual result

		SuburbResponse result = postcodeService.getSuburbByPostcode("312");

		// Assertions

		assertEquals(2, result.getSuburbs().size());

		assertEquals("BURNLEY, VIC", result.getSuburbs().get(0));
		assertEquals("RICHMOND, VIC", result.getSuburbs().get(1));
	}

	@Test
	public void shouldBeAbleToGetPostCodeBySuburbExactMatch() {

		// Actual result

		PostcodeResponse result = postcodeService.getPostcodeBySuburb("PARRAMATTA, NSW");

		// Assertions

		assertEquals(2, result.getPostcodes().size());

		assertEquals("2124", result.getPostcodes().get(0));
		assertEquals("2150", result.getPostcodes().get(1));
	}

	@Test
	public void shouldBeAbleToGetPostcodeBySuburbPartialMatch() {

		// Actual result

		PostcodeResponse result = postcodeService.getPostcodeBySuburb("PAR");

		// Assertions

		assertEquals(2, result.getPostcodes().size());

		assertEquals("2124", result.getPostcodes().get(0));
		assertEquals("2150", result.getPostcodes().get(1));
	}

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
