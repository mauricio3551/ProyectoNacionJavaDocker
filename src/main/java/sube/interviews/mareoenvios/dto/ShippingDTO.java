package sube.interviews.mareoenvios.dto;

import lombok.Getter;
import lombok.Setter;
import sube.interviews.mareoenvios.dto.response.CustomerDTO;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ShippingDTO {
    private Integer id;
    private String state;
    private Date sendDate;
    private Date arriveDate;
    private Integer priority;
    private CustomerDTO customer;
    private List<ShippingItemDTO> shippingItems;
}