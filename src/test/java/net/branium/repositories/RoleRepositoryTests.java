package net.branium.repositories;

import net.branium.constants.RoleEnum;
import net.branium.domains.Role;
import net.branium.exceptions.ApplicationException;
import net.branium.exceptions.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTests {

    @Autowired
    RoleRepository roleRepo;

    @Test
    void testGetRoleByNameSuccessful() {
        String roleName = RoleEnum.ROLE_STUDENT.getName();
        Role role = roleRepo.findById(roleName)
                .orElseThrow(() -> new ApplicationException(ErrorCode.ROLE_NON_EXISTED));
        assertDoesNotThrow(() -> new ApplicationException(ErrorCode.ROLE_NON_EXISTED));
        assertNotNull(role);
    }
}
