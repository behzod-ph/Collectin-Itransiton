package uz.itransition.collectin.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.itransition.collectin.entity.Collection;
import uz.itransition.collectin.entity.Field;
import uz.itransition.collectin.exception.DataNotFoundException;
import uz.itransition.collectin.payload.request.field.FieldRequest;
import uz.itransition.collectin.payload.response.APIResponse;
import uz.itransition.collectin.repository.CollectionRepository;
import uz.itransition.collectin.repository.FieldRepository;
import uz.itransition.collectin.service.core.CRUDService;

import static uz.itransition.collectin.component.ResourceConstants.COLLECTION_ENG;
import static uz.itransition.collectin.component.ResourceConstants.FIELD_ENG;

@Service
@RequiredArgsConstructor
public class FieldService implements CRUDService<Long, APIResponse, FieldRequest, FieldRequest> {

    private final CollectionRepository collectionRepository;
    private final FieldRepository fieldRepository;
    private final ModelMapper modelMapper;

    @Override
    public APIResponse create(FieldRequest fieldRequest) {
        Collection collection = collectionRepository
                .findById(fieldRequest.getId())
                .orElseThrow(() -> {
                    throw DataNotFoundException.of(COLLECTION_ENG,String.valueOf(fieldRequest.getId()));
                });
        Field field = modelMapper.map(fieldRequest, Field.class);
        field.setCollection(collection);
        fieldRepository.save(field);
        return APIResponse.success(true);
    }

    @Override
    public APIResponse get(Long id) {
        return APIResponse.success(
                fieldRepository
                        .findById(id)
                        .orElseThrow(() -> {
                            throw DataNotFoundException.of(FIELD_ENG,String.valueOf(id));
                        })
        );
    }

    public APIResponse getAllFieldsByCollectionId(Long id) {
        Collection collection = collectionRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw DataNotFoundException.of(COLLECTION_ENG,String.valueOf(id));
                });
        return APIResponse.success(fieldRepository.findAllByCollection(collection));
    }

    @Override
    public APIResponse modify(Long id, FieldRequest fieldRequest) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> {
                    throw DataNotFoundException.of(FIELD_ENG,String.valueOf(id));
                });
        modelMapper.map(fieldRequest, field);
        return APIResponse.success(field);
    }

    @Override
    public APIResponse delete(Long id) {
        fieldRepository.deleteById(id);
        return APIResponse.success(true);
    }
}
