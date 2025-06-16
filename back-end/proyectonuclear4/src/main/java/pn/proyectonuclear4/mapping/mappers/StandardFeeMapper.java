package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.StandardFee;
import pn.proyectonuclear4.mapping.dto.StandardFeeDto;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class StandardFeeMapper {
    public static StandardFeeDto mapFrom(StandardFee source) {
        return StandardFeeDto.builder()
                .idStandardFee(source.getIdStandardFee())
                .vehicleType(source.getVehicleType())
                .parkingLot(source.getParkingLot())
                .priceForHours(source.getPriceForHours())
                .isActivePriceFortTwelveHours(source.getIsActivePriceFortTwelveHours())
                .priceForTwelveHours(source.getPriceForTwelveHours())
                .isActive(source.getIsActive())
                .build();
    }

    public static StandardFee mapFrom(StandardFeeDto source) {
        return StandardFee.builder()
                .idStandardFee(source.getIdStandardFee())
                .vehicleType(source.getVehicleType())
                .parkingLot(source.getParkingLot())
                .priceForHours(source.getPriceForHours())
                .isActivePriceFortTwelveHours(source.getIsActivePriceFortTwelveHours())
                .priceForTwelveHours(source.getPriceForTwelveHours())
                .isActive(source.getIsActive())
                .build();
    }

    public static List<StandardFeeDto> mapFrom(List<StandardFee> source) {
        return source.stream().map(StandardFeeMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<StandardFee> mapToEntities(List<StandardFeeDto> source) {
        return source.stream().map(StandardFeeMapper::mapFrom).collect(Collectors.toList());
    }
}
