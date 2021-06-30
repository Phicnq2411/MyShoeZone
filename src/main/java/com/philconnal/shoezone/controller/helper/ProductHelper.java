package com.philconnal.shoezone.controller.helper;

import com.philconnal.shoezone.common.enums.ProductStatus;
import com.philconnal.shoezone.common.enums.ProductType;
import com.philconnal.shoezone.common.exception.errors.MyBadRequestException;
import com.philconnal.shoezone.common.exception.errors.MyNotFoundException;
import com.philconnal.shoezone.controller.request.CreateProductRequest;
import com.philconnal.shoezone.entity.Branch;
import com.philconnal.shoezone.entity.Product;
import com.philconnal.shoezone.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class ProductHelper {
    @Autowired
    private BranchService branchService;

    public Product createProduct(CreateProductRequest request) {
        final Product product = new Product();
        product.setName(request.getName().trim());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription().trim());
        product.setStatus(ProductStatus.AVAILABLE);
        product.setImgUrl(request.getImgUrl().trim());
        Optional<ProductType> first = Arrays.stream(ProductType.values())
                .filter(productType -> productType.ordinal() == request.getType())
                .findFirst();
        ProductType type = first.orElseThrow(() -> new MyBadRequestException("invalid type"));
        product.setType(type);
        Optional<ProductStatus> status = Arrays.stream(ProductStatus.values())
                .filter(productType -> productType.ordinal() == request.getStatus())
                .findFirst();
        ProductStatus productStatus = status.orElseThrow(() -> new MyBadRequestException("invalid product status"));
        product.setStatus(productStatus);
        Long id = request.getBranchID();
        Branch branch = branchService.getBranchById(id);
        if (branch == null)
            throw new MyNotFoundException(String.format("Branch with id %s not found", id));
        product.setBranch(branch);
        return product;
    }
}
