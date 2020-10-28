package itmo.oop.lab2.service;

import itmo.oop.lab2.model.Item;
import itmo.oop.lab2.model.ProcessedOrder;
import itmo.oop.lab2.model.Product;
import itmo.oop.lab2.model.Store;
import itmo.oop.lab2.repository.ItemRepository;
import itmo.oop.lab2.repository.OrderRepository;
import itmo.oop.lab2.repository.StoreRepository;
import itmo.oop.lab2.request.OrderRequest;
import itmo.oop.lab2.util.exception.OrderException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    public StoreService(StoreRepository storeRepository,
                        OrderRepository orderRepository,
                        ItemRepository itemRepository) {
        this.storeRepository = storeRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    private void updateStoreAndSave(UUID storeId, Consumer<Store> update) {
        Store store = storeRepository.getOne(storeId);
        update.accept(store);
        storeRepository.save(store);
    }

    public Optional<Product> getProduct(Store store, UUID itemId) {
        return store.getProduct(itemId);
    }

    public void addProduct(UUID storeId, UUID itemId, int amount, int price) {
        Item item = itemRepository.getOne(itemId);
        updateStoreAndSave(storeId, store -> store.addProduct(item, amount, price));
    }

    @Transactional
    public ProcessedOrder processOrder(OrderRequest request) {
        Store store = storeRepository.getOne(request.getStoreId());

        ProcessedOrder order = new ProcessedOrder(store);

        for (var position : request.getPositions())
            order.addPosition(
                    store.orderProduct(position.getItemId(), position.getAmount())
                            .orElseThrow(OrderException::new)
            );

        // FIXME: 20.10.2020 save not only order but also positions
        storeRepository.save(store);

        return orderRepository.save(order);
    }
}
