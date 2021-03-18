package com.somecompany.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Postcode {

	@EmbeddedId
	private PostcodePK postcodePK;

	@Column(name = "suburbinfo")
	@Size(min = 0, max = 100, message = "Suburb information must have length between 0 and 100!")
	private String suburbinfo;
}
