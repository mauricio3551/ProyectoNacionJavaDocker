package sube.interviews.mareoenvios.util.mapper;

import org.springframework.stereotype.Component;
import sube.interviews.mareoenvios.dto.response.ShippingDTO;
import sube.interviews.mareoenvios.entity.Shipping;

@Component
public class ShippingMapper {

    private final CustomerMapper customerMapper;
    private final ProductMapper productMapper;

    public ShippingMapper(CustomerMapper customerMapper, ProductMapper productMapper) {
        this.customerMapper = customerMapper;
        this.productMapper = productMapper;
    }

    public Shipping toEntity(ShippingDTO dto) {
        if (dto == null) {
            return null;
        }

        Shipping shipping = new Shipping();
        shipping.setState(dto.getState());
        shipping.setSendDate(dto.getSendDate());
        shipping.setArriveDate(dto.getArriveDate());
        shipping.setPriority(dto.getPriority());
        shipping.setCustomer(customerMapper.toEntity(dto.getCustomer()));
        //shipping.setShippingItems(toShippingItemEntityList(dto.getShippingItems(), shipping));

        return shipping;
    }

    public ShippingDTO toDTO(Shipping shipping) {
        if (shipping == null) {
            return null;
        }

        ShippingDTO dto = new ShippingDTO();
        dto.setId(shipping.getId());
        dto.setState(shipping.getState());
        dto.setSendDate(shipping.getSendDate());
        dto.setArriveDate(shipping.getArriveDate());
        dto.setPriority(shipping.getPriority());
        dto.setCustomer(customerMapper.toDTO(shipping.getCustomer())); // Mapeo de Customer
        //dto.setShippingItems(toShippingItemDTOList(shipping.getShippingItems())); // Mapeo de items

        return dto;
    }
}
