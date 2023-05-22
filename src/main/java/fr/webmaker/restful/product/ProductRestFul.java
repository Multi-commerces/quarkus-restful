package fr.webmaker.restful.product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;

import fr.webmaker.common.BaseResource;
import fr.webmaker.common.TranslationEmbeded;
import fr.webmaker.hateos.AbstractRestFul;
import fr.webmaker.hateos.wrapper.HateosCollectionWrapper;
import fr.webmaker.restful.data.CategoryDetailData;

@Path("/orders")
public class ProductRestFul extends AbstractRestFul {

	public static final String PATH = "/ordersddd";

	public ProductRestFul() {
		SELF_PATH = PATH;
	}

	@Inject
	Manager manager;

	@GET
	@Transactional
	@Path("/")
	@Produces("application/json")
	public RestResponse<HateosCollectionWrapper> getOrder(@RestPath Long orderId) {

		var test = new Product();

		test.priceHT = 23d;

		test.persist();

		// Bouchon pour test
		Map<String, TranslationEmbeded> translations = new HashMap<>();
		translations.put("fr", new TranslationEmbeded("001", "titre en français", "description en français"));
		translations.put("en", new TranslationEmbeded("001", "titre en anglais", "description en anglais"));

		Collection<BaseResource> products = new ArrayList<>();
		for (var pojo : manager.getProducts()) {

			var data = new ProductDetailData("12");
			data.setCategories(
					List.of(new CategoryDetailData(pojo.id.toString(), translations), new CategoryDetailData("45678")));
			data.setTranslations(translations);
			products.add(data);

		}

		return RestResponse.ok(toHateosCollectionWrapper(products, getClass()));
	}

}
