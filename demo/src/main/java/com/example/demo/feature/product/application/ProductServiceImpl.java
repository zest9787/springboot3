package com.example.demo.feature.product.application;

import com.example.demo.feature.product.application.dto.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductServiceImpl implements ProductService {

    List<Product> productList;

    @PostConstruct
    public void loadProductFromDB() {
        productList = IntStream.range(1, 100)
                .mapToObj(i -> Product.builder()
                        .productId(i)
                        .name("product " + i)
                        .qty(new Random().nextInt(10))
                        .price(new Random().nextInt(5000))
                        .build()
                ).collect(Collectors.toList());
    }

    @Override
    public List<Product> getProducts() {
        return productList;
    }

    @Override
    public Product getProduct(int id) {
        return productList.stream()
                .filter(product -> product.getProductId() == id)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Product " + id + " not found."));
    }
}
