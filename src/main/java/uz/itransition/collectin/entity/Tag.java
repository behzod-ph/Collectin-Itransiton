package uz.itransition.collectin.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Tag extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

}
