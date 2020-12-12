package org.acme.getting.started;

import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

@ApplicationScoped
public class PersonGenerator {
    @Transactional
    public void loadUsers(@Observes StartupEvent event) {
        Person.deleteAll();
        Person.add("Jonh", "Doe", "jonh@email.com", "passwordforjonh", "1234567890");
        Person.add("Jake", "Daniel", "jake@email.com", "passwordforjake", "1234567890");
        Person.add("Rose", "Kim", "rose@email.com", "passwordforrose", "12345678901");
        Person.add("Jimmy", "Ly", "jimmy@email.com", "passwordforjimmy", "123456789012");
    }
}