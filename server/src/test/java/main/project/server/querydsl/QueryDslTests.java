package main.project.server.querydsl;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class QueryDslTests {

    @Autowired
    EntityManager em;

    @Test
    void basicTest() {




    }
}
