package com.yudis.spring.inventory.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
	@OneToMany(
			mappedBy="warehouse",
			fetch=FetchType.LAZY,
			cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@ToString.Exclude
	private List<ProductWarehouse> productWarehouse;
	
}
