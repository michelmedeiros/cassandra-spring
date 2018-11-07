package com.cassandra.example.demo.repository;

import com.cassandra.example.demo.repository.entity.Person;
import com.cassandra.example.demo.repository.entity.PersonKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PersonRepository extends CassandraRepository<Person, PersonKey> {

  List<Person> findByKeyFirstName(final String firstName);

  List<Person> findByKeyFirstNameAndKeyDateOfBirthGreaterThan(
      final String firstName, final LocalDateTime dateOfBirth);

  // Don't do this!!
  @Query(allowFiltering = true)
  List<Person> findByLastName(final String lastName);

}