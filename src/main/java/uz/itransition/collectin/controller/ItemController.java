package uz.itransition.collectin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.itransition.collectin.controller.core.AbstractCRUDController;
import uz.itransition.collectin.payload.request.collection.ItemRequest;
import uz.itransition.collectin.payload.response.APIResponse;
import uz.itransition.collectin.service.ItemService;

import static uz.itransition.collectin.controller.core.ControllerUtils.ITEM_URI;


@RestController
@RequestMapping(ITEM_URI)
public class ItemController extends AbstractCRUDController<ItemService, Long, ItemRequest, ItemRequest> {

    protected ItemController(ItemService service) {
        super(service);
    }

    @GetMapping("/collection/{id}")
    public ResponseEntity<APIResponse> getItemsByCollectionId(@PathVariable Long id){
        return ResponseEntity.ok(service.getItemsByCollectionId(id));
    }

    @GetMapping("/tag/{id}")
    public ResponseEntity<APIResponse> getItemsByTagId(@PathVariable Long id){
        return ResponseEntity.ok(service.getItemsByTagId(id));
    }

    @GetMapping("/{user_id}/{item_id}")
    public ResponseEntity<APIResponse> getItemResponse(
            @PathVariable("user_id") Long userId,
            @PathVariable("item_id") Long itemId){
        return ResponseEntity.ok(service.getItemResponse(itemId, userId));
    }

    @GetMapping("/latest")
    public ResponseEntity<APIResponse> getLatestTenItems(){
        return ResponseEntity.ok(service.getTenLatest());
    }

    @GetMapping("/like/{user_id}/{item_id}")
    public ResponseEntity<APIResponse> updateItemLike(
            @PathVariable("user_id") Long userId,
            @PathVariable("item_id") Long itemId,
            @RequestParam(name = "isLiked") boolean isLiked){
        return ResponseEntity.ok(service.updateItemLike(userId, itemId, isLiked));
    }
}
