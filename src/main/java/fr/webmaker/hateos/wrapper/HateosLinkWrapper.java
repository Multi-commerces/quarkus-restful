package fr.webmaker.hateos.wrapper;

import java.util.Map;

import javax.ws.rs.core.Link;

import fr.webmaker.hateos.model.HateosLinkModel;
import io.smallrye.common.constraint.NotNull;

public interface HateosLinkWrapper {


	public Map<String, HateosLinkModel> getLinks();

	/**
	 * This method is used by Rest Data Panache to programmatically add links to the
	 * Hal wrapper.
	 *
	 * @param links The links to add into the Hal wrapper.
	 */
	default void addLinks(@NotNull Link... links) {
		for (Link link : links) {	
			getLinks().put(link.getRel(), new HateosLinkModel(link.getUri().toString(), 
					link.getParams() != null && !link.getParams().isEmpty()));
		}
	}
}
