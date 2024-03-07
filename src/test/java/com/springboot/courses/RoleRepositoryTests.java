package com.springboot.courses;

import com.springboot.courses.entity.Role;
import com.springboot.courses.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTests {

    @Autowired private RoleRepository roleRepository;

    @Test
    public void testSaveRoleRepository(){
        Role role1 = new Role();
        role1.setName("Admin");
        Role role2 = new Role();
        role2.setName("Customer");

        roleRepository.saveAll(List.of(role1, role2));
    }
}
