package fr.webmaker.restful.product;

import fr.webmaker.common.BaseResource;

public class ProductData extends BaseResource {

	private double price;

	public ProductData(String id) {
		super(id);
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	
}
