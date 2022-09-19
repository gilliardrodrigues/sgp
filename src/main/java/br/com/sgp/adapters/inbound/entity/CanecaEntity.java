package br.com.sgp.adapters.inbound.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "Caneca")
public class CanecaEntity extends ProdutoEntity{

    @Column(name = "MODELO")
    private String modelo;
}

