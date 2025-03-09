package sube.interviews.mareoenvios.util.mapper;

import org.springframework.stereotype.Component;
import sube.interviews.mareoenvios.dto.request.ProductRequestDTO;
import sube.interviews.mareoenvios.dto.response.ProductDTO;
import sube.interviews.mareoenvios.entity.Product;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setDescription(product.getDescription());
        dto.setWeight(product.getWeight());

        return dto;
    }

    public Product toEntity(ProductRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();
        product.setDescription(dto.getDescription());
        product.setWeight(dto.getWeight());

        return product;
    }
}
