package org.acme.getting.started;
import io.quarkus.runtime.StartupEvent;
import org.acme.getting.started.entity.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

@ApplicationScoped
public class BookGenerator {

    @Transactional
    public void loadBooks(@Observes StartupEvent event) {
        Book.deleteAll();
        Book.add("978-3836277471");
        Book.add("978-3836277372");
        Book.add("978-1983399404");
        Book.add("978-3966450614");
        System.out.println("init data");
    }
}