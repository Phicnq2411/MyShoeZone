package com.philconnal.shoezone.controller;


import com.philconnal.shoezone.common.exception.anotationvalidation.BadRequestException;
import com.philconnal.shoezone.controller.api.APIName;
import com.philconnal.shoezone.controller.helper.ProductHelper;
import com.philconnal.shoezone.controller.request.CreateProductRequest;
import com.philconnal.shoezone.controller.response.RestApiResponse;
import com.philconnal.shoezone.entity.Product;
import com.philconnal.shoezone.service.ProductService;
import com.philconnal.shoezone.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(APIName.PRODUCT)
public class ProductController {
    private final ResponseUtil responseUtil;
    private final ProductService productService;
    private final ProductHelper productHelper;

    public ProductController(ResponseUtil responseUtil, ProductService productService, ProductHelper productHelper) {
        this.responseUtil = responseUtil;
        this.productService = productService;

        this.productHelper = productHelper;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<RestApiResponse> getAllProducts() {
        List<Product> allProducts = productService.getAllProducts();
        return responseUtil.successResponse(allProducts);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestApiResponse> addProduct(@Valid @RequestBody CreateProductRequest request) {
        try {
            Product product = productHelper.createProduct(request);
            productService.saveProduct(product);
            return responseUtil.successResponse(product);
        } catch (BadRequestException exception) {
            throw new RuntimeException(exception);
        }
    }
}
