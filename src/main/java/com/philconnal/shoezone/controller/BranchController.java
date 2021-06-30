package com.philconnal.shoezone.controller;

import com.philconnal.shoezone.common.exception.anotationvalidation.BadRequestException;
import com.philconnal.shoezone.common.exception.errors.MyExistedException;
import com.philconnal.shoezone.common.exception.errors.MyNotFoundException;
import com.philconnal.shoezone.controller.api.APIName;
import com.philconnal.shoezone.controller.helper.BranchHelper;
import com.philconnal.shoezone.controller.request.CreateBranchRequest;
import com.philconnal.shoezone.controller.request.UpdateBranchRequest;
import com.philconnal.shoezone.controller.response.RestApiResponse;
import com.philconnal.shoezone.entity.Branch;
import com.philconnal.shoezone.service.BranchService;
import com.philconnal.shoezone.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(APIName.BRANCH)
public class BranchController {
    private final ResponseUtil responseUtil;
    private final BranchService branchService;
    private final BranchHelper branchHelper;

    public BranchController(ResponseUtil responseUtil, BranchService branchService, BranchHelper branchHelper) {
        this.responseUtil = responseUtil;
        this.branchService = branchService;
        this.branchHelper = branchHelper;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<RestApiResponse> getAllBranches() {
        final List<Branch> allBranches = branchService.getAllBranches();
        return responseUtil.successResponse(allBranches);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestApiResponse> addBranch(@Valid @RequestBody CreateBranchRequest request) {
        try {
            final Branch branchByBranchName = branchService.getBranchByBranchName(request.getName().trim());
            if (branchByBranchName != null)
                throw new MyExistedException(String.format("Branch name %s already exist!", request.getName().trim()));

            Branch branch = branchHelper.createBranch(request);
            branchService.saveBranch(branch);
            return responseUtil.successResponse(branch);
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.PUT, path = "{id}")
    public ResponseEntity<RestApiResponse> updateBranch(@RequestBody UpdateBranchRequest request, @PathVariable Long id) {
        Branch branchById = branchService.getBranchById(id);
        if (branchById == null)
            throw new MyNotFoundException(String.format("Branch with id %s not found", id));

        branchHelper.updateBranch(request, branchById);
        branchService.saveBranch(branchById);

        return responseUtil.successResponse(branchById);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.DELETE, path = "{id}")
    public ResponseEntity<RestApiResponse> deleteBranch(@PathVariable Long id) {
        Branch branchById = branchService.getBranchById(id);
        if (branchById == null)
            throw new MyNotFoundException(String.format("Branch with id %s not found", id));
        branchService.deleteBranch(id);
        return responseUtil.successResponse("Delete successfully!");

    }

}
