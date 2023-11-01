package com.task.person.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class PersonDtlsResponse {

	private String name;
	private List<Object> subClasses;

	@Override
	public String toString() {
		return "PersonDtlsResponse [name=" + name + ", subClasses=" + subClasses + "]";
	}

	public PersonDtlsResponse(String name, List<Object> subClasses) {
		super();
		this.name = name;
		this.subClasses = subClasses;
	}

	public PersonDtlsResponse(String name) {
		this.name = name;
	}

}
