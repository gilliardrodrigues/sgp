package br.com.sgp.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Fornecedor {

    private Long id;
    private String razaoSocial;
    private String CNPJ;
    private String email;
    private Integer tempoEntregaEmDias;
    private List<Observacao> observacoes;
    private List<TipoProduto> produtosOferecidos;

    public Fornecedor(Long id, String razaoSocial, String CNPJ, String email, Integer tempoEntregaEmDias) {
        this.id = id;
        this.razaoSocial = razaoSocial;
        this.CNPJ = CNPJ;
        this.email = email;
        this.tempoEntregaEmDias = tempoEntregaEmDias;
        this.observacoes = new ArrayList<>();
        this.produtosOferecidos = new ArrayList<>();
    }

    public Observacao cadastrarObservacao(String comentario) {

        Observacao observacao = new Observacao();
        observacao.setComentario(comentario);
        observacao.setData(OffsetDateTime.now());
        observacao.setFornecedor(this);

        this.getObservacoes().add(observacao);

        return observacao;
    }

}
