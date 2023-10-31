package com.task.person.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name="PERSON")
@Table
public class Person implements Serializable {

	private static final long serialVersionUID = 5706428746308831438L;

	
	@Id
	@Column
	private int id;
	
	@Column
	private String parentId;
	
	@Column
	private String name;
	
	@Column
	private String color;
	
	
}
