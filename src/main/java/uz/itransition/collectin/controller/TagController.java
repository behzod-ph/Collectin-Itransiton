package uz.itransition.collectin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.itransition.collectin.controller.core.AbstractCRUDController;
import uz.itransition.collectin.payload.request.tag.TagRequest;
import uz.itransition.collectin.service.TagService;

import static uz.itransition.collectin.controller.core.ControllerUtils.TAG_URI;


@RestController
@RequestMapping(TAG_URI)
public class TagController extends AbstractCRUDController<TagService, Long, TagRequest, TagRequest> {

    private final TagService tagService;

    protected TagController(TagService service) {
        super(service);
        this.tagService = service;
    }


}
