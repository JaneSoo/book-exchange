package org.acme.getting.started;

import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserGenerator {
    @Transactional
    public void loadUsers(@Observes StartupEvent event) {
        User.deleteAll();
        User.add("Jonh", "Doe", "jonh@email.com", "passwordforjonh", "1234567890");
        User.add("Jake", "Daniel", "jake@email.com", "passwordforjake", "1234567890");
        User.add("Rose", "Kim", "rose@email.com", "passwordforrose", "12345678901");
        User.add("Jimmy", "Ly", "jimmy@email.com", "passwordforjimmy", "123456789012");
        System.out.println("init data");
    }
}