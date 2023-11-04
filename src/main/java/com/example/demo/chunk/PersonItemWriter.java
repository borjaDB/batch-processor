package com.example.demo.chunk;

import com.example.demo.model.Person;
import com.example.demo.model.PersonInfo;
import com.example.demo.model.Profession;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


public class PersonItemWriter implements ItemWriter<PersonInfo> {

    @Autowired
    private DataSource dataSource;

    @Override
    public void write(Chunk<? extends PersonInfo> chunk) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        for(int i = 0; i<chunk.size(); i++) {
            Person person = chunk.getItems().get(i).getPerson();
            Profession profession = chunk.getItems().get(i).getProfession();

            String queryPerson = "INSERT INTO people (first_name, last_name) VALUES ('"+person.getFirstName()+"','"+person.getLastName()+"');";
            String queryProfession = "INSERT INTO profession (first_Name, last_Name, profession) VALUES ('"+profession.getFirstName()+"','"+profession.getLastName()+"','"+profession.getProfession()+"');";

            jdbcTemplate.execute(queryPerson+queryProfession);
        }
    }
}
