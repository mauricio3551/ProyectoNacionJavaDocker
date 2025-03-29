package sube.interviews.mareoenvios.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ShippingRequestDTO {
    private String state;
    private Date sendDate;
    private Date arriveDate;
    private Integer priority;
    private Integer customerId;
    private List<ShippingItemRequestDTO> shippingItems;
}
