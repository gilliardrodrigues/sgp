package br.com.sgp.adapters.inbound.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "FORNECEDOR")
public class FornecedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "RAZAO_SOCIAL")
    private String razaoSocial;

    @Column(name = "CNPJ")
    private String CNPJ;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TEMPO_ENTREGA_EM_DIAS")
    private Integer tempoEntregaEmDias;
}
