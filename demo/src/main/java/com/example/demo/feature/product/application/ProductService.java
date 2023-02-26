package com.example.demo.feature.product.application;

import com.example.demo.feature.product.application.dto.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts();

    Product getProduct(int id);
}
