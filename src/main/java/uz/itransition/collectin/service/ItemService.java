package uz.itransition.collectin.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.itransition.collectin.entity.*;
import uz.itransition.collectin.exception.DataNotFoundException;
import uz.itransition.collectin.payload.request.collection.FieldValueUpdateRequest;
import uz.itransition.collectin.payload.request.collection.ItemRequest;
import uz.itransition.collectin.payload.request.collection.ItemUpdateRequest;
import uz.itransition.collectin.payload.request.field.FieldValueRequest;
import uz.itransition.collectin.payload.response.APIResponse;
import uz.itransition.collectin.payload.response.collection.FieldResponse;
import uz.itransition.collectin.payload.response.collection.FieldValueListResponse;
import uz.itransition.collectin.payload.response.collection.FieldValueResponse;
import uz.itransition.collectin.payload.response.comment.CommentResponse;
import uz.itransition.collectin.payload.response.item.ItemResponse;
import uz.itransition.collectin.payload.response.item.ItemTagResponse;
import uz.itransition.collectin.payload.response.tag.TagResponse;
import uz.itransition.collectin.repository.*;
import uz.itransition.collectin.service.core.CRUDService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static uz.itransition.collectin.component.ResourceConstants.*;

@Service
@RequiredArgsConstructor
public class ItemService implements CRUDService<Long, APIResponse, ItemRequest, ItemUpdateRequest> {

    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository;
    private final FieldRepository fieldRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;
    private final FieldValueRepository fieldValueRepository;
    private final CollectionRepository collectionRepository;

    @Override
    public APIResponse create(ItemRequest itemRequest)
    {
        final List<Long> tagIdList = itemRequest.getTagIdList();
        final List<Tag> tagList = tagRepository.findAllById(tagIdList);
        final Collection collection = collectionRepository
                .findById(itemRequest.getCollectionId())
                .orElseThrow(() -> {
                    throw DataNotFoundException.of(COLLECTION_ENG, String.valueOf(itemRequest.getCollectionId()));
                });
        final Item item = itemRepository.save(new Item(itemRequest.getName(), collection, tagList));
        return APIResponse.success(fieldValueRepository.saveAll(getFieldValues(item, itemRequest.getFieldValues())));
    }

    @Override
    public APIResponse get(Long id) {
        return APIResponse.success(
                itemRepository.findById(id).orElseThrow(() -> {
                    throw DataNotFoundException.of(ITEM_ENG,String.valueOf(id));
                })
        );
    }

    public APIResponse getTenLatest(){
        final List<Item> items = itemRepository.findLatestTenItemsBy();
        List<ItemResponse> itemResponses = items.stream().map(item -> map(item, null)).collect(Collectors.toList());
        return APIResponse.success(itemResponses);
    }


    public APIResponse getItemResponse(Long id, Long userId) {
        final Item item = itemRepository.findById(id).orElseThrow(() -> {
                    throw DataNotFoundException.of(ITEM_ENG,String.valueOf(id));
                }
        );

        return APIResponse.success(map(item, userId));
    }

    public ItemResponse map(Item item, Long userId)
    {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                List.of(modelMapper.map(item.getTags(), TagResponse[].class)),
                getFieldValueListResponse(item.getCollection().getId()),
                item.getLikes(),
                List.of(modelMapper.map(commentRepository.findAllByItemIdOrderByCreationDateDesc(item.getId()), CommentResponse[].class)),
                userId != null && itemRepository.existsByIdAndLikedUsers_Id(item.getId(), userId));
    }

    @Override
    public APIResponse modify(Long id, ItemUpdateRequest itemRequest)
    {
        final Item item = itemRepository.findById(id).orElseThrow(() -> DataNotFoundException.of(ITEM_ENG, String.valueOf(id)));
        final List<Tag> tags = tagRepository.findAllById(itemRequest.getTags());
        final List<Long> filedIds = itemRequest.getFieldValues().stream().map(FieldValueUpdateRequest::getFieldId).collect(Collectors.toList());
        final List<Field> fields = fieldRepository.findAllById(filedIds);
        final List<FieldValue> fieldValues = itemRequest.getFieldValues().stream().map(fieldValue ->
              new FieldValue(fieldValue.getValue(), item,
                    fields.stream().filter(f -> f.getId() == fieldValue.getFieldId()).findFirst().orElseThrow(
                            () -> DataNotFoundException.of(FIELD_ENG, String.valueOf(fieldValue.getFieldId())))
            )
        ).collect(Collectors.toList());

        item.setName(itemRequest.getName());
        item.setTags(tags);
        itemRepository.save(item);
        fieldValueRepository.saveAll(fieldValues);

        return getItemResponse(item.getId(), item.getCollection().getUser().getId());
    }


    @Transactional
    @Override
    public APIResponse delete(Long id)
    {
        itemRepository.findById(id).orElseThrow(()->DataNotFoundException.of(ITEM_ENG,String.valueOf(id)));
        fieldValueRepository.deleteFieldValuesByItem_Id(id);
        commentRepository.deleteCommentsByItem_Id(id);
        itemRepository.deleteById(id);
        return APIResponse.success(HttpStatus.OK.value());
    }


    public APIResponse getItemsByTagId(Long tagId)
    {
        final Map<Long, List<ItemTagResponse>> collect = fieldValueRepository.findAllByItem_Tags_Id(tagId).stream()
                .map(fieldValue -> new ItemTagResponse(fieldValue.getItem().getId(), fieldValue.getField().getName(), fieldValue.getValue()))
                .collect(Collectors.groupingBy(ItemTagResponse::getItemId));
        return APIResponse.success(collect);
    }



    public APIResponse getItemsByCollectionId(Long collectionId) {
        return APIResponse.success(getFieldValueListResponse(collectionId));
    }

    private List<FieldValue> getFieldValues(Item item, List<FieldValueRequest> fieldValues) {
        return fieldValues
                .stream()
                .map((fvr) -> new FieldValue(fvr.getValue(),
                        item,
                        fieldRepository
                                .findById(fvr.getFieldId())
                                .orElseThrow(() -> DataNotFoundException.of(FIELD_ENG, String.valueOf(fvr.getFieldId())))))
                .collect(Collectors.toList());
    }

    public APIResponse updateItemLike(Long userId, Long itemId, boolean isLiked) {
        if (isLiked) {
            itemRepository.insertItemLike(itemId, userId);
        } else {
            itemRepository.deleteItemLike(itemId, userId);
        }
        return APIResponse.success(HttpStatus.OK.value());
    }

    private FieldValueListResponse getFieldValueListResponse(Long collectionId) {
        final FieldValueListResponse fieldList = new FieldValueListResponse();

        fieldList.setTypes(fieldRepository.findAllByCollection_Id(collectionId)
                .stream().map(elm -> modelMapper.map(elm, FieldResponse.class)).collect(Collectors.toList()));
        fieldList.setValues(fieldValueRepository
                .findAllByItem_CollectionId(collectionId)
                .stream()
                .map(item -> new FieldValueResponse(item.getValue(), item.getItem().getId(), item.getField().getId()))
                .collect(Collectors.groupingBy(FieldValueResponse::getItemId)));

        return fieldList;
    }
}
