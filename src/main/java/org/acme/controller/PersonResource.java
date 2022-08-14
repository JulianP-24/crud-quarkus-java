package org.acme.controller;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.acme.entity.Person;
import org.acme.repository.*;

@Path("/persona")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    private PersonRepository personRepository;

    @GET
    public Response ListAll() {
        List<Person> personas = personRepository.listAll();
        return Response.ok().entity(personas).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Person persona = personRepository.getPersonById(id);
        if (persona == null) {
            throw new WebApplicationException("Person with id of " + id + " does not exist.", 404);
        }
        return Response.ok().entity(persona).build();
    }

    @GET
    @Path("/search/{name}")
    public Response getPersonByName(@PathParam("name") String name) {
        List<Person> persona = personRepository.findByName(name);
        return Response.ok().entity(persona).build();
    }

    @POST
    @Transactional
    public Response create(Person persona) {
        personRepository.persist(persona);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT 
    @Path("/{id}")
    @Transactional
    public Person update(@PathParam("id") Long id, Person person) {
        Person persona = personRepository.findById(id);
        if (persona == null) {
            throw new WebApplicationException("Person with id of " + id + " does not exist.", 404);
        }
        if (!(persona.getName().equals(person.getName()))) {
            persona.setName(person.getName());
            persona.setFechaNacimiento(person.getFechaNacimiento());
        } else {
            persona.setFechaNacimiento(person.getFechaNacimiento());
        }
        return persona;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletePersonById(@PathParam("id") Long id) {
        personRepository.deleteById(id);
        return Response.status(Response.Status.CREATED).build();
    }

}
