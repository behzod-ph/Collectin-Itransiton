package uz.itransition.collectin.controller.core;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.itransition.collectin.service.marker.Creationable;
import uz.itransition.collectin.service.marker.Modifiable;

import javax.transaction.Transactional;
import javax.validation.Valid;


public interface CRUDController<ID, C extends Creationable, U extends Modifiable> {

    @PostMapping
    ResponseEntity<?> create(@RequestBody @Valid C c);

    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable ID id);


//    @PutMapping("/{id}")
    @PostMapping("/edit/{id}")
    ResponseEntity<?> modify(@PathVariable ID id, @RequestBody @Valid U u);



//    @DeleteMapping("/{id}")
    @GetMapping("/delete/{id}")
    ResponseEntity<?> delete(@PathVariable ID id);


}
