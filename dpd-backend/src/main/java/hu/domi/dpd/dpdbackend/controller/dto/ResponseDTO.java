package hu.domi.dpd.dpdbackend.controller.dto;

import java.util.List;
import org.springframework.validation.ObjectError;
import lombok.Data;

/**
 * ResponseDTO class represents the response object that is returned by an API endpoint.
 * It contains the responseObject and errorList fields.
 */
@Data
public class ResponseDTO {
    private Object responseObject;
    List<ObjectError> errorList;
}
