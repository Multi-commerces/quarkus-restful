package fr.webmaker.hateos.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Informations de pagination.
 * <p>
 * Il est possible que la liste remontée par le serveur comporte un très grand
 * nombre d’éléments. Afin d’éviter par exemple une surcharge, la pagination
 * permet de réduire le nombre d’éléments remontés par l’API.
 * </p>
 * 
 * "total_items": 100,  "page": 1, "page_size": 10,
 * @author Julien ILARI
 *
 */
public class HateosPageModel {

	public static final String JSON_NODE_PAGE_NUMBER = "page";
	public static final String JSON_NODE_PAGE_SIZE = "page_size";
	public static final String JSON_NODE_TOTAL_ITEMS = "total_items";

	/**
	 * Page en-cours de lecture
	 */
	@JsonProperty(JSON_NODE_PAGE_NUMBER)
	private long number;
	/**
	 * Nombre items total
	 */
	@JsonProperty(JSON_NODE_TOTAL_ITEMS)
	private long totalElements;

	/**
	 * Nombre de page total
	 */
	@JsonProperty(JSON_NODE_PAGE_SIZE)
	private long size;

	public HateosPageModel() {
		super();
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
	

}
