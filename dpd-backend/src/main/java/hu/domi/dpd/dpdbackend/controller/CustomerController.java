package hu.domi.dpd.dpdbackend.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import hu.domi.dpd.dpdbackend.controller.dto.CustomerDto;
import hu.domi.dpd.dpdbackend.controller.dto.ResponseDTO;
import hu.domi.dpd.dpdbackend.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addCustomer(@Valid @RequestBody CustomerDto customerDto, BindingResult bindingResult) {
        ResponseDTO response = new ResponseDTO();
        // Handle validation errors
        if (bindingResult.hasErrors()) {
            response.setResponseObject(customerDto);
            response.setErrorList(bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(response);
        }
        // Save customer
        CustomerDto savedCustomerDto = customerService.addCustomer(customerDto);
        response.setResponseObject(savedCustomerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllCustomer() {
        ResponseDTO response = new ResponseDTO();
        List<CustomerDto> customers = customerService.getAllCustomers();
        response.setResponseObject(customers);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{isGdprUpdate}")
    public ResponseEntity<ResponseDTO> updateCustomerData(@Valid @RequestBody CustomerDto customerDto, BindingResult bindingResult,  @PathVariable("isGdprUpdate") boolean isGdprUpdate) {
        ResponseDTO response = new ResponseDTO();
        // Handle validation errors
        if (bindingResult.hasErrors()) {
            response.setResponseObject(customerDto);
            response.setErrorList(bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(response);
        }
        // Update customer
        CustomerDto savedCustomerDto = customerService.updateCustomer(customerDto, isGdprUpdate);
        response.setResponseObject(savedCustomerDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}