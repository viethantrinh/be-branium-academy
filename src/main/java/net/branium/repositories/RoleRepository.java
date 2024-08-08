package net.branium.repositories;

import net.branium.domains.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT a FROM Role a WHERE a.name=:name")
    Optional<Role> findByName(String name);
}
