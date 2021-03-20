package com.somecompany;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.somecompany.model.GetPostcodeResponse;
import com.somecompany.service.PostcodeService;

@SpringBootTest
@ActiveProfiles("test")
public class GetPostCodeBySuburbTest {

	@Autowired
	private PostcodeService postcodeService;

	@Test
	public void shouldBeAbleToGetPostCodeBySuburbExactMatch() {

		// Actual result

		GetPostcodeResponse result = postcodeService.getPostcodeBySuburb("PARRAMATTA, NSW");

		// Assertions

		assertEquals(2, result.getPostcodes().size());

		assertEquals("2124", result.getPostcodes().get(0));
		assertEquals("2150", result.getPostcodes().get(1));
	}

	@Test
	public void shouldBeAbleToGetPostcodeBySuburbPartialMatch() {

		// Actual result

		GetPostcodeResponse result = postcodeService.getPostcodeBySuburb("PAR");

		// Assertions

		assertEquals(2, result.getPostcodes().size());

		assertEquals("2124", result.getPostcodes().get(0));
		assertEquals("2150", result.getPostcodes().get(1));
	}
}
