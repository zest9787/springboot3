package com.example.demo.feature.product.ui;

import com.example.demo.feature.product.application.ProductService;
import com.example.demo.feature.product.application.dto.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable int id) {
        return productService.getProduct(id);
    }
}
