package com.philconnal.shoezone.repository;

import com.philconnal.shoezone.entity.NewUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "new_user")
public interface NewerRepository extends PagingAndSortingRepository<NewUser, Long> {


}
