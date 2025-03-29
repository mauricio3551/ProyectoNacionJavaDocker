package sube.interviews.mareoenvios.service.impl;

import org.springframework.stereotype.Service;
import sube.interviews.mareoenvios.dto.request.ShippingItemRequestDTO;
import sube.interviews.mareoenvios.dto.request.ShippingRequestDTO;
import sube.interviews.mareoenvios.dto.response.ShippingDTO;
import sube.interviews.mareoenvios.entity.Customer;
import sube.interviews.mareoenvios.entity.Shipping;
import sube.interviews.mareoenvios.exception.DatabaseException;
import sube.interviews.mareoenvios.exception.InvalidDataException;
import sube.interviews.mareoenvios.exception.InvalidTransitionException;
import sube.interviews.mareoenvios.exception.ResourceNotFoundException;
import sube.interviews.mareoenvios.repository.CustomerRepository;
import sube.interviews.mareoenvios.repository.ProductRepository;
import sube.interviews.mareoenvios.repository.ShippingItemRepository;
import sube.interviews.mareoenvios.repository.ShippingRepository;
import sube.interviews.mareoenvios.service.ShippingService;
import sube.interviews.mareoenvios.util.mapper.ShippingMapper;
import sube.interviews.mareoenvios.util.ShippingStateUtils;
import sube.interviews.mareoenvios.entity.ShippingItem;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShippingServiceImpl implements ShippingService {

    private final ShippingRepository shippingRepository;
    private final ShippingItemRepository shippingItemRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final ShippingMapper shippingMapper;

    public ShippingServiceImpl(ShippingRepository shippingRepository, ShippingMapper shippingMapper,
                               ProductRepository productRepository, ShippingItemRepository shippingItemRepository,
                               CustomerRepository customerRepository) {
        this.shippingRepository = shippingRepository;
        this.shippingMapper = shippingMapper;
        this.productRepository = productRepository;
        this.shippingItemRepository = shippingItemRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public ShippingDTO getShippingById(Integer shippingId) {
        return shippingRepository.findById(shippingId)
                .map(shippingMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Envío no encontrado con ID: " + shippingId));
    }

    @Override
    public List<ShippingDTO> getShippingsByDateRange(String sendDateFrom, String sendDateTo) {
        return shippingRepository.findBySendDateBetween(sendDateFrom, sendDateTo)
                .stream()
                .map(shippingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShippingDTO> getShippingsByState(String state) {
        return shippingRepository.findByState(state)
                .stream()
                .map(shippingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ShippingDTO transitionToSendToMail(Integer shippingId) {
        return updateShippingState(shippingId, "Entregado al correo");
    }

    @Override
    public ShippingDTO transitionToInTravel(Integer shippingId) {
        return updateShippingState(shippingId, "En camino");
    }

    @Override
    public ShippingDTO transitionToDelivered(Integer shippingId) {
        return updateShippingState(shippingId, "Entregado");
    }

    @Override
    public ShippingDTO transitionToCancelled(Integer shippingId) {
        return updateShippingState(shippingId, "Cancelado");
    }

    @Override
    public ShippingDTO createShipping(ShippingRequestDTO shippingDTO) {
        try {
            Shipping shipping = shippingMapper.toEntity(shippingDTO);

            Customer customer = customerRepository.findById(shippingDTO.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + shippingDTO.getCustomerId()));

            if (shippingDTO.getShippingItems() == null || shippingDTO.getShippingItems().isEmpty()) {
                throw new InvalidDataException("El envío debe contener al menos un producto.");
            }

            shipping.setCustomer(customer);

            Shipping finalShipping = shipping;
            List<ShippingItem> shippingItems = shippingDTO.getShippingItems().stream()
                    .map(itemDTO -> {
                        ShippingItem shippingItem = new ShippingItem();
                        shippingItem.setProduct(productRepository.findById(itemDTO.getProductId()).orElseThrow(()->
                                new ResourceNotFoundException("Producto no encontrado con ID: " + itemDTO.getProductId())));
                        shippingItem.setProductCount(itemDTO.getProductCount());
                        shippingItem.setShipping(finalShipping);
                        return shippingItem;
                    })
                    .collect(Collectors.toList());

            shipping = shippingRepository.save(shipping);

            shipping.setShippingItems(shippingItems);
            shippingItemRepository.saveAll(shippingItems);

            return shippingMapper.toDTO(shipping);
        } catch (Exception e) {
            throw new DatabaseException("Error al crear el envío en la base de datos", e);
        }
    }

    private ShippingDTO updateShippingState(Integer shippingId, String newState) {
        Shipping shipping = shippingRepository.findById(shippingId)
                .orElseThrow(() -> new ResourceNotFoundException("Envío no encontrado con ID: " + shippingId));

        String currentState = shipping.getState();

        if (!ShippingStateUtils.isValidTransition(currentState, newState)) {
            throw new InvalidTransitionException("No se puede cambiar el estado de " + currentState + " a " + newState);
        }

        shipping.setState(newState);
        return shippingMapper.toDTO(shippingRepository.save(shipping));
    }

    private ShippingDTO updateShippingState(Integer shippingId, List<String> validStates, String newState) {
        Shipping shipping = shippingRepository.findById(shippingId)
                .orElseThrow(() -> new ResourceNotFoundException("Envío no encontrado con ID: " + shippingId));

        if (!validStates.contains(shipping.getState())) {
            throw new InvalidTransitionException("No se puede cambiar el estado de " + shipping.getState() + " a " + newState);
        }

        shipping.setState(newState);
        return shippingMapper.toDTO(shippingRepository.save(shipping));
    }
}