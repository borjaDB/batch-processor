package com.example.demo.chunk;

import com.example.demo.model.Person;
import com.example.demo.model.PersonInfo;
import com.example.demo.model.Profession;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, PersonInfo> {

    @Override
    public PersonInfo process(Person person) throws Exception {

        Profession profession = new Profession();
        profession.setFirstName(person.getFirstName());
        profession.setLastName(person.getLastName());
        profession.setProfession("Teacher");

        PersonInfo personInfo = new PersonInfo();
        personInfo.setPerson(person);
        personInfo.setProfession(profession);

        return personInfo;
    }
}


