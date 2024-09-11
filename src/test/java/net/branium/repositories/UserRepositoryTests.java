package net.branium.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTests {

    @Autowired
    UserRepository userRepo;

    public void givenUserObject_whenSave_thenReturnSavedEmployee
}
