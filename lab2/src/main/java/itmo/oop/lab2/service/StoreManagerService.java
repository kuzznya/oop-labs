package itmo.oop.lab2.service;

import itmo.oop.lab2.model.Product;
import itmo.oop.lab2.model.Store;
import itmo.oop.lab2.repository.StoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class StoreManagerService {

    private final StoreRepository storeRepository;

    public StoreManagerService(StoreRepository storeRepository, ItemService itemService) {
        this.storeRepository = storeRepository;
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Store getStore(UUID id) {
        return storeRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Store with id " + id + " not found")
                );
    }

    public UUID addStore(String address) {
        return storeRepository.save(new Store(address)).getId();
    }

    public Product getProduct(Store store, UUID itemId) {
        return store
                .getProduct(itemId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Product with id " + itemId + " not found in store")
                );
    }
}
