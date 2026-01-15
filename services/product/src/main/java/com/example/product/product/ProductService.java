package com.example.product.product;

import com.example.product.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(
            ProductRequest request
    ) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
    }


    public ProductResponse findById(Integer id) {
        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Product not found with ID: " + id)
                );

        return mapper.toProductResponse(product);
    }


    public List<ProductResponse> findAll() {
        List<ProductResponse> responses = new ArrayList<>();
        for (Product product : repository.findAll()) {
            responses.add(mapper.toProductResponse(product));
        }
        return responses;
    }

    @Transactional(rollbackFor = ProductPurchaseException.class)
    public List<ProductPurchaseResponse> purchaseProducts(
            List<ProductPurchaseRequest> request
    ) {
        // collect productIds
        List<Integer> productIds = new ArrayList<>();
        for (ProductPurchaseRequest r : request) {
            productIds.add(r.productId());
        }

        // fetch products ordered by id
        List<Product> storedProducts =
                repository.findAllByIdInOrderById(productIds);

        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }

        // sort request by productId
        List<ProductPurchaseRequest> sortedRequest = new ArrayList<>(request);
        sortedRequest.sort(Comparator.comparing(ProductPurchaseRequest::productId));

        List<ProductPurchaseResponse> purchasedProducts = new ArrayList<>();

        for (int i = 0; i < storedProducts.size(); i++) {
            Product product = storedProducts.get(i);
            ProductPurchaseRequest productRequest = sortedRequest.get(i);

            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException(
                        "Insufficient stock quantity for product with ID:: "
                                + productRequest.productId()
                );
            }

            int newAvailableQuantity =
                    (int) (product.getAvailableQuantity() - productRequest.quantity());

            product.setAvailableQuantity(newAvailableQuantity);

            repository.save(product);

            purchasedProducts.add(
                    mapper.toproductPurchaseResponse(
                            product,
                            productRequest.quantity()
                    )
            );
        }

        return purchasedProducts;
    }




}
