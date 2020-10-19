package itmo.oop.lab2.service;

import itmo.oop.lab2.model.Item;
import itmo.oop.lab2.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public UUID addItem(String name) {
        return itemRepository.save(new Item(name)).getId();
    }

    public Optional<Item> getItem(UUID id) {
        return itemRepository.findById(id);
    }

    public Optional<Item> findItemByName(String name) {
        return itemRepository.findByName(name);
    }
}
