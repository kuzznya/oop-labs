package itmo.oop.lab2.service;

import itmo.oop.lab2.model.Product;
import itmo.oop.lab2.model.Store;
import itmo.oop.lab2.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StoreManagerService {

    private final StoreRepository storeRepository;

    public StoreManagerService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Optional<Store> getStore(UUID id) {
        return storeRepository.findById(id);
    }

    public UUID addStore(String name, String address) {
        return storeRepository.save(new Store(name, address)).getId();
    }

    public Optional<Product> getProduct(Store store, UUID itemId) {
        return store.getProduct(itemId);
    }
}
