package uz.itransition.collectin.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Field extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private int type;

    @ManyToOne
    private Collection collection;

}
