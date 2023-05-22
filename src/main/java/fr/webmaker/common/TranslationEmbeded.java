package fr.webmaker.common;

public class TranslationEmbeded {
	
	private String key;

	private String title;

	private String description;

	public TranslationEmbeded() {
		super();
	}

	public TranslationEmbeded(String key, String title, String description) {
		super();
		this.key = key;
		this.title = title;
		this.description = description;
	}

	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
