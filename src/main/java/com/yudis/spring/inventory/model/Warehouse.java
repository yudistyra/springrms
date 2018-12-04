package com.yudis.spring.inventory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Warehouse {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty(message = "*Please provide a warehouse name")
	private String name;
	@Column(columnDefinition="INT DEFAULT 0")
	private int currentCapacity;
	@Column(columnDefinition="INT DEFAULT 0")
	private int maxCapacity;
	@Column(columnDefinition="INT DEFAULT 1")
	private boolean active;
	
}
