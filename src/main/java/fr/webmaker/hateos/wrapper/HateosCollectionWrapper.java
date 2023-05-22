package fr.webmaker.hateos.wrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Link;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.webmaker.hateos.model.HateosLinkModel;
import fr.webmaker.hateos.model.HateosPageModel;
import fr.webmaker.hateos.serializer.HateosCollectionWrapperSerializer;
import io.smallrye.common.constraint.NotNull;

/**
 * The Hal collection wrapper that includes the list of Hal entities
 * {@link HateosDataWrapper}, the collection name and the Hal links.
 *
 * This type is serialized into Json using: - the Jackson serializer:
 * {@link HateosCollectionWrapperSerializer}
 */
@JsonSerialize(using = HateosCollectionWrapperSerializer.class)
public class HateosCollectionWrapper implements HateosLinkWrapper {

	private final List<HateosDataWrapper> collection;
	
	@JsonIgnore
	private final Map<String, HateosLinkModel> links = new HashMap<>();
	
	@JsonInclude(Include.NON_EMPTY)
	private final HateosPageModel pagging;

	public HateosCollectionWrapper(@NotNull List<HateosDataWrapper> collection, Link... links) {
		this(collection, new HashMap<>());
		addLinks(links);
	}

	public HateosCollectionWrapper(@NotNull List<HateosDataWrapper> collection,
			Map<String, HateosLinkModel> links) {
		getLinks().putAll(links);

		this.collection = collection;
		this.pagging = new HateosPageModel();
		this.pagging.setTotalElements(collection.size());
		this.pagging.setSize(10);
		this.pagging.setNumber(1);
	}

	public List<HateosDataWrapper> getCollection() {
		return collection;
	}

	public Map<String, HateosLinkModel> getLinks() {
		return links;
	}

	public HateosPageModel getPagging() {
		return pagging;
	}

}
