package sube.interviews.mareoenvios.util.mapper;

import org.springframework.stereotype.Component;
import sube.interviews.mareoenvios.entity.Customer;
import sube.interviews.mareoenvios.dto.request.CustomerRequestDTO;
import sube.interviews.mareoenvios.dto.response.CustomerDTO;


@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequestDTO customerRequestDTO) {
        if (customerRequestDTO == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setFirstName(customerRequestDTO.getFirstName());
        customer.setLastName(customerRequestDTO.getLastName());
        customer.setAddress(customerRequestDTO.getAddress());
        customer.setCity(customerRequestDTO.getCity());

        return customer;
    }

    public CustomerDTO toDTO(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setCity(customer.getCity());

        return customerDTO;
    }

}
