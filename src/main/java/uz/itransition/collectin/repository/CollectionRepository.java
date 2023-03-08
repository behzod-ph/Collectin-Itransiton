package uz.itransition.collectin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.itransition.collectin.entity.Collection;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
    @Query("select c from Collection c where c.user.id = ?1")
    List<Collection> findAllByUser_Id(Long userId);

    @Query(value = "select * from collection c where c.doc @@ plainto_tsquery(:text)", nativeQuery = true)
    List<Collection> fullTextSearch(String text);



}
