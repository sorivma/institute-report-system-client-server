package com.institute.instituteserver.repository;

import com.institute.instituteserver.entity.Role;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReportRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;
    private final Role adminRole = Role.builder()
            .name("admin")
            .build();

    @Test
    public void testInitData() {
        roleRepository.exists(Example.of(
                adminRole
        ));
    }

    @Test
    public void testSaveRole() {
        Role testRole = Role.builder()
                .name("test1")
                .build();

        roleRepository.save(
                testRole
        );

        assertTrue(
                roleRepository.exists(Example.of(
                        testRole
                ))
        );
    }
}