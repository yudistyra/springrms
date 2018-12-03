package com.yudis.spring.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yudis.spring.inventory.model.Product;
import com.yudis.spring.inventory.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> findAllProduct() {
		return productRepository.findAll();
	}
	
	public Product findById(Long id) {
		return productRepository.findById(id).orElse(null);
	}
	
	public String save(Product product) {
		if(product.getId() == null)
			product.setActive(true);
		
		Product res = productRepository.save(product);
		
		if(res != null)
			return "success";
		
		return "failed";
	}
	
	public String delete(Long id) {
		Product theProduct = findById(id);
		
		if(theProduct != null) {
			productRepository.delete(theProduct);
			return "success";
		}
			
		return "failed";
	}
}
