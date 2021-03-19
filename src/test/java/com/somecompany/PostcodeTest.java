package com.somecompany;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.somecompany.model.PostcodeResponse;
import com.somecompany.model.SuburbResponse;
import com.somecompany.service.PostcodeService;

@SpringBootTest
@ActiveProfiles("test")
public class PostcodeTest {

	@Autowired
	private PostcodeService PostcodeService;

	@Test
	public void shouldBeAbleToGetSuburbByPostCodeExactMatch() {

		// Actual result

		SuburbResponse result = PostcodeService.getSuburbByPostcode("3121");

		// Assertions

		assertEquals(2, result.getSuburbs().size());

		assertEquals("BURNLEY, VIC", result.getSuburbs().get(0));
		assertEquals("RICHMOND, VIC", result.getSuburbs().get(1));
	}

	@Test
	public void shouldBeAbleToGetSuburbByPostCodePartialMatch() {

		// Actual result

		SuburbResponse result = PostcodeService.getSuburbByPostcode("312");

		// Assertions

		assertEquals(2, result.getSuburbs().size());

		assertEquals("BURNLEY, VIC", result.getSuburbs().get(0));
		assertEquals("RICHMOND, VIC", result.getSuburbs().get(1));
	}

	@Test
	public void shouldBeAbleToGetPostCodeBySuburbExactMatch() {

		// Actual result

		PostcodeResponse result = PostcodeService.getPostcodeBySuburb("PARRAMATTA, NSW");

		// Assertions

		assertEquals(2, result.getPostcodes().size());

		assertEquals("2124", result.getPostcodes().get(0));
		assertEquals("2150", result.getPostcodes().get(1));
	}

	@Test
	public void shouldBeAbleToGetPostcodeBySuburbPartialMatch() {

		// Actual result

		PostcodeResponse result = PostcodeService.getPostcodeBySuburb("PAR");

		// Assertions

		assertEquals(2, result.getPostcodes().size());

		assertEquals("2124", result.getPostcodes().get(0));
		assertEquals("2150", result.getPostcodes().get(1));
	}
}
