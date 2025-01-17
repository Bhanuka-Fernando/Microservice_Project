package com.example.product.service;


import com.example.product.dto.ProductDTO;
import com.example.product.model.Product;
import com.example.product.repo.ProductRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ModelMapper modelMapper;

    // get all products
    public List<ProductDTO> getAllProducts(){
        List<Product> productList = productRepo.findAll();
        return modelMapper.map(productList, new TypeToken<List<ProductDTO>>(){}.getType());
    }

    // get product by ID
    public ProductDTO getProductById(Integer productId){
        Product product = productRepo.getProductById(productId);
        return modelMapper.map(product, ProductDTO.class);
    }

    // save a product
    public ProductDTO saveProduct(ProductDTO productDTO){
        productRepo.save(modelMapper.map(productDTO, Product.class));
        return productDTO;
    }

    // update a product
    public ProductDTO updateProduct(ProductDTO productDTO){
        productRepo.save(modelMapper.map(productDTO, Product.class));
        return productDTO;
    }

    // delete a product
    public String deleteProduct(Integer itemId){
        productRepo.deleteById(itemId);
        return "Product deleted";
    }

}
