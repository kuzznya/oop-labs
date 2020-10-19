package itmo.oop.lab2.controller;

import itmo.oop.lab2.model.Item;
import itmo.oop.lab2.request.AddItemRequest;
import itmo.oop.lab2.service.ItemService;
import org.springframework.web.bind.annotation.*;

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
    public Item findItemByName(@RequestParam String name) {
        return itemService.findItemByName(name);
    }
}
