package com.nagarro.s4m.repository;

import com.nagarro.s4m.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
