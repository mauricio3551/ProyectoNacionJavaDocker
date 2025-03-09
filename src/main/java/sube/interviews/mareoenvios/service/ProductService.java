package sube.interviews.mareoenvios.service;

import sube.interviews.mareoenvios.dto.request.ProductRequestDTO;
import sube.interviews.mareoenvios.dto.response.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO addProduct(ProductRequestDTO productRequestDTO);
    List<ProductDTO> getAllProducts();
}
