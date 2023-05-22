package fr.webmaker.hateos.serializer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

import fr.webmaker.hateos.model.HateosLinkModel;
import fr.webmaker.hateos.wrapper.HateosDataWrapper;
import io.smallrye.common.constraint.NotNull;

public class HateosDataWrapperSerializer extends JsonSerializer<HateosDataWrapper> {

	@Override
	public void serialize(@NotNull HateosDataWrapper wrapper, @NotNull JsonGenerator generator,
			@NotNull SerializerProvider serializers) throws IOException {
		Object entity = wrapper.getData();

		generator.writeStartObject();
		for (BeanPropertyDefinition property : getPropertyDefinitions(serializers, entity.getClass())) {
			AnnotatedMember accessor = property.getAccessor();
			if (accessor != null) {
				Object value = accessor.getValue(entity);
				generator.writeFieldName(property.getName());
				if (value == null) {
					generator.writeNull();
				} else {
					serializers.findValueSerializer(value.getClass()).serialize(value, generator, serializers);
				}
			}
		}
		
		
		writeLinks(wrapper.getLinks(), generator);
		generator.writeEndObject();
	}

	private void writeLinks(Map<String, HateosLinkModel> links, @NotNull JsonGenerator generator) throws IOException {
		generator.writeFieldName("_links");
		generator.writeObject(links);
	}

	private List<BeanPropertyDefinition> getPropertyDefinitions(@NotNull SerializerProvider serializers,
			@NotNull Class<?> entityClass) {
		JavaType entityType = serializers.getTypeFactory().constructType(entityClass);

		return new BasicClassIntrospector()
				.forSerialization(serializers.getConfig(), entityType, serializers.getConfig()).findProperties();
	}
}
