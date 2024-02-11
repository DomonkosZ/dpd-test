package hu.domi.dpd.dpdbackend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import hu.domi.dpd.dpdbackend.controller.CustomerController;
import hu.domi.dpd.dpdbackend.controller.dto.AddressDto;
import hu.domi.dpd.dpdbackend.controller.dto.CustomerDto;
import hu.domi.dpd.dpdbackend.controller.dto.PhoneDto;
import hu.domi.dpd.dpdbackend.service.CustomerService;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void createNewCustomer() throws Exception {
        CustomerDto customerDto = getCustomerBaseData();

        when(customerService.addCustomer(any(CustomerDto.class))).thenReturn(customerDto);

        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDto)))
            .andExpect(status().isCreated());
    }

    @Test
    public void failIfRequiredDataIsNullOrEmpty() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("");
        customerDto.setEmail("highlander@theone.com");
        customerDto.setMotherName("Nobody Knows");
        customerDto.setTajNumber(null);
        customerDto.setTaxIdentifier("12345678910");
        customerDto.setPlaceOfBirth("Glenfinnan, Scotland");
        customerDto.setDateOfBirth(LocalDate.of(1592, 1, 1));

        when(customerService.addCustomer(any(CustomerDto.class))).thenReturn(customerDto);

        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void ifStatusIsOkThenDataEqualsRequestData() throws Exception {
        CustomerDto customerDto = getCustomerBaseData();

        when(customerService.addCustomer(any(CustomerDto.class))).thenReturn(customerDto);

        String responseString =
            mockMvc.perform(post("/api/customers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(customerDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JsonNode responseJson = new ObjectMapper().readTree(responseString);

        JsonNode responseObject = responseJson.get("responseObject");

        assertEquals(customerDto.getName(), responseObject.get("name").asText());
        assertEquals(customerDto.getEmail(), responseObject.get("email").asText());
        assertEquals(customerDto.getMotherName(), responseObject.get("motherName").asText());
        assertEquals(customerDto.getTajNumber(), responseObject.get("tajNumber").asText());
        assertEquals(customerDto.getPlaceOfBirth(), responseObject.get("placeOfBirth").asText());
        assertEquals(customerDto.getTaxIdentifier(), responseObject.get("taxIdentifier").asText());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Modify to match your date format
        String dateOfBirthInStringFormat = customerDto.getDateOfBirth().format(formatter);
        assertEquals(dateOfBirthInStringFormat, responseObject.get("dateOfBirth").asText());
    }


    @Test
    public void addressSaved() throws Exception {
        CustomerDto customerDto = getCustomerBaseData();
        //New Address
        AddressDto address = new AddressDto();
        address.setCity("Glenfinnan");
        address.setZipcode("PH37");

        customerDto.setAddressList(Collections.singletonList(address));

        when(customerService.addCustomer(any(CustomerDto.class))).thenReturn(customerDto);

        String responseString =
            mockMvc.perform(post("/api/customers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(customerDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JsonNode responseJson = new ObjectMapper().readTree(responseString);

        JsonNode responseObject = responseJson.get("responseObject");
        assertEquals(customerDto.getAddressList().get(0).getCity(), responseObject.get("addressList").get(0).get("city").asText());
        assertEquals(customerDto.getAddressList().get(0).getZipcode(), responseObject.get("addressList").get(0).get("zipcode").asText());
    }

    @Test
    public void phoneNumberSaved() throws Exception {
        CustomerDto customerDto = getCustomerBaseData();
        //New Phone
        PhoneDto phone = new PhoneDto();
        phone.setPhoneNumber("06-90-555-555");
        phone.setPhoneType("mobile");

        customerDto.setPhoneNumbersList(Collections.singletonList(phone));

        when(customerService.addCustomer(any(CustomerDto.class))).thenReturn(customerDto);

        String responseString =
            mockMvc.perform(post("/api/customers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(customerDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JsonNode responseJson = new ObjectMapper().readTree(responseString);

        JsonNode responseObject = responseJson.get("responseObject");
        assertEquals(customerDto.getPhoneNumbersList().get(0).getPhoneNumber(), responseObject.get("phoneNumbersList").get(0).get("phoneNumber").asText());
        assertEquals(customerDto.getPhoneNumbersList().get(0).getPhoneType(), responseObject.get("phoneNumbersList").get(0).get("phoneType").asText());
    }

    /**
     * Retrieves the base data for a customer.
     *
     * @return A CustomerDto object containing the customer's base data.
     */
    private static CustomerDto getCustomerBaseData() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("Duncan MacLeod");
        customerDto.setEmail("highlander@theone.com");
        customerDto.setMotherName("Nobody Knows");
        customerDto.setTajNumber("111222333");
        customerDto.setTaxIdentifier("12345678910");
        customerDto.setPlaceOfBirth("Glenfinnan, Scotland");
        customerDto.setDateOfBirth(LocalDate.of(1592, 1, 1));
        return customerDto;
    }

    /**
     * This method is used to map the object into a json string
     * @return json string
     */
    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}