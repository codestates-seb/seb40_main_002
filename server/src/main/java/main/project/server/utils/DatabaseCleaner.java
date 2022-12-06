package main.project.server.utils;

import org.springframework.stereotype.Component;

import com.google.common.base.CaseFormat;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DatabaseCleaner {
    private final EntityManager entityManager;
    private final List<String> tableNames;

    public DatabaseCleaner(EntityManager entityManager, List<String> tableNames) {
        this.entityManager = entityManager;
        this.tableNames = entityManager.getMetamodel()
                .getEntities()
                .stream()
                .map(EntityType::getName)
                .map(e -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e))
                .collect(Collectors.toList());
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET foreign_key_checks = 0").executeUpdate(); // Foreign Key의 제약조건 끄기

        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        }

        entityManager.createNativeQuery("SET foreign_key_checks = 1").executeUpdate();
    }
}
