package fr.webmaker.hateos.serializer;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import fr.webmaker.common.BaseResource;
import fr.webmaker.hateos.annotation.HateosEmbeded;
import fr.webmaker.hateos.annotation.HateosId;
import fr.webmaker.hateos.annotation.HateosRelation;
import fr.webmaker.hateos.annotation.HateosType;
import fr.webmaker.hateos.model.HateosLinkModel;
import fr.webmaker.hateos.wrapper.HateosCollectionWrapper;
import fr.webmaker.hateos.wrapper.HateosDataWrapper;
import io.smallrye.common.constraint.NotNull;

public class HateosCollectionWrapperSerializer extends JsonSerializer<HateosCollectionWrapper> {

	@Override
	public void serialize(@NotNull HateosCollectionWrapper wrapper, @NotNull JsonGenerator generator,
			@NotNull SerializerProvider serializers) throws IOException {
		generator.writeStartObject();
		try {
			writeData(wrapper, generator, serializers);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		// writeLinks(wrapper.getLinks(), generator);
		generator.writeEndObject();
	}

	private void writeData(@NotNull HateosCollectionWrapper wrapper, @NotNull JsonGenerator generator,
			@NotNull SerializerProvider serializers) throws Exception {

		// Pagination
		if (wrapper.getPagging() != null) {
			generator.writeFieldName("_page");
			generator.writeObject(wrapper.getPagging());
		}

		// @DATA (StartArray)
		final Map<String, Set<Object>> classRelations = new HashMap<>();
		generator.writeFieldName("_data");
		generator.writeStartArray();
		for (HateosDataWrapper item : wrapper.getCollection()) {
			final Map<String, Set<Object>> enityRelations = new HashMap<>();
			
			generator.writeStartObject();

			// @DATA - ID
			var fieldId = searchFieldByAnnotation(item.getData().getClass(), HateosId.class);
			generator.writeFieldName("id");
			generator.writeObject(fieldId.get(item.getData()));

			// @DATA - TYPE
			generator.writeFieldName("type");
			generator.writeObject(item.getData().getClass().getAnnotation(HateosType.class).value());

			// @DATA - ATTRIBUTES
			generator.writeFieldName("_attributes");
			generator.writeStartObject();

			Map<String, Object> embededEntity = new HashMap<>();
			for (Field field : getDeclaredFields(item.getData().getClass())) {
				try {
					field.setAccessible(true);
					Object value = field.get(item.getData());
					if (value == null || field.isAnnotationPresent(HateosId.class)
							|| field.isAnnotationPresent(HateosType.class)) {
						continue;
					}

					// Sérialiser les propriétés annotées @HateosRelation
					if (field.isAnnotationPresent(HateosEmbeded.class)) {
						embededEntity.put(field.getName(), field.get(item.getData()));
					} else if (field.isAnnotationPresent(HateosRelation.class)) {
						classRelations.compute(field.getName(), (s, o) -> o == null ? new HashSet<Object>() : o)
								.addAll((List<?>) field.get(item.getData()));

						enityRelations.compute(field.getName(), (s, o) -> o == null ? new HashSet<Object>() : o)
								.addAll((List<?>) field.get(item.getData()));
					} else {
						generator.writeObjectField(field.getName(), value);
					}
				} catch (IllegalAccessException e) {
					// Ignore
				}
			}
			generator.writeEndObject();

			// @RELATION
			writeRelations(enityRelations, generator);

			// @EMBEDED
			generator.writeFieldName("_embeded");
			generator.writeObject(embededEntity);

			// @LINKS
			writeLinks(item.getLinks(), generator);

			generator.writeEndObject();
		}
		// @DATA (EndArray)
		generator.writeEndArray();

		// @INCLUDED (les relations communes à la ressource principale)
		// Exemple : une même catégorie peut-être dans plusieurs produits
		generator.writeFieldName("_included");
		generator.writeStartArray(); // BEGIN
		
		
		for (var entry : classRelations.entrySet()) {
			generator.writeStartObject();
			
			generator.writeFieldName(entry.getKey());
			generator.writeObject(entry.getValue());
			
			generator.writeEndObject();
		}
		generator.writeEndArray();
		
		
		
	}

	private <T extends Annotation> Field searchFieldByAnnotation(Class<?> clazz, Class<T> annotationClass) {
		if (clazz == null) {
			return null;
		}
		for (Field field : clazz.getDeclaredFields()) {
			Annotation annotation = field.getAnnotation(annotationClass);
			if (annotation != null) {
				field.setAccessible(true);
				return field;
			}

		}

		if (clazz.getSuperclass() != null) {
			return searchFieldByAnnotation(clazz.getSuperclass(), annotationClass);
		}

		return null;
	}

	private <T extends Annotation> List<Field> getDeclaredFields(Class<?> clazz) {
		if (clazz == null) {
			return Collections.emptyList();
		}

		final List<Field> collection = new ArrayList<>();
		if (clazz.getDeclaredFields() != null) {
			for (Field field : clazz.getDeclaredFields()) {
				collection.add(field);
			}
		}

		if (clazz.getSuperclass() != null) {
			var values = getDeclaredFields(clazz.getSuperclass());
			for (Field field : values) {
				collection.add(field);
			}
		}

		return collection;
	}

	@FunctionalInterface
	public interface CheckedFunction<T, R> {
		R apply(T t) throws Exception;
	}

	public static <T, R> Function<T, R> sefl(CheckedFunction<T, R> checkedFunction) {
		return t -> {
			try {
				return checkedFunction.apply(t);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	private void writeRelations(final Map<String, Set<Object>> embededClass, final JsonGenerator generator)
			throws IOException {

		if (embededClass == null || embededClass.isEmpty()) {
			return;
		}

		try {
			var field = List.of(BaseResource.class.getDeclaredFields()).stream()
					.filter(f -> f.getDeclaredAnnotation(HateosId.class) != null)
					.findAny();
			if (field.isEmpty()) {
				return;
			}
			field.get().setAccessible(true);

			generator.writeFieldName("_relations");
			generator.writeStartArray(); // BEGIN

			for (var entry : embededClass.entrySet()) {

				generator.writeStartObject();

				Collection<String> relations = entry.getValue().stream()
						.map(sefl(item -> (String) field.get().get(item)))
						.collect(Collectors.toUnmodifiableList());

				if (relations != null && !relations.isEmpty()) {
					generator.writeFieldName(entry.getKey());
					generator.writeStartObject();
					generator.writeFieldName("size");
					generator.writeObject(relations.size());
					generator.writeFieldName("identifiers");
					generator.writeObject(relations);
					generator.writeEndObject();
				}

				generator.writeEndObject();

			}
			generator.writeEndArray(); // END
		} catch (Exception e) {
			throw new RuntimeException("(Exception) => call writeRelations", e);
		}
	}

	private void writeLinks(@NotNull Map<String, HateosLinkModel> links, @NotNull JsonGenerator generator)
			throws IOException {
		generator.writeFieldName("_links");
		generator.writeObject(links);
	}
}
