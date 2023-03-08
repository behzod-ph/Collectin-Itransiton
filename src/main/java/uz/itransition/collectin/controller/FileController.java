package uz.itransition.collectin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.itransition.collectin.payload.response.APIResponse;
import uz.itransition.collectin.service.FileService;

import java.util.List;
import java.util.stream.Collectors;

import static uz.itransition.collectin.controller.core.ControllerUtils.FILE_URI;


@RestController
@RequiredArgsConstructor
@RequestMapping(FILE_URI)
public class FileController {

    private final FileService fileService;

    @PostMapping("/list")
    public ResponseEntity<?> uploadFiles(MultipartHttpServletRequest request) {
        return ResponseEntity.ok(APIResponse.success(request
                .getFileMap()
                .values()
                .parallelStream()
                .map(fileService::uploadFile)
                .collect(Collectors.toList())));
    }

    @PostMapping()
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) {
        return ResponseEntity.ok(
                APIResponse.success(List.of(fileService.uploadFile(file))
        ));
    }
}
