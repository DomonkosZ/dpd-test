package hu.domi.dpd.dpdbackend.controller.dto;

import lombok.Data;

/**
 * Data Transfer Object for Address information.
 */
@Data
public class AddressDto {

    private Long id;
    private Long userId;
    private String zipcode;
    private String city;
    private String street;
    private String houseNumber;
    private String additionalInfo;
}
