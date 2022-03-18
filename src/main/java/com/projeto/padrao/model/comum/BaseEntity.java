package com.projeto.padrao.model.comum;

import com.projeto.padrao.utils.DateTimeUtils;
import com.projeto.padrao.utils.UserContextUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Transient
    private Date criadoEmSalvo;

    @Transient
    private Integer criadoPorSalvo;

    @PostLoad
    private void saveState(){
        setCriadoEmSalvo(getCriadoEm());
        setCriadoPorSalvo(getCriadoPor());
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "criado_em", nullable = false)
    private Date criadoEm;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "atualizado_em", nullable = false)
    private Date atualizadoEm;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "apagado_em")
    private Date apagadoEm;

    private Integer criadoPor;
    private Integer atualizadoPor;

    @PrePersist
    private void prePersist() {
        if (ObjectUtils.isEmpty(this.criadoEm)) {
            this.criadoEm = DateTimeUtils.newDate();
        }
        if (ObjectUtils.isEmpty(this.atualizadoEm)) {
            this.atualizadoEm = DateTimeUtils.newDate();
        }
        if (ObjectUtils.isEmpty(this.criadoPor)) {
            this.criadoPor = UserContextUtil.getCurrentUserId();
        }
        if (ObjectUtils.isEmpty(this.atualizadoPor)) {
            this.atualizadoPor = UserContextUtil.getCurrentUserId();
        }
    }

    @PreUpdate
    private void preUpdate() {
        if (ObjectUtils.isEmpty(this.atualizadoEm)) {
            this.atualizadoEm = DateTimeUtils.newDate();
        }
        if (ObjectUtils.isEmpty(this.atualizadoPor)) {
            this.atualizadoPor = UserContextUtil.getCurrentUserId();
        }
        if (ObjectUtils.isEmpty(this.criadoEm)) {
            this.criadoEm = this.criadoEmSalvo;
        }
        if (ObjectUtils.isEmpty(this.criadoPor)) {
            this.criadoPor = this.criadoPorSalvo;
        }
    }

    @PreRemove
    private void preRemove() {
        if (ObjectUtils.isEmpty(this.apagadoEm)) {
            this.apagadoEm = DateTimeUtils.newDate();
        }
        if (ObjectUtils.isEmpty(this.atualizadoPor)) {
            this.atualizadoPor = UserContextUtil.getCurrentUserId();
        }
    }
}
