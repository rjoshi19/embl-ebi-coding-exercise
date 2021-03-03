package com.embl.techtest.controller;

import com.embl.techtest.entity.Person;
import com.embl.techtest.entity.Persons;
import com.embl.techtest.exception.ResourceNotFoundException;
import com.embl.techtest.repository.PersonRepository;
import io.swagger.annotations.*;
import org.apache.tomcat.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Api(value = "Persons", tags = "Persons", description = "REST Controller for Persons API. All APIs require Authorization for execution.")
public class PersonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);
    private PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @ApiOperation(value = "View a list of available persons", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/persons")
    public List<Person> getAllPersons() {
        LOGGER.info("Getting all persons in the database");
        return personRepository.findAll();
    }

    @ApiOperation(value = "Get details of a person by ID", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a person"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Person does not exist for the given ID")
    })
    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> getPersonById(@ApiParam(value = "ID returned in the response when person was stored in the database using /persons POST endpoint")
                                                @PathVariable(value = "id") Long personId)
            throws Exception {
        LOGGER.info("Getting person in the database by id::" + personId);
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> {
                    LOGGER.error("Person not found for id::" + personId);
                    return new ResourceNotFoundException("Person not found for this id :: " + personId);
                });
        return ResponseEntity.ok().body(person);
    }

    @ApiOperation(value = "Save persons in the database", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully saved person/s"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Some error occurred internally while saving persons in the database")
    })
    @PostMapping("/persons")
    public List<Person> createPerson(@RequestBody Persons persons) {
        LOGGER.info("Saving persons in the database");
        LOGGER.debug("Persons being saved::" + persons);
        List<Person> res = new ArrayList<>();

        List<Person> personObjs = persons.getPerson();
        for (Person person : personObjs) {
            Person save = personRepository.save(person);
            res.add(save);
        }

        return res;
    }

    @ApiOperation(value = "Update details of a person in the database by ID", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated details of a person"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Some error occurred internally while updating person details in the database")
    })
    @PutMapping("/persons/{id}")
    public ResponseEntity<Person> updatePerson(@ApiParam(value = "ID returned in the response when person was stored in the database using /persons endpoint")
                                               @PathVariable(value = "id") Long personId,
                                               @RequestBody Person personDetails) throws Exception {
        LOGGER.debug("Updating Person for id::" + personId);
        LOGGER.debug("Person Details::" + personDetails);

        LOGGER.info("Updating Person");

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> {
                    LOGGER.error("Person not found for id::" + personId);
                    return new ResourceNotFoundException("Person not found for this id :: " + personId);
                });

        person.setAge(personDetails.getAge());
        person.setLast_name(personDetails.getLast_name());
        person.setFirst_name(personDetails.getFirst_name());
        person.setAge(personDetails.getAge());
        person.setFavourite_colour(personDetails.getFavourite_colour());
        final Person updatedPerson = personRepository.save(person);
        return ResponseEntity.ok(updatedPerson);
    }

    @ApiOperation(value = "Delete person for given ID", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted a person"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 404, message = "Person does not exist for given ID"),
            @ApiResponse(code = 500, message = "Some error occurred internally while deleting persons in the database")
    })
    @DeleteMapping("/persons/{id}")
    public Map<String, Boolean> deletePerson(@ApiParam(value = "ID returned in the response when person was stored in the database using /persons endpoint")
                                             @PathVariable(value = "id") Long personId)
            throws Exception {
        LOGGER.info("Deleting Person");
        LOGGER.debug("Deleting Person with id::" + personId);
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> {
                    LOGGER.error("Person not found for id::" + personId);
                    return new ResourceNotFoundException("Person not found for this id :: " + personId);
                });

        personRepository.delete(person);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
