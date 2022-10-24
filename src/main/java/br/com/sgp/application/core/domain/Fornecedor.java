package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class Fornecedor {

    private Long id;
    private String razaoSocial;
    private String CNPJ;
    private String email;
    private Integer tempoEntregaEmDias;
    private List<Observacao> observacoes;

    public Observacao cadastrarObservacao(String comentario) {

        Observacao observacao = new Observacao();
        observacao.setComentario(comentario);
        observacao.setData(OffsetDateTime.now());
        observacao.setFornecedor(this);

        this.getObservacoes().add(observacao);

        return observacao;
    }

}
