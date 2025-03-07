package sube.interviews.mareoenvios.service.impl;

import org.hibernate.MappingException;
import org.springframework.stereotype.Service;
import sube.interviews.mareoenvios.dto.request.CustomerRequestDTO;
import sube.interviews.mareoenvios.dto.response.CustomerDTO;
import sube.interviews.mareoenvios.entity.Customer;
import sube.interviews.mareoenvios.exception.DatabaseException;
import sube.interviews.mareoenvios.exception.DuplicateResourceException;
import sube.interviews.mareoenvios.exception.ResourceNotFoundException;
import sube.interviews.mareoenvios.repository.CustomerRepository;
import sube.interviews.mareoenvios.service.CustomerService;
import sube.interviews.mareoenvios.util.mapper.CustomerMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerDTO addCustomer(CustomerRequestDTO customerDTO) {
        try {
            if (customerRepository.existsByFirstNameAndLastNameAndAddress(
                    customerDTO.getFirstName(),
                    customerDTO.getLastName(),
                    customerDTO.getAddress())) {
                throw new DuplicateResourceException("El cliente ya existe: " +
                        customerDTO.getFirstName() + " " +
                        customerDTO.getLastName() + ", " +
                        customerDTO.getAddress());
            }
            Customer customer = customerMapper.toEntity(customerDTO);
            return customerMapper.toDTO(customerRepository.save(customer));
        } catch (DatabaseException e) {
            throw new DatabaseException("Error al guardar el cliente en la base de datos", e);
        } catch (MappingException e) {
            throw new MappingException("Error al mappear el nuevo cliente");
        }
    }

    @Override
    public CustomerDTO getCustomerById(Integer customerId) {
        try {
            return customerRepository.findById(customerId)
                    .map(customerMapper::toDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + customerId));
        } catch (DatabaseException e) {
            throw new DatabaseException("Error al buscar el cliente con ID: " + customerId, e);
        } catch (MappingException e) {
            throw new MappingException("Error al mappear el cliente con ID: " + customerId);
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        try {
            return customerRepository.findAll()
                    .stream()
                    .map(customerMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (DatabaseException e) {
            throw new DatabaseException("Error al acceder a la base de datos al obtener todos los clientes", e);
        } catch (MappingException e) {
            throw new MappingException("Error al mappear los clientes");
        }
    }
}
