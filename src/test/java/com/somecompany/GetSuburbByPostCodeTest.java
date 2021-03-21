package com.somecompany;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.somecompany.error.ApiError;
import com.somecompany.model.GetSuburbResponse;
import com.somecompany.service.PostcodeService;

/**
 * Test cases for "get a list of suburbs matching the postcode".
 * 
 * @author patrick
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class GetSuburbByPostCodeTest {

	@Autowired
	private PostcodeService postcodeService;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void shouldBeAbleToGetSuburbByPostCodeExactMatch() {

		// Actual result

		GetSuburbResponse result = postcodeService.getSuburbByPostcode("3121");

		// Assertions

		assertEquals(2, result.getSuburbs().size());

		assertEquals("BURNLEY, VIC", result.getSuburbs().get(0));
		assertEquals("RICHMOND, VIC", result.getSuburbs().get(1));
	}

	@Test
	public void shouldBeAbleToGetSuburbByPostCodePartialMatch() {

		// Actual result

		GetSuburbResponse result = postcodeService.getSuburbByPostcode("312");

		// Assertions

		assertEquals(2, result.getSuburbs().size());

		assertEquals("BURNLEY, VIC", result.getSuburbs().get(0));
		assertEquals("RICHMOND, VIC", result.getSuburbs().get(1));
	}

	@Test
	public void shouldBeAbleToGetSuburbByPostCodeExactMatchThroughAPI() {

		webTestClient.get().uri("/api/postcode/suburb?postcode=3121").exchange().expectStatus().isOk()
				.expectBody(GetSuburbResponse.class).value(result -> {
					// Assertions

					assertEquals(2, result.getSuburbs().size());

					assertEquals("BURNLEY, VIC", result.getSuburbs().get(0));
					assertEquals("RICHMOND, VIC", result.getSuburbs().get(1));
				});
	}

	@Test
	public void shouldBeAbleToGetSuburbByPostCodePartialMatchThroughAPI() {

		webTestClient.get().uri("/api/postcode/suburb?postcode=312").exchange().expectStatus().isOk()
				.expectBody(GetSuburbResponse.class).value(result -> {
					// Assertions

					assertEquals(2, result.getSuburbs().size());

					assertEquals("BURNLEY, VIC", result.getSuburbs().get(0));
					assertEquals("RICHMOND, VIC", result.getSuburbs().get(1));
				});
	}

	@Test
	public void shouldBeAbleToThrowErrorIfPostcodeIsMissingOnGetSuburbByPostCodeThroughAPI() {
		webTestClient.get().uri("/api/postcode/suburb").exchange().expectStatus().isBadRequest()
				.expectBody(ApiError.class).value(result -> {
					// Assertions

					assertEquals(HttpStatus.BAD_REQUEST, result.getStatus());
					assertThat(result.getMessage(),
							CoreMatchers.containsString("Required String parameter 'postcode' is not present"));
				});
	}

	@Test
	public void shouldBeAbleToThrowErrorIfPostcodeLengthIsLessThan3OnGetSuburbByPostCodeThroughAPI() {
		webTestClient.get().uri("/api/postcode/suburb?postcode=31").exchange().expectStatus().isBadRequest()
				.expectBody(ApiError.class).value(result -> {
					// Assertions

					assertEquals(HttpStatus.BAD_REQUEST, result.getStatus());
					assertThat(result.getMessage(), CoreMatchers.containsString(
							"getSuburbByPostcode.postcode: Please provide at least 3 but no more than 4 characters for postcode!"));
				});
	}

	@Test
	public void shouldBeAbleToThrowErrorIfPostcodeLengthIsMoreThan4OnGetSuburbByPostCodeThroughAPI() {
		webTestClient.get().uri("/api/postcode/suburb?postcode=31211").exchange().expectStatus().isBadRequest()
				.expectBody(ApiError.class).value(result -> {
					// Assertions

					assertEquals(HttpStatus.BAD_REQUEST, result.getStatus());
					assertThat(result.getMessage(), CoreMatchers.containsString(
							"getSuburbByPostcode.postcode: Please provide at least 3 but no more than 4 characters for postcode!"));

				});
	}

	@Test
	public void shouldBeAbleToThrowErrorIfPostcodeIsNonNumericOnGetSuburbByPostCodeThroughAPI() {
		webTestClient.get().uri("/api/postcode/suburb?postcode=###").exchange().expectStatus().isBadRequest()
				.expectBody(ApiError.class).value(result -> {
					// Assertions

					assertEquals(HttpStatus.BAD_REQUEST, result.getStatus());
					assertThat(result.getMessage(),
							CoreMatchers.containsString("getSuburbByPostcode.postcode: Postcode must be a number!"));
				});
	}
}
