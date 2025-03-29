package sube.interviews.mareoenvios.util.mapper;

import org.springframework.stereotype.Component;
import sube.interviews.mareoenvios.dto.ShippingItemDTO;
import sube.interviews.mareoenvios.dto.request.ShippingRequestDTO;
import sube.interviews.mareoenvios.dto.response.ShippingDTO;
import sube.interviews.mareoenvios.entity.Shipping;
import sube.interviews.mareoenvios.entity.ShippingItem;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShippingMapper {

    private final CustomerMapper customerMapper;
    private final ProductMapper productMapper;

    public ShippingMapper(CustomerMapper customerMapper, ProductMapper productMapper) {
        this.customerMapper = customerMapper;
        this.productMapper = productMapper;
    }

    public Shipping toEntity(ShippingRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Shipping shipping = new Shipping();
        shipping.setState(dto.getState());
        shipping.setSendDate(dto.getSendDate());
        shipping.setArriveDate(dto.getArriveDate());
        shipping.setPriority(dto.getPriority());

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
        dto.setCustomer(customerMapper.toDTO(shipping.getCustomer()));

        if (shipping.getShippingItems() != null) {
            List<ShippingItemDTO> shippingItems = shipping.getShippingItems().stream()
                    .map(this::toShippingItemDTO)
                    .collect(Collectors.toList());
            dto.setShippingItems(shippingItems);
        }

        return dto;
    }

    private ShippingItemDTO toShippingItemDTO(ShippingItem item) {
        if (item == null) {
            return null;
        }

        ShippingItemDTO dto = new ShippingItemDTO();
        dto.setId(item.getId());
        dto.setProduct(productMapper.toDTO(item.getProduct())); // Mapeo de Producto
        dto.setProductCount(item.getProductCount());

        return dto;
    }

}
