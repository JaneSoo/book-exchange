package org.acme;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserContext {
    private Long userId;

    public long getUserId() throws Exception {
        if (userId == null)
            throw new Exception("Must login first");
        return userId;
    }

    public void setUserId(Long id){
        userId = id;
    }
}
