package uz.itransition.collectin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.itransition.collectin.controller.core.AbstractCRUDController;
import uz.itransition.collectin.payload.request.field.FieldRequest;
import uz.itransition.collectin.service.FieldService;

import static uz.itransition.collectin.controller.core.ControllerUtils.FIELD_URI;

@RestController
@RequestMapping(FIELD_URI)
public class FieldController extends AbstractCRUDController<FieldService, Long, FieldRequest, FieldRequest> {

    protected FieldController(FieldService service) {
        super(service);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getFieldsByCollection(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(service.getAllFieldsByCollectionId(id));
    }
}
