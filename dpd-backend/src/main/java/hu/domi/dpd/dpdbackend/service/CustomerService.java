package hu.domi.dpd.dpdbackend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.domi.dpd.dpdbackend.controller.dto.AddressDto;
import hu.domi.dpd.dpdbackend.controller.dto.CustomerDto;
import hu.domi.dpd.dpdbackend.controller.dto.PhoneDto;
import hu.domi.dpd.dpdbackend.entity.Address;
import hu.domi.dpd.dpdbackend.entity.CustomerData;
import hu.domi.dpd.dpdbackend.entity.PhoneNumber;
import hu.domi.dpd.dpdbackend.mapper.AddressMapper;
import hu.domi.dpd.dpdbackend.mapper.CustomerMapper;
import hu.domi.dpd.dpdbackend.mapper.PhoneMapper;
import hu.domi.dpd.dpdbackend.repository.AddressRepository;
import hu.domi.dpd.dpdbackend.repository.CustomerRepository;
import hu.domi.dpd.dpdbackend.repository.PhoneRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final PhoneRepository phoneRepository;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;
    private final PhoneMapper phoneMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, AddressRepository addressRepository, PhoneRepository phoneRepository,
        CustomerMapper customerMapper, AddressMapper addressMapper, PhoneMapper phoneMapper) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.phoneRepository = phoneRepository;
        this.customerMapper = customerMapper;
        this.addressMapper = addressMapper;
        this.phoneMapper = phoneMapper;
    }

    /**
     * Adds a new customer to the customer repository.
     *
     * @param customerDto the customer data transfer object containing the customer information
     * @return the customer data transfer object with the added customer information, including the saved addresses and phone numbers
     */
    @Transactional
    public CustomerDto addCustomer(CustomerDto customerDto) {
        CustomerData newCustomer = customerRepository.save(customerMapper.toEntity(customerDto));
        // handle addresses
        List<AddressDto> savedAddresses = new ArrayList<>();
        for (AddressDto addressDto : customerDto.getAddressList()) {
            Address newAddress = addressMapper.toEntity(addressDto);
            newAddress.setUser(newCustomer);
            savedAddresses.add(addressMapper.toDto(addressRepository.save(newAddress)));
        }
        // handle phoneNumbers
        List<PhoneDto> savedPhoneNumbers = new ArrayList<>();
        for (PhoneDto phoneDto : customerDto.getPhoneNumbersList()) {
            PhoneNumber newPhoneNumber = phoneMapper.toEntity(phoneDto);
            newPhoneNumber.setUser(newCustomer);
            savedPhoneNumbers.add(phoneMapper.toDto(phoneRepository.save(newPhoneNumber)));
        }

        // Add saved addresses and phoneNumbers to response
        CustomerDto customerResponse = customerMapper.toDto(newCustomer);
        customerResponse.setAddressList(savedAddresses);
        customerResponse.setPhoneNumbersList(savedPhoneNumbers);
        return customerResponse;
    }

    /**
     * Retrieves all customers from the customer repository.
     *
     * @return a list of CustomerDto objects representing the customers
     */
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
            .map(customer -> {
                CustomerDto customerDto = customerMapper.toDto(customer);
                List<AddressDto> addresses = addressRepository.findByUser(customer).stream()
                    .map(addressMapper::toDto).collect(Collectors.toList());
                List<PhoneDto> phoneNumbers = phoneRepository.findByUser(customer).stream()
                    .map(phoneMapper::toDto).collect(Collectors.toList());
                customerDto.setAddressList(addresses);
                customerDto.setPhoneNumbersList(phoneNumbers);
                return customerDto;
            }).collect(Collectors.toList());
    }

    /**
     * Updates a customer in the customer repository.
     *
     * @param customerDto    the customer data transfer object containing the updated customer information
     * @param isGdprUpdate   a flag indicating if this is a GDPR update
     * @return the customer data transfer object with the updated customer information
     * @throws EntityNotFoundException    if the customer is not found in the repository
     */
    @Transactional
    public CustomerDto updateCustomer(CustomerDto customerDto, boolean isGdprUpdate) {
        final String GDPR_TEXT = "GDPR Handled";
        CustomerData existingCustomer = customerRepository.findById(customerDto.getId()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        existingCustomer.setName(customerDto.getName());
        existingCustomer.setPlaceOfBirth(isGdprUpdate ? GDPR_TEXT : customerDto.getPlaceOfBirth());
        existingCustomer.setDateOfBirth(isGdprUpdate ? LocalDate.of(1592, 01, 01) : customerDto.getDateOfBirth());
        existingCustomer.setMotherName(isGdprUpdate ? GDPR_TEXT : customerDto.getMotherName());
        existingCustomer.setTajNumber(isGdprUpdate ? GDPR_TEXT : customerDto.getTajNumber());
        existingCustomer.setTaxIdentifier(isGdprUpdate ? GDPR_TEXT : customerDto.getTaxIdentifier());
        existingCustomer.setEmail(isGdprUpdate ? GDPR_TEXT : customerDto.getEmail());

        // GPRD delete all addresses and phone numbers.
        if(isGdprUpdate) {
            phoneRepository.deleteAllByUser(existingCustomer);
            addressRepository.deleteAllByUser(existingCustomer);
        }

        // It is not currently possible to change the address and phone number, so start saving.
        CustomerData updatedCustomer = customerRepository.save(existingCustomer);
        return customerMapper.toDto(updatedCustomer);
    }
}
