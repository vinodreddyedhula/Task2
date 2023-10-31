package com.task.person.interfaces;

import java.util.List;

import com.task.person.request.PersonRequestDTO;
import com.task.person.response.ParentChildResponse;

public interface PersonService {

	public List<PersonRequestDTO> getPersonDtls();

	public PersonRequestDTO getPersonDtlsById(int id);

	public ParentChildResponse getParentChildRelation();

}
