package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.MonthlyFee;
import pn.proyectonuclear4.mapping.dto.MonthlyFeeDto;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class MonthlyFeeMapper {
    public static MonthlyFeeDto mapFrom(MonthlyFee source) {
        if (source == null) {
            return null;
        }
        return new MonthlyFeeDto(
            source.getIdMonthlyFee(),
            source.getVehicleType(),
            source.getParkingLot(),
            source.getPrice(),
            source.getIsActive()
        );
    }

    public static MonthlyFee mapFrom(MonthlyFeeDto source) {
        if (source == null) {
            return null;
        }
        return MonthlyFee.builder()
                .idMonthlyFee(source.idMonthlyFee())
                .vehicleType(source.vehicleType())
                .parkingLot(source.parkingLot())
                .price(source.price())
                .isActive(source.isActive())
                .build();
    }

    public static List<MonthlyFeeDto> mapFrom(List<MonthlyFee> source) {
        if (source == null) {
            return List.of();
        }
        return source.stream().map(MonthlyFeeMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<MonthlyFee> mapToEntities(List<MonthlyFeeDto> source) {
        if (source == null) {
            return List.of();
        }
        return source.stream().map(MonthlyFeeMapper::mapFrom).collect(Collectors.toList());
    }
}
