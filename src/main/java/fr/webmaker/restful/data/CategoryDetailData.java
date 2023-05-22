package fr.webmaker.restful.data;

import java.util.HashMap;
import java.util.Map;

import fr.webmaker.common.DetailDTO;
import fr.webmaker.common.TranslationEmbeded;
import fr.webmaker.hateos.annotation.HateosEmbeded;

public class CategoryDetailData extends CategoryData implements DetailDTO {

	public CategoryDetailData(String id) {
		super(id);
	}

	public CategoryDetailData(String id, Map<String, TranslationEmbeded> translations) {
		super(id);
		this.translations = translations;
	}

	@HateosEmbeded
	private Map<String, TranslationEmbeded> translations = new HashMap<>();


	public Map<String, TranslationEmbeded> getTranslations() {
		return translations;
	}

	public void setTranslations(Map<String, TranslationEmbeded> translations) {
		this.translations = translations;
	}

	
}
