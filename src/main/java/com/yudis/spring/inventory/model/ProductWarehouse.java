package com.yudis.spring.inventory.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductWarehouse implements Serializable {

	@Id
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	@Id
	@ManyToOne
	@JoinColumn(name="warehouse_id")
	private Warehouse warehouse;
	@Min(value=1, message="*Price must be higher than 1 or equal to 1")
	private int qty;
	@NotNull(message = "*Please provide a transaction date")
	private Date transDate;
}
