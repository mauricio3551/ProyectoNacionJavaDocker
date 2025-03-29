package sube.interviews.mareoenvios.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingItemRequestDTO {
    private Integer productId;
    private Integer productCount;
}