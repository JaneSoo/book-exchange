package org.acme.getting.started.entity;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity(name = "\"user\"")
public class User extends PanacheEntity {
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String phoneNumber;

    public static void add(String firstName, String lastName, String email, String password, String phoneNumber){
        User user = new User();
        user.firstName = firstName;
        user.lastName = lastName;
        user.email = email;
        user.password = BcryptUtil.bcryptHash(password);
        byte[] salt = new byte[16];
        user.password = BcryptUtil.bcryptHash(password, 4, salt);
        user.persist();
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", id=" + id +
                '}';
    }

    public String getPassword() {
        return password;
    }
}
