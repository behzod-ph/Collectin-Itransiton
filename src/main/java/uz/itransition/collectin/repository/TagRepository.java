package uz.itransition.collectin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.itransition.collectin.entity.Tag;



@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

}
