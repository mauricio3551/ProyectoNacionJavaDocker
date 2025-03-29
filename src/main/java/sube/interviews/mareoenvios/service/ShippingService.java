package sube.interviews.mareoenvios.service;

import sube.interviews.mareoenvios.dto.request.ShippingRequestDTO;
import sube.interviews.mareoenvios.dto.response.ShippingDTO;
import java.util.List;

public interface ShippingService {
    ShippingDTO getShippingById(Integer shippingId);
    List<ShippingDTO> getShippingsByDateRange(String sendDateFrom, String sendDateTo);
    List<ShippingDTO> getShippingsByState(String state);
    ShippingDTO transitionToSendToMail(Integer shippingId);
    ShippingDTO transitionToInTravel(Integer shippingId);
    ShippingDTO transitionToDelivered(Integer shippingId);
    ShippingDTO transitionToCancelled(Integer shippingId);
    ShippingDTO createShipping(ShippingRequestDTO shippingRequest);
}
