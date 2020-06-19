package com.cx.prod.list.repos.users;

import com.cx.prod.list.model.users.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByPhoneNumber(String phoneNumber);

    @Query("from User where lower(lastName) like lower(:lastName) order by birthDate")
    List<User> findByLastNameOrderByBirthDate(@Param("lastName") String name, Pageable pageable);

    @Query("from User where phoneNumber is not null and birthDate <= all(select birthDate from User)")
    List<User> findOldestUserWithPhoneNumber(Pageable pageable);

    @Query("select count(u) from User u")
    Integer getTotalUserCount();
}
