package uz.itransition.collectin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.itransition.collectin.entity.FieldValue;

import java.util.List;


@Repository
public interface FieldValueRepository extends JpaRepository<FieldValue, Long> {

    @Query("select f from FieldValue f where f.item.collection.id = ?1 order by f.item.id, f.item.name")
    List<FieldValue> findAllByItem_CollectionId(Long collection_id);

    @Query("select f from FieldValue f where f.item.id = ?1")
    List<FieldValue> findAllByItemId(Long item_id);

    @Query("select f from FieldValue f inner join f.item.tags tags where tags.id = ?1")
    List<FieldValue> findAllByItem_Tags_Id(Long item_tags_id);

    @Query("select f from FieldValue f where f.item.collection.id = ?1 order by f.item.id, f.item.name")
    List<FieldValue> findAllByCollectionIdOrderItemIdAndByFieldName(Long collection_id);

    void deleteFieldValuesByField_Collection_Id(Long collectionId);

    void deleteFieldValuesByItem_Id(Long itemId);
}
