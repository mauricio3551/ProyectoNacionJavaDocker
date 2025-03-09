package sube.interviews.mareoenvios.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sube.interviews.mareoenvios.dto.response.ShippingDTO;
import sube.interviews.mareoenvios.service.ShippingService;

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
}
