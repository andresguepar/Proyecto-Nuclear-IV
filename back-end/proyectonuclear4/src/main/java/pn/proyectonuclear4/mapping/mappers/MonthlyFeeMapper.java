package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.MonthlyFee;
import pn.proyectonuclear4.mapping.dto.MonthlyFeeDto;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class MonthlyFeeMapper {
    public static MonthlyFeeDto mapFrom(MonthlyFee source) {
        return MonthlyFeeDto.builder()
                .idMonthlyFee(source.getIdMonthlyFee())
                .vehicleType(source.getVehicleType())
                .parkingLot(source.getParkingLot())
                .price(source.getPrice())
                .isActive(source.getIsActive())
                .build();
    }

    public static MonthlyFee mapFrom(MonthlyFeeDto source) {
        return MonthlyFee.builder()
                .idMonthlyFee(source.getIdMonthlyFee())
                .vehicleType(source.getVehicleType())
                .parkingLot(source.getParkingLot())
                .price(source.getPrice())
                .isActive(source.getIsActive())
                .build();
    }

    public static List<MonthlyFeeDto> mapFrom(List<MonthlyFee> source) {
        return source.stream().map(MonthlyFeeMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<MonthlyFee> mapToEntities(List<MonthlyFeeDto> source) {
        return source.stream().map(MonthlyFeeMapper::mapFrom).collect(Collectors.toList());
    }
}
