package net.branium.repositories;

import net.branium.constants.AuthorityConstants;
import net.branium.domains.Permission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class PermissionRepositoryTests {

    @Autowired
    PermissionRepository permissionRepo;

    @Test
    void testGetAllPermissionsByRoleNameAssociatedSuccessful() {
        List<Permission> permissions = permissionRepo.findAllByRoleName(AuthorityConstants.ROLE_ADMIN);
        permissions.forEach(p -> System.out.println(p));
    }
}