package itmo.oop.lab2.service;

import itmo.oop.lab2.model.Item;
import itmo.oop.lab2.repository.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Item getItem(UUID id) {
        return itemRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Item with id " + id + " not found")
                );
    }

    public Item findItemByName(String name) {
        return itemRepository
                .findByName(name)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Item with name " + name + " not found")
                );
    }
}
