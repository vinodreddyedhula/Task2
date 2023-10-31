package com.task.person.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PersonRequestDTO {

	@Schema(description = "Id of the person")
	@NotBlank(message = "ID is required.")
	private int id;

	@Schema(description = "ParentId of the person")
	@NotBlank(message = "ParentIdis required.")
	private int parentId;

	@Schema(description = "Name of the person")
	@NotBlank(message = "The Name is required.")
	@Size(min = 1, max = 15, message = "Name should be between 1 and 15")
	private String name;

	@Schema(description = "Color")
	@NotBlank(message = "Color is required.")
	private String color;

}
