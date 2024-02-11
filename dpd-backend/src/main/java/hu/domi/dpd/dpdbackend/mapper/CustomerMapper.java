package hu.domi.dpd.dpdbackend.mapper;

import org.mapstruct.Mapper;
import hu.domi.dpd.dpdbackend.controller.dto.CustomerDto;
import hu.domi.dpd.dpdbackend.entity.CustomerData;

@Mapper(componentModel = "spring")
public abstract class CustomerMapper {

    public abstract CustomerData toEntity(CustomerDto source);
    public abstract CustomerDto toDto(CustomerData source);

}
