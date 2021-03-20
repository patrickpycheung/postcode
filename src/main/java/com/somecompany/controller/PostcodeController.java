package com.somecompany.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.somecompany.error.ApiError;
import com.somecompany.model.GetPostcodeResponse;
import com.somecompany.model.GetSuburbResponse;
import com.somecompany.service.PostcodeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/postcode")
@Validated
@Slf4j
public class PostcodeController {

	@Autowired
	private PostcodeService postcodeService;

	@GetMapping(path = "/suburb", produces = "application/json")
	@ApiOperation(value = "Get a list of suburbs matching the postcode.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved suburb list.", response = GetSuburbResponse.class) })
	public ResponseEntity<Object> getSuburbByPostcode(
			@RequestParam @NotEmpty(message = "Postcode cannot be null nor empty!") @Pattern(regexp = "^[0-9]+$", message = "Postcode must be a number!") @Size(min = 3, max = 4, message = "Please provide at least 3 but no more than 4 characters for postcode!") String postcode) {

		try {
			return ResponseEntity.ok(postcodeService.getSuburbByPostcode(postcode));
		} catch (Exception e) {
			return getErrorResponse(e);
		}
	}

	@GetMapping(path = "/postcode", produces = "application/json")
	@ApiOperation(value = "Get a list of postcodes matching the suburb.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved postcode list.", response = GetPostcodeResponse.class) })
	public ResponseEntity<Object> getPostcodeBySuburb(
			@RequestParam @NotEmpty(message = "Suburb cannot be null nor empty!") @Size(min = 3, max = 100, message = "Please provide at least 3 but no more than 100 characters for suburb!") String suburb) {

		try {
			return ResponseEntity.ok(postcodeService.getPostcodeBySuburb(suburb));
		} catch (Exception e) {
			return getErrorResponse(e);
		}
	}

	/**
	 * Create error response from exception messages.
	 * 
	 * @param exception
	 * @return A list of all error responses
	 */
	private ResponseEntity<Object> getErrorResponse(Exception exception) {
		List<String> errors = new ArrayList<String>();
		String error = "Invalid request! Please check the request and request params.";
		errors.add(error);

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), errors);
		log.error("\n##################################################\n" + "Exception:\n"
				+ exception.getLocalizedMessage() + "\n##################################################");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
}
