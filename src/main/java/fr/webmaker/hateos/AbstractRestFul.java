package fr.webmaker.hateos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;

import fr.webmaker.common.BaseResource;
import fr.webmaker.hateos.model.HateosLinkModel;
import fr.webmaker.hateos.wrapper.HateosCollectionWrapper;
import fr.webmaker.hateos.wrapper.HateosDataWrapper;

/**
 * Service with Hateos utilities.
 */
public abstract class AbstractRestFul {

	protected static final String SELF_REF = "self";
	protected String SELF_PATH = "/";

	/**
	 * Wrap a collection of objects into a Hal collection wrapper by resolving the
	 * Hal links. The Hal collection wrapper is then serialized by either json or
	 * jackson.
	 *
	 * @param collection  The collection of objects to wrap.
	 * @param entityClass The class of the objects in the collection. If null, it
	 *                    will not resolve the links for these objects.
	 * @return The Hal collection wrapper instance.
	 */
	public HateosCollectionWrapper toHateosCollectionWrapper(Collection<BaseResource> collection,
			Class<?> entityClass) {
		List<HateosDataWrapper> items = new ArrayList<>();
		for (BaseResource entity : collection) {
			items.add(toHalWrapper(entity));
		}

		Map<String, HateosLinkModel> classLinks = Collections.emptyMap();
		if (entityClass != null) {
			// classLinks = getClassLinks(entityClass);
		}

		return new HateosCollectionWrapper(items, classLinks);
	}

	/**
	 * Wrap an entity into a Hal instance by including the entity itself and the Hal
	 * links.
	 *
	 * @param entity The entity to wrap.
	 * @return The Hal entity wrapper.
	 */
	public HateosDataWrapper toHalWrapper(BaseResource entity) {
		return new HateosDataWrapper(entity, getInstanceLinks(entity));
	}

	/**
	 * Get the HREF link with reference `self` from the Hal links of the entity
	 * instance.
	 *
	 * @param entity The entity instance where to get the Hal links.
	 * @return the HREF link with rel `self`.
	 */
	public String getSelfLink(BaseResource entity) {
		HateosLinkModel halLink = getInstanceLinks(entity).get(this.getClass().getAnnotation(Path.class).value());
		if (halLink != null) {
			return halLink.getHref();
		}

		return null;
	}

	/**
	 * Get the Hal links using the entity type class.
	 *
	 * @param entityClass The entity class to get the Hal links.
	 * @return a map with the Hal links which keys are the rel attributes, and the
	 *         values are the href attributes.
	 */
	protected Map<String, HateosLinkModel> getClassLinks(Class<?> entityClass) {
		Map<String, HateosLinkModel> links = new HashMap<>();
		links.put(SELF_REF, new HateosLinkModel(this.getClass().getAnnotation(Path.class).value(), false));
		return links;
	}

	/**
	 * Get the Hal links using the entity instance.
	 *
	 * @param entity the Object instance.
	 * @return a map with the Hal links which keys are the rel attributes, and the
	 *         values are the href attributes.
	 */
	protected Map<String, HateosLinkModel> getInstanceLinks(BaseResource entity) {
		Map<String, HateosLinkModel> links = new HashMap<>();
		var annotation = this.getClass().getAnnotation(Path.class);
		if (annotation == null) {
			return links;
		}

		links.put(SELF_REF, new HateosLinkModel(annotation.value(), false));
		return links;
	}
}
