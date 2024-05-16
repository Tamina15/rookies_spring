/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.model;
import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
public abstract class AuditEntity<P extends Serializable> extends IdEntity<P> implements Persistable<P> {

    @Column(name = "DATE_CREATED")
    @CreatedDate
    LocalDateTime dateCreated;

    @Column(name = "DATE_MODIFIED")
    @LastModifiedDate
    LocalDateTime dateModified;

    @Transient
    @Override
    public boolean isNew () {
        return null == getId();
    }
    
}