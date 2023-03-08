package uz.itransition.collectin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.itransition.collectin.payload.request.comment.CommentCreationRequest;
import uz.itransition.collectin.service.CommentService;

import static uz.itransition.collectin.controller.core.ControllerUtils.COMMENT_URI;

@RestController
@RequestMapping(COMMENT_URI)
public class CommentController {

    private final CommentService commentService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public CommentController(CommentService commentService, SimpMessagingTemplate simpMessagingTemplate) {
        this.commentService = commentService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<Void> sendMessage(@RequestBody CommentCreationRequest request) {
        simpMessagingTemplate.convertAndSend("/topic/comment/" + request.getItemId(), commentService.create(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @MessageMapping("/comment") // app/comment
    public ResponseEntity<Void> sendComment(@Payload CommentCreationRequest request) {
        simpMessagingTemplate.convertAndSend("/topic/comment/" + request.getItemId(), commentService.create(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
