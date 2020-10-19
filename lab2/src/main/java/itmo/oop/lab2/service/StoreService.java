package itmo.oop.lab2.service;

import itmo.oop.lab2.model.Product;
import itmo.oop.lab2.model.Store;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class StoreService {
    public Optional<Product> getProduct(Store store, UUID itemId) {
        return store.getProduct(itemId);
    }
}
