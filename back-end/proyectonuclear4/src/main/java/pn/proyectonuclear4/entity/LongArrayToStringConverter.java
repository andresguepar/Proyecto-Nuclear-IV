package pn.proyectonuclear4.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Converter
public class LongArrayToStringConverter implements AttributeConverter<Long[], String> {
    @Override
    public String convertToDatabaseColumn(Long[] attribute) {
        if (attribute == null) return null;
        // Convert to PostgreSQL array string format: {1,2,3}
        return "{" + Arrays.stream(attribute).map(String::valueOf).collect(Collectors.joining(",")) + "}";
    }

    @Override
    public Long[] convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.length() < 2) return new Long[0];
        // Remove curly braces and split
        String content = dbData.substring(1, dbData.length() - 1);
        if (content.isEmpty()) return new Long[0];
        return Arrays.stream(content.split(","))
                .map(Long::valueOf)
                .toArray(Long[]::new);
    }
}
