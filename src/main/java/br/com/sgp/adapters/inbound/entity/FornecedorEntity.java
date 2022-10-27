package br.com.sgp.adapters.inbound.entity;


import br.com.sgp.application.core.domain.TipoProduto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "FORNECEDOR")
public class FornecedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "RAZAO_SOCIAL")
    private String razaoSocial;

    @Column(name = "CNPJ")
    private String CNPJ;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TEMPO_ENTREGA_EM_DIAS")
    private Integer tempoEntregaEmDias;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fornecedor")
    private List<ObservacaoEntity> observacoes = new ArrayList<>();

    @ElementCollection(targetClass = TipoProduto.class)
    @JoinTable(name = "FORNECEDOR_PRODUTO_OFERECIDO", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "PRODUTO_OFERECIDO", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<TipoProduto> produtosOferecidos;

}
