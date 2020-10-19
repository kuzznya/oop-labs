package itmo.oop.lab2.service;

import itmo.oop.lab2.model.Product;
import itmo.oop.lab2.model.Store;
import itmo.oop.lab2.repository.ProductRepository;
import itmo.oop.lab2.repository.StoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShopAssistantService {

    private final StoreRepository storeRepository;

    private final ProductRepository productRepository;

    public ShopAssistantService(StoreRepository storeRepository,
                                ProductRepository productRepository) {
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
    }

    public List<Store> getStoresSellingItem(UUID itemId) {
        return productRepository
                .getAllByItem_Id(itemId)
                .stream()
                .map(Product::getStore)
                .collect(Collectors.toList());
    }

    public Store getStoreWithLowestPrice(UUID itemId) {
        return getStoresSellingItem(itemId).stream().sorted((store1, store2) -> {
            if (store1.getProduct(itemId).isPresent() && store2.getProduct(itemId).isPresent()) {
                float price1 = store1.getProduct(itemId)
                        .get()
                        .getPrice();
                float price2 = store2.getProduct(itemId)
                        .get()
                        .getPrice();
                return price1 < price2 ? -1 : 1;
            }
            else
                return 0;
        })
                .findAny()
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Store with item " + itemId + " not found")
                );
    }
}
