package com.yudis.spring.inventory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.Nullable;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message = "*Please provide a product name")
	private String name;
	@Nullable
	private String description;
	@Min(value=1, message="*Price must be higher than 1 or equal to 1")
	private int price;
	@Column(columnDefinition="INT DEFAULT 1")
	private boolean active;
}
