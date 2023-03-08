package uz.itransition.collectin.controller;

import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.itransition.collectin.service.CollectionService;

import javax.servlet.http.HttpServletResponse;

import static uz.itransition.collectin.controller.core.ControllerUtils.COLLECTION_URI;


@RestController
@RequestMapping(COLLECTION_URI + "/csv")
public class CSVController {


    private final CollectionService collectionService;

    public CSVController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }


    @SneakyThrows
    @GetMapping()
    public ResponseEntity<Void> createCSV(
            @RequestParam(name = "collection_id") Long collectionId,
            @RequestParam(name = "lang") String lang,
            HttpServletResponse response
    ) {
        final String FILE_NAME = collectionService.loadCSV(response, collectionId, lang);

        String contentType = "text/csv";
        String headerValue = "attachment; filename=\"" + FILE_NAME + "\"";
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue).build();


    }
}
