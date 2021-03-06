package itmo.oop.lab2.controller;

import itmo.oop.lab2.model.Product;
import itmo.oop.lab2.model.Store;
import itmo.oop.lab2.request.CheapestOrderRequest;
import itmo.oop.lab2.service.ShopAssistantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assistant")
public class AssistantController {

    private final ShopAssistantService assistantService;

    public AssistantController(ShopAssistantService assistantService) {
        this.assistantService = assistantService;
    }

    @GetMapping("/stores-by-item")
    public List<Store> findStoresSellingItem(@RequestParam("item") UUID itemId) {
        return assistantService.getStoresSellingItem(itemId);
    }

    @GetMapping("/cheapest-store")
    public Store findStoreWithLowestPrice(@RequestParam("item") UUID itemId) {
        return assistantService
                .getStoreWithLowestPrice(itemId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Store with item " + itemId + " not found")
                );
    }

    @GetMapping("/max-by-total-price")
    public List<Product> getProductsByTotalPrice(@RequestParam("store") UUID storeId,
                                                 @RequestParam("total") float totalPrice) {
        return assistantService.getProductsByTotalPrice(storeId, totalPrice);
    }

    @PostMapping("/cheapest-store")
    public Store findStoreWithCheapestOrder(@RequestBody CheapestOrderRequest request) {
        return assistantService
                .getCheapestStoreByOrder(request)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found")
                );
    }
}
