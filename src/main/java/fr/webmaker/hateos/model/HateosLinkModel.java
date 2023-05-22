package fr.webmaker.hateos.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HateosLinkModel {

	private final boolean templated;
	private final String href;

	public HateosLinkModel(String href, boolean templated) {
		this.href = href;
		this.templated = templated;
	}

	public String getHref() {
		return href;
	}

	@JsonProperty("templated")
	public boolean isTemplated() {
		return templated;
	}
}
