package net.branium.dtos.lecture;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.branium.domains.LectureType;

import java.io.IOException;

public class LectureTypeSerializer extends JsonSerializer<LectureType> {
    @Override
    public void serialize(LectureType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.name().toLowerCase());
    }
}
