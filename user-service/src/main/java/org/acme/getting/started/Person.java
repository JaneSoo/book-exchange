package org.acme.getting.started;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Person extends PanacheEntity {
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String phoneNumber;

    public static void add(String firstName, String lastName, String email, String password, String phoneNumber){
        Person person = new Person();
        person.firstName = firstName;
        person.lastName = lastName;
        person.email = email;
        person.password = BcryptUtil.bcryptHash(password);
        person.persist();
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", id=" + id +
                '}';
    }

}
