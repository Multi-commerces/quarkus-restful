package fr.webmaker.restful.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.webmaker.common.DetailDTO;
import fr.webmaker.common.TranslationEmbeded;
import fr.webmaker.hateos.annotation.HateosEmbeded;
import fr.webmaker.hateos.annotation.HateosRelation;
import fr.webmaker.hateos.annotation.HateosType;
import fr.webmaker.restful.data.CategoryDetailData;

@HateosType("product")
public class ProductDetailData extends ProductData implements DetailDTO {

	@HateosEmbeded
	private Map<String, TranslationEmbeded> translations;

	@HateosRelation
	private List<CategoryDetailData> categories;

	public ProductDetailData(String id) {
		super(id);
		this.translations = new HashMap<>();
	}

	public List<CategoryDetailData> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDetailData> categories) {
		this.categories = categories;
	}

	public Map<String, TranslationEmbeded> getTranslations() {
		return translations;
	}

	public void setTranslations(Map<String, TranslationEmbeded> translations) {
		this.translations = translations;
	}

}
