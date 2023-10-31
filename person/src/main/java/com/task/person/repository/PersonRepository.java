package com.task.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task.person.entity.Person;

@Repository("personRepository")
public interface PersonRepository extends JpaRepository<Person, Integer>{

}
