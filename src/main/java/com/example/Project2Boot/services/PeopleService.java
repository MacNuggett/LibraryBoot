package com.example.Project2Boot.services;

import com.example.Project2Boot.models.Person;
import com.example.Project2Boot.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(int id){
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public void save(Person person){
        peopleRepository.save(person);
    }

    public void update(int id, Person person){
        person.setId(id);
        peopleRepository.save(person);
    }

    public void delete(int id){
        peopleRepository.deleteById(id);
    }
}
