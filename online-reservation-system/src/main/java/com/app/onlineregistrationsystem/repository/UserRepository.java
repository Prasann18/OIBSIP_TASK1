package com.app.onlineregistrationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.onlineregistrationsystem.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    
    @Query(value = "select name from roles where id = (select role_id from users_roles where user_id = (select id from users where email=?1))", nativeQuery = true)
    String findRoleByEmail(String email);

}
