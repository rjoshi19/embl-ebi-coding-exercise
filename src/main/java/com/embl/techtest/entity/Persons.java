package com.embl.techtest.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class Persons {
    private List<Person> person = new ArrayList<>();

    public List<Person> getPerson() {
        return person;
    }

    public void setPerson(List<Person> person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("person", person)
                .toString();
    }

}
