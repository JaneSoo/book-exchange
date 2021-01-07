package org.acme.getting.started;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class PersonService {
    private Logger log = Logger.getLogger(PersonService.class);

    @Transactional
    public Person create(Person person){
        person.persist();
        return person;
    }

    @Transactional
    public Person update(long id, JsonObject updateJson){
        Person person = Person.findById(id);
        if (person == null){
            return null;
        }

        updateJson.entrySet().forEach(entry -> {
            String value = entry.getValue().toString().replace("\"", "");
            switch (entry.getKey()) {
                case "id":
                    person.id = Long.valueOf(value);
                    break;
                case "firstName":
                    person.firstName = value;
                    break;
                case "lastName":
                    person.lastName = value;
                    break;
                case "email":
                    person.email = value;
                    break;
                case "password":
                    person.email = value;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown property provided for update user: " + entry.getKey());
            }
        });
        person.persist();
        return person;
    }

    @Transactional
    public Person delete(long id){
        Person person = Person.findById(id);
        if (person == null){
            throw new NotFoundException("Cannot find user with id: " + id);
        }
        boolean deleted = Person.deleteById(id);
        return deleted ? person : null;
    }


}
