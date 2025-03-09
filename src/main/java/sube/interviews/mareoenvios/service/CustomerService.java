package sube.interviews.mareoenvios.service;

import sube.interviews.mareoenvios.dto.request.CustomerRequestDTO;
import sube.interviews.mareoenvios.dto.response.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO addCustomer(CustomerRequestDTO customerDTO);
    CustomerDTO getCustomerById(Integer customerId);
    List<CustomerDTO> getAllCustomers();
}
