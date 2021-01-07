package org.acme.getting.started;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class UserService {
    private Logger log = Logger.getLogger(UserService.class);

    @Transactional
    public User create(User user){
        user.persist();
        return user;
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


}
