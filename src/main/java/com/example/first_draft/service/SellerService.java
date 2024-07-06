package com.example.first_draft.service;

import com.example.first_draft.controller.SellerController;
import com.example.first_draft.entity.*;
import com.example.first_draft.repository.CategoryRepository;
import com.example.first_draft.repository.ProductRepository;
import com.example.first_draft.repository.SellerRepository;
import com.example.first_draft.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Seller createSeller(Seller seller) {
        User user = seller.getUser();
        if (user != null) {
            user = userRepository.save(user);
            seller.setUser(user);
        }
        return sellerRepository.save(seller);
    }

    @Transactional
    public Product addProductToSeller(Long sellerId, Product product, List<MultipartFile> files, List<String> colors, List <MultipartFile> f) throws IOException {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));
           List<Color> colorsList = product.getColors();
        Set<String> colorNames = new HashSet<>();
        for(Color color : colorsList) {
            colorNames.add(color.getName().toLowerCase());
        }

        for(String color : colors) {
                    if(!colorNames.contains(color.toLowerCase())) {
                        throw new RuntimeException("Color not found");
                    }
                }

        Category existingCategory = categoryRepository.findById(product.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<ProductImage> productImages = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String color = colors.get(i);

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(SellerController.uploadDirectory, fileName);
            Files.write(filePath, file.getBytes());

            ProductImage productImage = new ProductImage();
            productImage.setImagePath(fileName);
            productImage.setImageColor(color);
//            productImage.setProduct(product);

            productImages.add(productImage);
        }
        List<Banner> banners = new ArrayList<>();
        for(int i=0; i<f.size();i++){
            MultipartFile file = f.get(i);
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(SellerController.uploadDirectory, fileName);
            Files.write(filePath, file.getBytes());


            Banner banner = new Banner();
            banner.setImagePath(fileName);
            banner.setProduct(product);
            banners.add(banner);

        }
        product.setBanner(banners);
        product.setProductImages(productImages);


        product.setSeller(seller);

        Product savedProduct = productRepository.save(product);
        seller.getProducts().add(savedProduct);
        sellerRepository.save(seller);

        return savedProduct;
    }

    @Transactional
    public void removeProductFromSeller(Long sellerId, Long productId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        Product product = productService.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getSeller().getId().equals(sellerId)) {
            throw new RuntimeException("Seller does not own this product");
        }

        seller.getProducts().remove(product);
        productService.deleteById(productId);
        sellerRepository.save(seller);
    }

    public List<Seller> getAllSellersWithProducts() {
        return sellerRepository.findAll();
    }

    @Transactional
    public Product updateProductForSeller(Long sellerId, Long productId, Product updatedProduct) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        Product existingProduct = productService.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Category existingCategory = categoryRepository.findById(updatedProduct.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!existingProduct.getSeller().getId().equals(sellerId)) {
            throw new RuntimeException("Seller does not own this product");
        }

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setColors(updatedProduct.getColors());
        existingProduct.setSizes(updatedProduct.getSizes());
        existingProduct.setCategoryId(updatedProduct.getCategoryId());

        return productService.save(existingProduct);
    }
}
