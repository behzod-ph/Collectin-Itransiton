package uz.itransition.collectin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.itransition.collectin.entity.Topic;



@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
}
