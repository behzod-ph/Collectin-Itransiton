package uz.itransition.collectin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.itransition.collectin.controller.core.AbstractCRUDController;
import uz.itransition.collectin.payload.request.collection.CollectionRequest;
import uz.itransition.collectin.service.CollectionService;

import static uz.itransition.collectin.controller.core.ControllerUtils.COLLECTION_URI;


@RestController
@RequestMapping(COLLECTION_URI)
public class CollectionController extends AbstractCRUDController<CollectionService, Long, CollectionRequest, CollectionRequest> {

    protected CollectionController(CollectionService service) {
        super(service);
    }

    @GetMapping
    public ResponseEntity<?> getList(
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(name = "size", required = false, defaultValue = "20") Integer size,
        @RequestParam(name = "order", required = false, defaultValue = "DESC") String order,
        @RequestParam(name = "categories", required = false) String[] categories
    ){
        return ResponseEntity.ok(service.getList(page, size, order, categories));
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getTopCollections(){
        return ResponseEntity.ok(service.getTopFiveCollections());
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserCollections(@PathVariable("userId") Long id){
        return ResponseEntity.ok(service.getUserCollections(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllUserCollectionsForAdmin(){
        return ResponseEntity.ok(service.getAllUserCollection());
    }
}
