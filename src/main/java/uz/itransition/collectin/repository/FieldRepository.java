package uz.itransition.collectin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.itransition.collectin.entity.Collection;
import uz.itransition.collectin.entity.Field;

import java.util.List;


@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    @Query("select f from Field f where f.collection = ?1")
    List<Field> findAllByCollection(Collection collection);

    int deleteAllByCollection_Id(Long collectionId);

    @Query(value = "SELECT collection_id FROM field GROUP BY collection_id ORDER BY COUNT(collection_id) DESC LIMIT 5", nativeQuery = true)
    List<Long> findTopCollectionIds();

    @Query("select f from Field f where f.collection.id = ?1")
    List<Field> findAllByCollection_Id(Long id);

    @Query("select f from Field f where f.collection.id = ?1 order by f.name")
    List<Field> findAllByCollectionIdOrderOrderByName(Long collectionId);

    void deleteFieldsByCollection_Id(Long collectionId);

}
