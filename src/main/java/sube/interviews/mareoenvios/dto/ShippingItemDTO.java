package sube.interviews.mareoenvios.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingItemDTO {
    private Integer id;
    private ProductDTO product;
    private Integer productCount;
}