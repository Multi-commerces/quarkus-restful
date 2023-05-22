package fr.webmaker.hateos.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import fr.webmaker.hateos.model.HateosLinkModel;
import io.smallrye.common.constraint.NotNull;

public class HateosLinkSerializer extends JsonSerializer<HateosLinkModel> {

	@Override
	public void serialize(@NotNull HateosLinkModel value, @NotNull JsonGenerator generator,
			@NotNull SerializerProvider serializers) throws IOException {
		generator.writeStartObject();
		generator.writeObjectField("href", value.getHref());
		generator.writeEndObject();
	}
}
