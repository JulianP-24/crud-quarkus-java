package org.acme.repository;

import java.util.List;


import javax.enterprise.context.ApplicationScoped;

import org.acme.entity.Person;


import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {
    public List<Person> findByName(String PersonName) {
        return list("name", PersonName);
    }

    public Person getPersonById(Long id) {
        return findById(id);
    }
}
