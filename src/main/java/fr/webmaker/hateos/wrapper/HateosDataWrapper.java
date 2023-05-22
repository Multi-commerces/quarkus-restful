package fr.webmaker.hateos.wrapper;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Link;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.webmaker.common.BaseResource;
import fr.webmaker.hateos.model.HateosLinkModel;
import fr.webmaker.hateos.serializer.HateosDataWrapperSerializer;
import io.smallrye.common.constraint.NotNull;

/**
 * The Hal entity wrapper that includes the entity and the Hal links.
 *
 * This type is serialized into Json using: - the JSON-B serializer:
 * {@link HateosDataWrapperSerializer} - the Jackson serializer:
 * {@link HateosDataWrapperSerializer}
 */
public class HateosDataWrapper implements HateosLinkWrapper {

	private final BaseResource data;

	@JsonIgnore
	@JsonInclude(Include.NON_EMPTY)
	public Map<String, HateosLinkModel> links = new HashMap<>();

	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	private Map<String, Object> embeded = new HashMap<>();

	public HateosDataWrapper(@NotNull BaseResource entity, @NotNull Link... links) {
		this(entity, new HashMap<>());

		addLinks(links);
	}

	public HateosDataWrapper(BaseResource data, @NotNull Map<String, HateosLinkModel> links) {
		this.getLinks().putAll(links);
		this.data = data;
	}

	
	public BaseResource getData() {
		return data;
	}

	@Override
	public Map<String, HateosLinkModel> getLinks() {
		return links;
	}

	public Map<String, Object> getEmbeded() {
		return embeded;
	}

}
