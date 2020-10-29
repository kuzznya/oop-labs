package itmo.oop.lab2.controller;

import itmo.oop.lab2.model.Product;
import itmo.oop.lab2.model.Store;
import itmo.oop.lab2.request.AddProductRequest;
import itmo.oop.lab2.service.StoreManagerService;
import itmo.oop.lab2.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    private final StoreManagerService managerService;

    public StoreController(StoreService storeService, StoreManagerService managerService) {
        this.storeService = storeService;
        this.managerService = managerService;
    }

    @PostMapping("/{storeId}/products")
    public void addProduct(@PathVariable UUID storeId, @RequestBody AddProductRequest request) {
        storeService.addProduct(storeId, request.getItemId(), request.getAmount(), request.getPrice());
    }

    @GetMapping("/{storeId}/products")
    public List<Product> getProducts(@PathVariable UUID storeId) {
        return managerService
                .getStore(storeId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Store with id " + storeId + " not found")
                )
                .getProducts();
    }

    @GetMapping("/{storeId}/products/{itemId}")
    public Product getProduct(@PathVariable UUID storeId, @PathVariable UUID itemId) {
        Store store = managerService
                .getStore(storeId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Store with id " + storeId + " not found")
                );

        return storeService
                .getProduct(store, itemId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Product with id " + itemId + " not found in store")
                );
    }
}
