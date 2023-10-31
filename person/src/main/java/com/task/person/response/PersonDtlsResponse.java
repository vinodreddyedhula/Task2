package com.task.person.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonDtlsResponse {
	
	private String name;
	private List<ChildDetailsDTO> subClasses;

}
