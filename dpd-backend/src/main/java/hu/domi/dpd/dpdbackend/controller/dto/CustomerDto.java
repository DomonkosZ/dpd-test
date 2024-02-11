package hu.domi.dpd.dpdbackend.controller.dto;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object for Customer information.
 */
@Data
public class CustomerDto {
    private Long id;
    @NotBlank(message = "Customer name not be empty")
    private String name;
    @NotNull(message = "Customer date of birth can not be null")
    private LocalDate dateOfBirth;
    @NotBlank(message = "Customer place of birth can not be emtpy")
    private String placeOfBirth;
    @NotBlank(message = "Customer mother name not be emtpy")
    private String motherName;
    @NotBlank(message = "Customer taj can not be emtpy")
    private String tajNumber;
    @NotBlank(message = "Customer tax identifier can not be emtpy")
    private String taxIdentifier;
    @Email(message = "Invalid email format in customer email")
    private String email;

    @Valid
    private List<AddressDto> addressList;

    @Valid
    private List<PhoneDto> phoneNumbersList;
}
