package main.project.server.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DatabaseCleanup implements InitializingBean { //빈 초기화시 작업을 명시할 수 있게 해주는 인터페이스 구현

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    List<EntityType<?>> collect;

    @Override
    public void afterPropertiesSet() throws Exception {

        collect = entityManager.getMetamodel().getEntities().stream()
                .filter(e -> e.getJavaType().getAnnotation(Entity.class) != null).collect(Collectors.toList());

        tableNames = List.of("ddd","sss");
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
            entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN ID RESTART WITH 1").executeUpdate();
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}
