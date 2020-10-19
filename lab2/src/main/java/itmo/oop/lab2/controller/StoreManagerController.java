package itmo.oop.lab2.controller;

import itmo.oop.lab2.model.Product;
import itmo.oop.lab2.model.Store;
import itmo.oop.lab2.request.RegisterStoreRequest;
import itmo.oop.lab2.service.StoreManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stores")
public class StoreManagerController {

    private final StoreManagerService managerService;

    public StoreManagerController(StoreManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping
    public UUID addStore(@RequestBody RegisterStoreRequest request) {
        return managerService.addStore(request.getName(), request.getAddress());
    }

    @GetMapping
    public List<Store> getStores() {
        return managerService.getAllStores();
    }

    @GetMapping("/{id}")
    public Store getStore(@PathVariable UUID id) {
        return managerService
                .getStore(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Product with id " + id + " not found in store")
                );
    }
}
