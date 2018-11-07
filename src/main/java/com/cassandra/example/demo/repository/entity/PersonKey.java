package com.cassandra.example.demo.repository.entity;

import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.data.cassandra.core.cql.Ordering.DESCENDING;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

@PrimaryKeyClass
public class PersonKey implements Serializable {

  @PrimaryKeyColumn(name = "first_name", type = PARTITIONED)
  private String firstName;

  @PrimaryKeyColumn(name = "date_of_birth", ordinal = 0)
  private LocalDateTime dateOfBirth;

  @PrimaryKeyColumn(name = "person_id", ordinal = 1, ordering = DESCENDING)
  private UUID id;

  public PersonKey(final String firstName, final LocalDateTime dateOfBirth, final UUID id) {
    this.firstName = firstName;
    this.id = id;
    this.dateOfBirth = dateOfBirth;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public LocalDateTime getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDateTime dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PersonKey)) return false;
    PersonKey personKey = (PersonKey) o;
    return Objects.equals(id, personKey.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}