package org.acme.getting.started.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import org.acme.getting.started.entity.User;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class UserService {
    private Logger log = Logger.getLogger(UserService.class);
    private String email;
    private String password;

    @Transactional
    public User create(User user){
        byte[] salt = new byte[16];
        user.password = BcryptUtil.bcryptHash(user.password, 4, salt);
        user.persist();
        return user;
    }

    @Transactional
    public long login(JsonObject userCredential) {
        userCredential.entrySet().forEach(entry -> {
            String value = entry.getValue().toString().replace("\"", "");
            switch (entry.getKey()) {
                case "email":
                    email = value;
                    break;
                case "password":
                    password = value;
                    break;
                default:
                    throw new IllegalArgumentException("unknown property");
            }
        });

        if (email == null) {
            throw new IllegalArgumentException("Cannot login without email");
        }
        User user = User.find("email", email).firstResult();
        if (password == null) {
            throw new IllegalArgumentException("Impossible to authenticate customer without password");
        }
        byte[] salt = new byte[16];
        password = BcryptUtil.bcryptHash(password, 4, salt);
        System.out.println("try hashed to compoare: " + BcryptUtil.bcryptHash(password));

        System.out.println(user.password);
        if (password.equals(user.password)){
            return user.id;
        } else {
            throw new IllegalArgumentException("Incorrect login");
        }
    }

    @Transactional
    public User update(long id, JsonObject updateJson){
        User user = User.findById(id);
        if (user == null){
            return null;
        }

        updateJson.entrySet().forEach(entry -> {
            String value = entry.getValue().toString().replace("\"", "");
            switch (entry.getKey()) {
                case "id":
                    user.id = Long.valueOf(value);
                    break;
                case "firstName":
                    user.firstName = value;
                    break;
                case "lastName":
                    user.lastName = value;
                    break;
                case "email":
                    user.email = value;
                    break;
                case "password":
                    user.email = value;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown property provided for update user: " + entry.getKey());
            }
        });
        user.persist();
        return user;
    }

    @Transactional
    public User delete(long id){
        User user = User.findById(id);
        if (user == null){
            throw new NotFoundException("Cannot find user with id: " + id);
        }
        boolean deleted = User.deleteById(id);
        return deleted ? user : null;
    }

    @Transactional
    public User findById(long id) {
        User user = User.findById(id);
        return user;
    }


}
