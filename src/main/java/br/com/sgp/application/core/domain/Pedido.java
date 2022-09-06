package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Pedido {

    private Long id;
    private Date data;
    private Double valor;
    private StatusPedido status;
    private StatusPagamento situacaoPagamento;
    private Temporada temporada;
    private Aluno aluno;
    private List<Produto> produtos;
}
