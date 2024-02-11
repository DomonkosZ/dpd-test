package hu.domi.dpd.dpdbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import hu.domi.dpd.dpdbackend.controller.dto.AddressDto;
import hu.domi.dpd.dpdbackend.entity.Address;

@Mapper(componentModel = "spring")
public abstract class AddressMapper {

    public abstract Address toEntity(AddressDto source);

    @Mapping(target = "userId", source = "user.id")
    public abstract AddressDto toDto(Address source);
}
