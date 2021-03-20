package com.somecompany;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.somecompany.error.ApiError;
import com.somecompany.model.GetPostcodeResponse;
import com.somecompany.service.PostcodeService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class GetPostCodeBySuburbTest {

	@Autowired
	private PostcodeService postcodeService;

	@Autowired
	private WebTestClient webTestClient;

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

	@Test
	public void shouldBeAbleToGetPostCodeBySuburbExactMatchThroughAPI() {

		webTestClient.get().uri(builder -> {
			return builder.path("/api/postcode/postcode").queryParam("suburb", "PARRAMATTA, NSW").build();
		}).exchange().expectStatus().isOk().expectBody(GetPostcodeResponse.class).value(result -> {
			// Assertions

			assertEquals(2, result.getPostcodes().size());

			assertEquals("2124", result.getPostcodes().get(0));
			assertEquals("2150", result.getPostcodes().get(1));
		});
	}

	@Test
	public void shouldBeAbleToGetPostCodeBySuburbPartialMatchThroughAPI() {

		webTestClient.get().uri("/api/postcode/postcode?suburb=PAR").exchange().expectStatus().isOk()
				.expectBody(GetPostcodeResponse.class).value(result -> {
					// Assertions

					assertEquals(2, result.getPostcodes().size());

					assertEquals("2124", result.getPostcodes().get(0));
					assertEquals("2150", result.getPostcodes().get(1));
				});
	}

	@Test
	public void shouldBeAbleToThrowErrorIfSuburbIsMissingOnGetPostcodeBySuburbThroughAPI() {

		webTestClient.get().uri("/api/postcode/postcode").exchange().expectStatus().isBadRequest()
				.expectBody(ApiError.class).value(result -> {
					// Assertions

					assertEquals(HttpStatus.BAD_REQUEST, result.getStatus());
					assertThat(result.getMessage(),
							CoreMatchers.containsString("Required String parameter 'suburb' is not present"));
				});
	}

	@Test
	public void shouldBeAbleToThrowErrorIfSuburbLengthIsLessThan3OnGetPostCodeBySuburbThroughAPI() {

		webTestClient.get().uri("/api/postcode/postcode?suburb=PA").exchange().expectStatus().isBadRequest()
				.expectBody(ApiError.class).value(result -> {
					// Assertions

					assertEquals(HttpStatus.BAD_REQUEST, result.getStatus());
					assertThat(result.getMessage(), CoreMatchers.containsString(
							"getPostcodeBySuburb.suburb: Please provide at least 3 but no more than 100 characters for suburb!"));
				});
	}

	@Test
	public void shouldBeAbleToThrowErrorIfSuburbLengthIsMoreThan100OnGetPostCodeBySuburbThroughAPI() {

		webTestClient.get().uri(
				"/api/postcode/postcode?suburb=01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890")
				.exchange().expectStatus().isBadRequest().expectBody(ApiError.class).value(result -> {
					// Assertions

					assertEquals(HttpStatus.BAD_REQUEST, result.getStatus());
					assertThat(result.getMessage(), CoreMatchers.containsString(
							"getPostcodeBySuburb.suburb: Please provide at least 3 but no more than 100 characters for suburb!"));
				});
	}
}
