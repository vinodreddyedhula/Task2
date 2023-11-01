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
		List<Object> obj= buildParentChildReltn(personDtls,0);
		List<PersonDtlsResponse> a=new ArrayList<>();
		//Converting to actual instance
		obj.forEach(x->{
			if(x instanceof PersonDtlsResponse) {
				PersonDtlsResponse respo=(PersonDtlsResponse) x;
				a.add(respo);
			}
		});
		ParentChildResponse response=new ParentChildResponse();
		response.setResponse(a);
		return response;
	}

	@LogMethodParam
	private ParentChildResponse prepareResponse(List<PersonRequestDTO> personDtls) {

		Map<Integer, List<PersonRequestDTO>> m = personDtls.stream().collect(Collectors.groupingBy(PersonRequestDTO::getParentId));

		ParentChildResponse parentChildResponse = new ParentChildResponse();

		List<PersonDtlsResponse> personDtlsList = personDtls.stream().map(x -> {
			PersonDtlsResponse response = new PersonDtlsResponse();
			response.setName(x.getName());

			List<ChildDetailsDTO> subClasses = new ArrayList<ChildDetailsDTO>();

			List<PersonRequestDTO> subclasses = m.get(x.getId());
			if (subclasses != null && !subclasses.isEmpty()) {
				subclasses.forEach(y -> {

					ChildDetailsDTO childDtls = new ChildDetailsDTO();
					childDtls.setName(y.getName());
					subClasses.add(childDtls);
				});
			}
		//	response.setSubClasses(subClasses);
			return response;
		}).collect(Collectors.toList());
		
		parentChildResponse.setResponse(personDtlsList.stream()
				.filter(x -> x.getSubClasses() != null && !x.getSubClasses().isEmpty()).collect(Collectors.toList()));
		return parentChildResponse;

	}
	
	@LogMethodParam
	//Using Recursive mechanism
	private List<Object> buildParentChildReltn(List<PersonRequestDTO> personDtls,int id) {
		List<Object> parentClasses = new ArrayList<Object>();
        for (PersonRequestDTO person : personDtls) {
            if (person.getParentId() == id) {
                List<Object> subclasses = buildParentChildReltn(personDtls, person.getId());
                if (subclasses.isEmpty()) {
                	parentClasses.add(new PersonDtlsResponse(person.getName()));
                } else {
                	parentClasses.add(new PersonDtlsResponse(person.getName(), subclasses));
                }
            }
        }
		return parentClasses;
	}
}
