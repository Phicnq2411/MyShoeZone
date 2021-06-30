package com.philconnal.shoezone.controller.helper;

import com.philconnal.shoezone.common.exception.errors.MyBadRequestException;
import com.philconnal.shoezone.common.exception.errors.MyExistedException;
import com.philconnal.shoezone.controller.request.CreateBranchRequest;
import com.philconnal.shoezone.controller.request.UpdateBranchRequest;
import com.philconnal.shoezone.entity.Branch;
import com.philconnal.shoezone.service.BranchService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Data
public class BranchHelper {
    @Autowired
    private BranchService branchService;

    public Branch createBranch(CreateBranchRequest request) {
        Branch branch = new Branch();
        branch.setAddress(request.getAddress().trim());
        branch.setName(request.getName().trim());
        return branch;
    }

    public void updateBranch(UpdateBranchRequest request, Branch branch) {
        if (request.getName() != null) {
            if (request.getName().isEmpty())
                throw new MyBadRequestException("Branch name is empty");
            if (!request.getName().trim().equals(branch.getName())) {
                final Branch branchByBranchName = branchService.getBranchByBranchName(request.getName().trim());
                if (branchByBranchName != null)
                    throw new MyExistedException(String.format("Branch name %s already exist!", request.getName().trim()));
                branch.setName(request.getName().trim());
            }

        } else {
            throw new MyBadRequestException("Branch name is null");
        }
        if (request.getAddress() != null) {
            if (request.getAddress().trim().isEmpty())
                throw new MyBadRequestException("Branch Address is empty");
            branch.setAddress(request.getAddress().trim());
        } else {
            throw new MyBadRequestException("Branch address is null");
        }
    }
}
