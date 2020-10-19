package itmo.oop.lab2.controller;

import itmo.oop.lab2.model.Store;
import itmo.oop.lab2.service.ShopAssistantService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/assistant")
public class AssistantController {

    private final ShopAssistantService assistantService;

    public AssistantController(ShopAssistantService assistantService) {
        this.assistantService = assistantService;
    }

    @GetMapping
    public Store findStoreWithLowestPrice(@RequestParam("item") UUID itemId) {
        return assistantService.getStoreWithLowestPrice(itemId);
    }
}
