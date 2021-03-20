package com.somecompany.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddPostcodeAndSuburbRequest {

	private String postcode;

	private String suburb;
}
