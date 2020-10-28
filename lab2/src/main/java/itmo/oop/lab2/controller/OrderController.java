package itmo.oop.lab2.controller;

import itmo.oop.lab2.model.ProcessedOrder;
import itmo.oop.lab2.request.OrderRequest;
import itmo.oop.lab2.service.StoreService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final StoreService storeService;

    public OrderController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ProcessedOrder processOrder(@RequestBody OrderRequest orderRequest) {
        return storeService.processOrder(orderRequest);
    }
}
