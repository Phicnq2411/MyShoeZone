package com.philconnal.shoezone.repository;

import com.philconnal.shoezone.entity.Branch;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BranchRepository extends PagingAndSortingRepository<Branch,Long> {
    Branch findBranchByName(String name);
    Branch findBranchById(Long id);
}
