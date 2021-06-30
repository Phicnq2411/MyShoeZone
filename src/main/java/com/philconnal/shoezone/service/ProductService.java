package com.philconnal.shoezone.service;

import com.philconnal.shoezone.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductService {
    List<Product> getAllProducts();

    void saveProduct(Product product);

    void deleteProduct(Long id);

    Optional<Product> getProductByID(Long id);

}
