package com.philconnal.shoezone.service;

import com.philconnal.shoezone.entity.Branch;
import com.philconnal.shoezone.repository.BranchRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;

    public BranchServiceImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public Branch getBranchById(Long id) {
        return branchRepository.findBranchById(id);
    }

    @Override
    public Branch getBranchByBranchName(String name) {
        return branchRepository.findBranchByName(name);
    }

    @Override
    public List<Branch> getAllBranches() {
        return (List<Branch>) branchRepository.findAll();
    }

    @Override
    public void deleteBranch(Long id) {
        branchRepository.deleteById(id);
    }

    @Override
    public void saveBranch(Branch branch) {
        branchRepository.save(branch);
    }
}
