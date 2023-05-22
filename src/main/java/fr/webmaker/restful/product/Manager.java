package fr.webmaker.restful.product;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public final class Manager {
	
	
	@Transactional
	void save()
	{
		var test = new Product();
		
		test.priceHT = 23d;
		
		test.persist();
	}
	
	List<Product> getProducts()
	{
		return Product.<Product>findAll().list();
	}
	
	
}