package uz.itransition.collectin.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.itransition.collectin.payload.response.APIResponse;
import uz.itransition.collectin.service.SearchService;

import static uz.itransition.collectin.controller.core.ControllerUtils.SEARCH_URI;

@RequestMapping(SEARCH_URI)
@RestController
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping()
    public ResponseEntity<?> search(
            @RequestParam(name = "text") String text
    ) {
        return ResponseEntity.ok(searchService.search(text));
    }

    @GetMapping("/element")
    public ResponseEntity<APIResponse> get(
            @RequestParam(name = "type") String type,
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "user_id", defaultValue = "0") Long userId
    ) {
        return ResponseEntity.ok(searchService.getElement(type, id, userId));
    }

}
