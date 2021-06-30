package com.philconnal.shoezone.repository;

import com.philconnal.shoezone.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
