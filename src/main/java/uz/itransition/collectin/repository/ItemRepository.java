package uz.itransition.collectin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.itransition.collectin.entity.Item;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT collection_id FROM item GROUP BY collection_id ORDER BY COUNT(collection_id) DESC LIMIT 5", nativeQuery = true)
    List<Long> findLargeFiveCollectionIds();

    @Query(value = "SELECT * FROM item  ORDER BY creation_date DESC LIMIT 10", nativeQuery = true)
    List<Item> findLatestTenItemsBy();

    @Query("select i from Item i where i.collection.id = ?1")
    List<Item> findAllByCollection_Id(Long id);

    @Query("select i from Item i inner join i.tags tags where tags.id = ?1")
    List<Item> findAllByTags_Id(Long tags_id);

    @Query("select (count(i) > 0) from Item i inner join i.likedUsers likedUsers where i.id = ?1 and likedUsers.id = ?2")
    Boolean existsByIdAndLikedUsers_Id(Long itemId, Long userId);

    @Transactional
    @Modifying
    @Query(value = "insert into item_liked_users(item_id, liked_users_id) select :itemId, :userId where " +
            " not exists (select 1 from item_liked_users t where t.item_id = :itemId and t.liked_users_id = :userId)",
        nativeQuery = true
    )
    void insertItemLike(@Param("itemId") long itemId, @Param("userId") long userId);

    @Transactional
    @Modifying
    @Query(value = "delete from item_liked_users where " +
            " exists (select 1 from item_liked_users t where t.item_id = :itemId and t.liked_users_id = :userId)",
            nativeQuery = true
    )
    void deleteItemLike(@Param("itemId") long itemId, @Param("userId") long userId);


    @Query(value = "select * from item i where i.doc @@ plainto_tsquery(:text)", nativeQuery = true)
    List<Item> fullTextSearch(String text);

    void deleteAllByCollection_Id(Long id);

}
