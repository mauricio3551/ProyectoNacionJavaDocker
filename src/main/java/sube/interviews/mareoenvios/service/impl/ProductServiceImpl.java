package sube.interviews.mareoenvios.service.impl;

import org.hibernate.MappingException;
import org.springframework.stereotype.Service;
import sube.interviews.mareoenvios.dto.request.ProductRequestDTO;
import sube.interviews.mareoenvios.dto.response.ProductDTO;
import sube.interviews.mareoenvios.entity.Product;
import sube.interviews.mareoenvios.exception.DatabaseException;
import sube.interviews.mareoenvios.exception.DuplicateResourceException;
import sube.interviews.mareoenvios.repository.ProductRepository;
import sube.interviews.mareoenvios.service.ProductService;
import sube.interviews.mareoenvios.util.mapper.ProductMapper;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO addProduct(ProductRequestDTO productRequestDTO) {
        try {
            if (productRepository.existsByDescriptionAndWeight(productRequestDTO.getDescription(), productRequestDTO.getWeight())) {
                throw new DuplicateResourceException("El producto ya existe: " + productRequestDTO.getDescription());
            }

            Product product = productMapper.toEntity(productRequestDTO);
            return productMapper.toDTO(productRepository.save(product));
        } catch (DatabaseException e) {
            throw new DatabaseException("Error al guardar el cliente en la base de datos", e);
        } catch (MappingException e) {
            throw new MappingException("Error al mappear el nuevo cliente");
        }

    }

    @Override
    public List<ProductDTO> getAllProducts() {
        try {
            return productRepository.findAll()
                    .stream()
                    .map(productMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (DatabaseException e) {
            throw new DatabaseException("Error al acceder a la base de datos al obtener todos los clientes", e);
        } catch (MappingException e) {
            throw new MappingException("Error al mappear los clientes");
        }
    }
}
