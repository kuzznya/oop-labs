package itmo.oop.lab2.controller;

import itmo.oop.lab2.model.Item;
import itmo.oop.lab2.request.AddItemRequest;
import itmo.oop.lab2.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public UUID addItem(@RequestBody AddItemRequest request) {
        return itemService.addItem(request.getName());
    }

    @GetMapping
    public List<Item> getItems() {
        return itemService.getItems();
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable UUID id) {
        return itemService
                .getItem(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Item with id " + id + " not found")
                );
    }

    @GetMapping(params = "name")
    public Item findItemByName(@RequestParam String name) {
        return itemService
                .findItemByName(name)
                .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Item with name " + name + " not found")
                );
    }
}
