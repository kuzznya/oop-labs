package itmo.oop.lab2.service;

import itmo.oop.lab2.model.Product;
import itmo.oop.lab2.model.Store;
import itmo.oop.lab2.repository.ProductRepository;
import itmo.oop.lab2.request.CheapestOrderRequest;
import itmo.oop.lab2.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopAssistantService {

    private final ProductRepository productRepository;
    private final StoreManagerService storeManagerService;

    public List<Store> getStoresSellingItem(UUID itemId) {
        return productRepository
                .getAllByItem_Id(itemId)
                .stream()
                .map(Product::getStore)
                .collect(Collectors.toList());
    }

    public Optional<Store> getStoreWithLowestPrice(UUID itemId) {
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
                .findAny();
    }

    private Product getMaxAmountByTotalPrice(Product product, float totalPrice) {
        int amount = Math.min((int) Math.floor(totalPrice / product.getPrice()), product.getAmount());
        float productPrice = product.getPrice() * amount;
        return new Product(product.getItem(), product.getStore(), amount, productPrice);
    }

    public List<Product> getProductsByTotalPrice(UUID storeId, float totalPrice) {
        return productRepository
                .findAll()
                .stream()
                .filter(product -> product.getStore().getId().equals(storeId))
                .map(product -> getMaxAmountByTotalPrice(product, totalPrice))
                .filter(product -> product.getAmount() > 0)
                .collect(Collectors.toList());
    }

    private Optional<StoreWithPrice> getTotalPriceOfRequest(OrderRequest request) {
        try {
            Store store = storeManagerService.getStore(request.getStoreId()).orElseThrow();
            double result = request
                    .getPositions()
                    .stream()
                    .map(position -> store
                            .getProduct(position.getItemId())
                            .filter(product -> product.getAmount() >= position.getAmount())
                            .orElseThrow()
                    ).mapToDouble(product -> product.getPrice() *
                            request
                                    .getPositions()
                                    .stream()
                                    .filter(position -> position.getItemId().equals(product.getItem().getId()))
                                    .findAny()
                                    .orElseThrow()
                                    .getAmount()).sum();
            return Optional.of(new StoreWithPrice(store, result));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public Optional<Store> getCheapestStoreByOrder(CheapestOrderRequest request) {
        return storeManagerService
                .getAllStores()
                .stream()
                .map(store -> getTotalPriceOfRequest(new OrderRequest(store.getId(), request.getPositions())))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(Comparator.comparingDouble(StoreWithPrice::getPrice))
                .map(StoreWithPrice::getStore)
                .findFirst();
    }

    @Value
    private static class StoreWithPrice {
        Store store;
        double price;
    }
}
