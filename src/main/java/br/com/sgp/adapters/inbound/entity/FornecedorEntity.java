package br.com.sgp.adapters.inbound.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
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

    public ObservacaoEntity cadastrarObservacao(String comentario) {

        ObservacaoEntity observacao = new ObservacaoEntity();
        observacao.setComentario(comentario);
        observacao.setData(OffsetDateTime.now());
        observacao.setFornecedor(this);

        this.getObservacoes().add(observacao);

        return observacao;
    }
}
