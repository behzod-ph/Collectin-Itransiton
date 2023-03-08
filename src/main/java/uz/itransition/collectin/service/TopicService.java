package uz.itransition.collectin.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.itransition.collectin.entity.Topic;
import uz.itransition.collectin.exception.DataNotFoundException;
import uz.itransition.collectin.payload.request.collection.TopicRequest;
import uz.itransition.collectin.payload.response.APIResponse;
import uz.itransition.collectin.payload.response.collection.TopicResponse;
import uz.itransition.collectin.repository.TopicRepository;
import uz.itransition.collectin.service.core.CRUDService;

import java.util.List;

import static uz.itransition.collectin.component.ResourceConstants.TOPIC_ENG;

@Service
public class TopicService implements CRUDService<Long, APIResponse, TopicRequest, TopicRequest> {

    private final ModelMapper modelMapper;
    private final TopicRepository topicRepository;

    public TopicService(ModelMapper modelMapper, TopicRepository topicRepository) {
        this.modelMapper = modelMapper;
        this.topicRepository = topicRepository;
    }

    @Override
    public APIResponse create(TopicRequest topicRequest) {
        return APIResponse.success(topicRepository
                .save(modelMapper.map(topicRequest, Topic.class)));
    }

    @Override
    public APIResponse get(Long id)
    {
        final Topic topic = topicRepository.findById(id).orElseThrow(() -> {
            throw DataNotFoundException.of(TOPIC_ENG,String.valueOf(id));
        });
        return APIResponse.success(topic);
    }

    @Override
    public APIResponse modify(Long id, TopicRequest topicRequest)
    {
        final Topic topic = topicRepository.findById(id).orElseThrow(() -> {
            throw DataNotFoundException.of(TOPIC_ENG,String.valueOf(id));
        });
        modelMapper.map(topicRequest, topic);
        return APIResponse.success(topicRepository.save(topic));
    }

    @Override
    public APIResponse delete(Long id)
    {
        final Topic topic = topicRepository.findById(id).orElseThrow(() -> {
            throw DataNotFoundException.of(TOPIC_ENG,String.valueOf(id));
        });
        topicRepository.delete(topic);
        return APIResponse.success(true);
    }

    public APIResponse getAll() {
        return APIResponse.success(List.of(modelMapper.map(topicRepository.findAll(), TopicResponse[].class)));
    }
}
