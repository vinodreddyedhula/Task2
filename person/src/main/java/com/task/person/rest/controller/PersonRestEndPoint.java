package com.task.person.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.person.aspect.LogMethodParam;
import com.task.person.interfaces.PersonService;
import com.task.person.request.PersonRequestDTO;
import com.task.person.request.SuccessResponse;
import com.task.person.response.ParentChildResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/v1/person")
@Slf4j
public class PersonRestEndPoint {

	@Autowired
	private PersonService service;

	@ApiOperation(value = "Get Parent-Child Relationship Details")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully Created", response = SuccessResponse.class),
			@ApiResponse(code = 400, message = "Invalid Request"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal validation error") })
	@GetMapping("/parent-child/details")
	@LogMethodParam
	public ResponseEntity<SuccessResponse<ParentChildResponse>> getParentChildRltnDtls() {
		log.info("---getting the Parent-Child Relationship details---");
		return ResponseEntity.ok(new SuccessResponse(service.getParentChildRelation()));
	}

	@ApiOperation(value = "Get all Person Dtls")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully Created", response = SuccessResponse.class),
			@ApiResponse(code = 400, message = "Invalid Request"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal validation error") })
	@GetMapping("/details/")
	@LogMethodParam
	public ResponseEntity<SuccessResponse<List<PersonRequestDTO>>> getPersonDtls() {
		log.info("---getting the details of whole entity---");
		return ResponseEntity.ok(new SuccessResponse(service.getPersonDtls()));
	}

	@ApiOperation(value = "Get PersonDtls byId")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully Created", response = SuccessResponse.class),
			@ApiResponse(code = 400, message = "Invalid Request"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal validation error") })
	@GetMapping("/details/{id}")
	@LogMethodParam
	public ResponseEntity<SuccessResponse<PersonRequestDTO>> addPersonDtls(@Valid @PathVariable int id) {
		log.info("---getting the details of a parent byID---");
		return ResponseEntity.ok(new SuccessResponse(service.getPersonDtlsById(id)));
	}

}
