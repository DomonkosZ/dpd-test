package hu.domi.dpd.dpdbackend.controller.dto;

import lombok.Data;

/**
 * Data Transfer Object for Phone information.
 */
@Data
public class PhoneDto {
    private Long id;
    private Long userId;
    private String phoneNumber;
    private String phoneType;
}
