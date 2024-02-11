package hu.domi.dpd.dpdbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import hu.domi.dpd.dpdbackend.controller.dto.PhoneDto;
import hu.domi.dpd.dpdbackend.entity.PhoneNumber;

@Mapper(componentModel = "spring")
public abstract class PhoneMapper {

    public abstract PhoneNumber toEntity(PhoneDto source);

    @Mapping(target = "userId", source = "user.id")
    public abstract PhoneDto toDto(PhoneNumber source);
}
