package itmo.oop.lab2.controller;

import itmo.oop.lab2.model.Product;
import itmo.oop.lab2.model.Store;
import itmo.oop.lab2.request.RegisterStoreRequest;
import itmo.oop.lab2.service.StoreManagerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreManagerService managerService;

    public StoreController(StoreManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping
    public UUID addStore(@RequestBody RegisterStoreRequest request) {
        return managerService.addStore(request.getAddress());
    }

    @GetMapping
    public List<Store> getStores() {
        return managerService.getAllStores();
    }

    @GetMapping("/{id}")
    public Store getStore(@PathVariable UUID id) {
        return managerService.getStore(id);
    }

    @GetMapping("/{storeId}/products")
    public List<Product> getProducts(@PathVariable UUID storeId) {
        return managerService.getStore(storeId).getProducts();
    }

    @GetMapping("/{storeId}/products/{itemId}")
    public Product getProduct(@PathVariable UUID storeId, @PathVariable UUID itemId) {
        return managerService.getProduct(managerService.getStore(storeId), itemId);
    }
}
