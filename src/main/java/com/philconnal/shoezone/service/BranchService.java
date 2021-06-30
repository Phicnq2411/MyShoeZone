package com.philconnal.shoezone.service;

import com.philconnal.shoezone.entity.Branch;

import java.util.List;

public interface BranchService {
    Branch getBranchById(Long id);

    Branch getBranchByBranchName(String name);

    List<Branch> getAllBranches();

    void deleteBranch(Long id);

    void saveBranch(Branch branch);
}
