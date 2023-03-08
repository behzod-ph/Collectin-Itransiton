package uz.itransition.collectin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import java.util.LinkedList;
import java.util.List;

import static uz.itransition.collectin.component.ResourceConstants.COLLECTION_ENG;
import static uz.itransition.collectin.component.ResourceConstants.COLLECTION_RUS;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Collection extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    private String imageUrl;

    @ManyToOne
    private Topic topic;

    @ManyToOne
    private User user;

    public List<String[]> toCSVCollectionHeader(String lang) {
        List<String[]> list = new LinkedList<>();

        return lang.equals("ENG") ?
                List.of(
                new String[]{COLLECTION_ENG},
                new String[]{"name", "description", "image", "topic", "creation date", "author"},
                new String[]{name, description, imageUrl,  topic.getName(), getCreationDate().toString(), user.getName()}
        ) :
                List.of(
                new String[]{COLLECTION_RUS},
                new String[]{"название", "описание", "изображение", "заголовок", "дата создания", "автор"},
                new String[]{name, description, imageUrl,  topic.getName(), getCreationDate().toString(), user.getName()}
        );
    }
}
