package br.com.sgp.adapters.inbound.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "CANECA")
public class CanecaEntity extends ProdutoEntity {

    @Column(name = "MODELO_CANECA")
    private String modelo;
}
