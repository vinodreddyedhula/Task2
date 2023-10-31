package com.task.person.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.person.aspect.LogMethodParam;
import com.task.person.entity.Person;
import com.task.person.interfaces.PersonService;
import com.task.person.repository.PersonRepository;
import com.task.person.request.PersonRequestDTO;
import com.task.person.response.ChildDetailsDTO;
import com.task.person.response.ParentChildResponse;
import com.task.person.response.PersonDtlsResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<PersonRequestDTO> getPersonDtls() {

		List<Person> persons = personRepository.findAll();
		log.info("---mapping entity to DTO---");
		List<PersonRequestDTO> personDtls = persons.stream()
				.map(person -> modelMapper.map(person, PersonRequestDTO.class)).collect(Collectors.toList());
		return personDtls;
	}
	
	@Override
	public PersonRequestDTO getPersonDtlsById(int id) {

		return modelMapper.map(personRepository.getById(id), PersonRequestDTO.class);

	}

	@Override
	public ParentChildResponse getParentChildRelation() {

		List<Person> persons = personRepository.findAll();
		log.info("---mapping entity to DTO---");
		List<PersonRequestDTO> personDtls = persons.stream()
				.map(person -> modelMapper.map(person, PersonRequestDTO.class)).collect(Collectors.toList());

		return prepareResponse(personDtls);
	}

	@LogMethodParam
	private ParentChildResponse prepareResponse(List<PersonRequestDTO> personDtls) {

		Map<Integer, List<String>> m = personDtls.stream().collect(Collectors.groupingBy(PersonRequestDTO::getParentId,
				Collectors.mapping(PersonRequestDTO::getName, Collectors.toList())));

		ParentChildResponse parentChildResponse = new ParentChildResponse();

		List<PersonDtlsResponse> personDtlsList = personDtls.stream().map(x -> {
			PersonDtlsResponse response = new PersonDtlsResponse();
			response.setName(x.getName());

			List<ChildDetailsDTO> subClasses = new ArrayList<ChildDetailsDTO>();

			List<String> subclasses = m.get(x.getId());
			if (subclasses != null && !subclasses.isEmpty()) {
				subclasses.forEach(y -> {

					ChildDetailsDTO childDtls = new ChildDetailsDTO();
					childDtls.setName(y);
					subClasses.add(childDtls);
				});
			}
			response.setSubClasses(subClasses);
			return response;
		}).collect(Collectors.toList());
		
		parentChildResponse.setResponse(personDtlsList.stream()
				.filter(x -> x.getSubClasses() != null && !x.getSubClasses().isEmpty()).collect(Collectors.toList()));
		return parentChildResponse;

	}

	//Without Streams
	private ParentChildResponse prepareResponse1(List<PersonRequestDTO> personDtls) {

		ParentChildResponse parentChildResponse = new ParentChildResponse();
		List<PersonDtlsResponse> personDtlsList = new ArrayList<PersonDtlsResponse>();
		for (int i = 0; i < personDtls.size(); i++) {
			PersonDtlsResponse response = new PersonDtlsResponse();
			response.setName(personDtls.get(i).getName());
			List<ChildDetailsDTO> subclasses = new ArrayList<ChildDetailsDTO>();
			for (int j = 1; j < personDtls.size(); j++) {
				if (personDtls.get(i).getId() == personDtls.get(j).getParentId()) {

					ChildDetailsDTO childDtls = new ChildDetailsDTO();
					childDtls.setName(personDtls.get(j).getName());
					subclasses.add(childDtls);
				}
			}
			response.setSubClasses(subclasses);
			personDtlsList.add(response);
		}
		parentChildResponse.setResponse(personDtlsList);
		return parentChildResponse;

	}

	public void checkForSubClasses(List<PersonRequestDTO> personDtls, List<ChildDetailsDTO> subclasses, int i) {

		for (int j = 1; j < personDtls.size(); j++) {
			if (personDtls.get(i).getId() == personDtls.get(j).getParentId()) {

				ChildDetailsDTO childDtls = new ChildDetailsDTO();
				childDtls.setName(personDtls.get(j).getName());
				subclasses.add(childDtls);
			}
		}
	}

}
