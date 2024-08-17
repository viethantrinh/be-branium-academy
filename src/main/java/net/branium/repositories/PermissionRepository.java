package net.branium.repositories;

import net.branium.domains.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, String> {

    @Query("SELECT p FROM Permission p JOIN p.roles r WHERE r.name = ?1")
    List<Permission> findAllByRoleName(String roleName);

}
