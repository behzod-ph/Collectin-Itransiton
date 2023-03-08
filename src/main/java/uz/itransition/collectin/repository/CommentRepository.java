package uz.itransition.collectin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.itransition.collectin.entity.Comment;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByItemIdOrderByCreationDateDesc(Long item_id);

    @Query(value = "select * from comment c where c.doc @@ plainto_tsquery(:text)", nativeQuery = true)
    List<Comment> fullTextSearch(String text);

    void deleteCommentsByItem_Collection_Id(Long collectionId);

    void deleteCommentsByItem_Id(Long itemId);
}
