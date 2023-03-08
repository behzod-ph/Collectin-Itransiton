package uz.itransition.collectin.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.itransition.collectin.entity.Tag;
import uz.itransition.collectin.exception.DataNotFoundException;
import uz.itransition.collectin.payload.request.tag.TagRequest;
import uz.itransition.collectin.payload.response.APIResponse;
import uz.itransition.collectin.payload.response.tag.TagResponse;
import uz.itransition.collectin.repository.TagRepository;
import uz.itransition.collectin.service.core.CRUDService;

import static uz.itransition.collectin.component.ResourceConstants.TAG_ENG;


@Service
@RequiredArgsConstructor
public class TagService implements CRUDService<Long, APIResponse, TagRequest, TagRequest> {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Override
    public APIResponse create(TagRequest tagRequest) {
        return APIResponse.success(tagRepository
                .save(modelMapper.map(tagRequest, Tag.class)));
    }

    @Override
    public APIResponse get(Long id) {
        return APIResponse.success(modelMapper
                .map(tagRepository.findById(id)
                        .orElseThrow(() -> {
                            throw DataNotFoundException.of(TAG_ENG,String.valueOf(id));
                        }), TagRequest.class));
    }

    @Override
    public APIResponse modify(Long id, TagRequest tagRequest)
    {
        final Tag tag = tagRepository.findById(id).orElseThrow(() -> {
            throw DataNotFoundException.of(TAG_ENG,String.valueOf(id));
        });
        modelMapper.map(tag, tagRequest);
        tagRepository.save(tag);

        return APIResponse.success(modelMapper.map(tag, TagResponse.class));
    }

    @Override
    public APIResponse delete(Long id)
    {
        tagRepository.findById(id).orElseThrow(() -> {
            throw DataNotFoundException.of(TAG_ENG, String.valueOf(id));
        });
        tagRepository.deleteById(id);
        return APIResponse.success(HttpStatus.OK.value());
    }

    public APIResponse getList() {
        return APIResponse.success(modelMapper
                .map(tagRepository.findAll(), TagResponse[].class));
    }
}
