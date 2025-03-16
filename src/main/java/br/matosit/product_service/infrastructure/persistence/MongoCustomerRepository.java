package br.matosit.product_service.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.matosit.product_service.application.ports.ProductRepository;
import br.matosit.product_service.domain.entities.Product;

@Repository
public interface MongoCustomerRepository extends MongoRepository<Product, String>, ProductRepository {
    
} 