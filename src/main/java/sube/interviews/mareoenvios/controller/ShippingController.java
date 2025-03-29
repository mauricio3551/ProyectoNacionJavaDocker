package sube.interviews.mareoenvios.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sube.interviews.mareoenvios.dto.request.ShippingRequestDTO;
import sube.interviews.mareoenvios.dto.response.ShippingDTO;
import sube.interviews.mareoenvios.service.ShippingService;

import java.util.List;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @GetMapping("/info/{shippingId}")
    public ShippingDTO getShippingById(@PathVariable Integer shippingId) {
        return shippingService.getShippingById(shippingId);
    }

    @GetMapping("/info/{sendDateFrom}/{sendDateTo}")
    public List<ShippingDTO> getShippingsByDateRange(@PathVariable String sendDateFrom, @PathVariable String sendDateTo) {
        return shippingService.getShippingsByDateRange(sendDateFrom, sendDateTo);
    }

    @GetMapping("/info/{state}")
    public List<ShippingDTO> getShippingsByState(@PathVariable String state) {
        return shippingService.getShippingsByState(state);
    }

    @PutMapping("/transition/sendToMail/{shippingId}")
    public ShippingDTO transitionToSendToMail(@PathVariable Integer shippingId) {
        return shippingService.transitionToSendToMail(shippingId);
    }

    @PutMapping("/transition/inTravel/{shippingId}")
    public ShippingDTO transitionToInTravel(@PathVariable Integer shippingId) {
        return shippingService.transitionToInTravel(shippingId);
    }

    @PutMapping("/transition/delivered/{shippingId}")
    public ShippingDTO transitionToDelivered(@PathVariable Integer shippingId) {
        return shippingService.transitionToDelivered(shippingId);
    }

    @PutMapping("/transition/cancelled/{shippingId}")
    public ShippingDTO transitionToCancelled(@PathVariable Integer shippingId) {
        return shippingService.transitionToCancelled(shippingId);
    }

    @PostMapping("/create")
    public ShippingDTO createShipping(@RequestBody ShippingRequestDTO shippingRequest) {
        return shippingService.createShipping(shippingRequest);
    }
}
