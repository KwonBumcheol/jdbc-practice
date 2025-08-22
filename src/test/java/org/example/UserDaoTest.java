package org.example;

import org.h2.engine.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserDaoTest {
    // TDD


    @BeforeEach
    void setUp() { // 테스트코드 실행 전에 실행할 코드
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db_schema.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    void createTest() {
        UserDao userDao = new UserDao(); // Dao = Data access object -- usarDao 클래스 생성

        userDao.create(new User("wizard", "password", "name", "email")); // DB 저장

        User user = userDao.findByUserId("wizard"); // userId 조회
        assertThat(user).isEqualTo(new User("wizard", "password", "name", "email")); // 위의 정보와 동일한지 점검
    }
}
