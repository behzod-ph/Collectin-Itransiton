package uz.itransition.collectin.service;

import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.itransition.collectin.entity.*;
import uz.itransition.collectin.exception.DataNotFoundException;
import uz.itransition.collectin.exception.UserNotFoundException;
import uz.itransition.collectin.payload.request.collection.CollectionRequest;
import uz.itransition.collectin.payload.request.field.FieldRequest;
import uz.itransition.collectin.payload.response.APIResponse;
import uz.itransition.collectin.payload.response.collection.CollectionResponse;
import uz.itransition.collectin.payload.response.collection.FieldResponse;
import uz.itransition.collectin.repository.*;
import uz.itransition.collectin.service.core.CRUDService;
import uz.itransition.collectin.util.CSVCreator;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import static uz.itransition.collectin.component.ResourceConstants.COLLECTION_ENG;
import static uz.itransition.collectin.component.ResourceConstants.TOPIC_ENG;

@Service
@RequiredArgsConstructor
public class CollectionService implements CRUDService<Long, APIResponse, CollectionRequest, CollectionRequest> {

    private final CollectionRepository collectionRepository;
    private final TopicRepository topicRepository;
    private final FieldRepository fieldRepository;

    private final CommentRepository commentRepository;

    private final FieldValueRepository fieldValueRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final static String DEFAULT_SORT_FIELD = "creationDate";

    @Override
    public APIResponse create(CollectionRequest collectionRequest) {
        User user = userRepository.findById(collectionRequest.getUserId()).orElseThrow(() -> {
            throw UserNotFoundException.of(String.valueOf(collectionRequest.getUserId()));
        });
        Topic topic = topicRepository.findById(collectionRequest.getTopicId()).orElseThrow(() -> {
            throw DataNotFoundException.of(TOPIC_ENG, String.valueOf(collectionRequest.getTopicId()));
        });
        Collection collection = collectionRepository.save(
                new Collection()
                .setName(collectionRequest.getName())
                .setDescription(collectionRequest.getDescription())
                .setImageUrl(collectionRequest.getImageUrl())
                .setUser(user)
                .setTopic(topic));

        return APIResponse.success(getCollectionResponse(collection,
                collectionRequest.getCustomFields()));
    }

    @Override
    public APIResponse get(Long id) {
        final Collection collection = collectionRepository.findById(id).orElseThrow(() -> {
            throw DataNotFoundException.of(COLLECTION_ENG,String.valueOf(id));
        });
        final FieldResponse[] fieldList = modelMapper.map(fieldRepository.findAllByCollection(collection), FieldResponse[].class);
        final CollectionResponse response = modelMapper.map(collection, CollectionResponse.class);

        response.setFields(fieldList);
        return APIResponse.success(response);
    }

    public APIResponse getList(int page, int size, String order, String[] categories) {
        categories = (categories == null || categories.length == 0) ? new String[]{DEFAULT_SORT_FIELD} : categories;
        final Page<Collection> list = collectionRepository
                .findAll(PageRequest.of(page, size, Sort.Direction.valueOf(order), categories));
        return APIResponse.success(getCollectionResponseList(list.getContent()));
    }

    @Override
    public APIResponse modify(Long id, CollectionRequest collectionRequest) {
        final User user = userRepository.findById(collectionRequest.getUserId()).orElseThrow(() -> {
            throw UserNotFoundException.of(String.valueOf(collectionRequest.getUserId()));
        });
        final Topic topic = topicRepository.findById(collectionRequest.getTopicId()).orElseThrow(() -> {
            throw DataNotFoundException.of(TOPIC_ENG, String.valueOf(collectionRequest.getTopicId()));
        });
        Collection collection = collectionRepository.findById(id).orElseThrow(() -> {
            throw DataNotFoundException.of(COLLECTION_ENG, String.valueOf(id));
        });
        collection.setImageUrl(collectionRequest.getImageUrl());
        collection.setDescription(collection.getDescription());
        collection.setName(collectionRequest.getName());
        collection.setTopic(topic);
        collection.setUser(user);
        collection = collectionRepository.save(collection);
        return APIResponse.success(getCollectionResponse(collection,
                collectionRequest.getCustomFields()));
    }

    @Transactional
    @Override
    public APIResponse delete(Long id) {
        fieldValueRepository.deleteFieldValuesByField_Collection_Id(id);
        fieldRepository.deleteFieldsByCollection_Id(id);
        commentRepository.deleteCommentsByItem_Collection_Id(id);
        itemRepository.deleteAllByCollection_Id(id);
        collectionRepository.deleteById(id);
        return APIResponse.success(true);
    }

    public APIResponse getTopFiveCollections() {
        final List<Long> collectionIds = itemRepository.findLargeFiveCollectionIds();
        return APIResponse.success(getCollectionResponseList(collectionRepository.findAllById(collectionIds)));
    }

    public APIResponse getUserCollections(Long userId) {
        final List<Collection> collectionList = collectionRepository.findAllByUser_Id(userId);
        return APIResponse.success(getCollectionResponseList(collectionList));
    }

    private CollectionResponse getCollectionResponse(Collection collection, FieldRequest[] fieldRequests) {
        final CollectionResponse response = modelMapper.map(collection, CollectionResponse.class);
        response.setFields(saveCollectionFields(collection, fieldRequests));
        return response;
    }

    private FieldResponse[] saveCollectionFields(Collection collection, FieldRequest[] fieldRequests) {
        List<Field> fields = List.of(modelMapper.map(fieldRequests, Field[].class));
        fields.forEach(field -> field.setCollection(collection));
        fields = fieldRepository.saveAll(fields);
        return modelMapper.map(fields, FieldResponse[].class);
    }

    private List<CollectionResponse> getCollectionResponseList(List<Collection> collectionList) {
        return collectionList
                .stream()
                .map(collection -> modelMapper.map(collection, CollectionResponse.class)).collect(Collectors.toList());
    }

    public APIResponse getAllUserCollection() {
        final List<Collection> all = collectionRepository.findAll(Sort.by("creationDate").descending());
        return APIResponse.success(List.of(modelMapper.map(all, CollectionResponse[].class)));
    }

    public String loadCSV(HttpServletResponse response, long collectionId, String lang) {
        try {
            PrintWriter writer = response.getWriter();
            CSVWriter csvWriter = new CSVWriter(writer);
            final Collection collection = collectionRepository.findById(collectionId).orElseThrow(() -> {
                throw DataNotFoundException.of(COLLECTION_ENG, String.valueOf(collectionId));
            });
            final List<Field> fields = fieldRepository.findAllByCollectionIdOrderOrderByName(collectionId);
            final List<FieldValue> fieldValues = fieldValueRepository.findAllByCollectionIdOrderItemIdAndByFieldName(collectionId);
            return CSVCreator.ofCollection(csvWriter, lang, collection, fields, fieldValues);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
