package pn.proyectonuclear4.mapping.mappers;

import lombok.Builder;
import pn.proyectonuclear4.entity.Fee;
import pn.proyectonuclear4.mapping.dto.FeeDto;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class FeeMapper {
    public static FeeDto mapFrom(Fee source) {
        return FeeDto.builder()
                .idFee(source.getIdFee())
                .name(source.getName())
                .description(source.getDescription())
                .total(source.getTotal())
                .standardFee(source.getStandardFee())
                .monthlyFee(source.getMonthlyFee())
                .addOnService(source.getAddOnService())
                .build();
    }

    public static Fee mapFrom(FeeDto source) {
        return Fee.builder()
                .idFee(source.getIdFee())
                .name(source.getName())
                .description(source.getDescription())
                .total(source.getTotal())
                .standardFee(source.getStandardFee())
                .monthlyFee(source.getMonthlyFee())
                .addOnService(source.getAddOnService())
                .build();
    }

    public static List<FeeDto> mapFrom(List<Fee> source) {
        return source.stream().map(FeeMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<Fee> mapToEntities(List<FeeDto> source) {
        return source.stream().map(FeeMapper::mapFrom).collect(Collectors.toList());
    }
}
