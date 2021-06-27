package com.philconnal.shoezone.repository;

import com.philconnal.shoezone.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "user")
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    @Query(value = "select distinct * from user u " +
            "where u.username=:username " +
            "and u.status=0",nativeQuery = true)
    User getOneByUsername(String username);
    @Query(value = "select distinct * from user u " +
            "where u.status = 0 " +
            "order by u.username,u.email,u.role",nativeQuery = true)
    List<User> getAllUsers();
    @Query(value = "select distinct * from user u " +
            "where u.email=:email " +
            "and u.status=0",nativeQuery = true)
    User getOneByEmail(String email);

}
