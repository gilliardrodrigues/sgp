package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Pedido {

    private Long id;
    private OffsetDateTime data;
    private Double valor;
    private StatusPedido situacao;
    private StatusPagamento statusPagamento;
    private Double valorPago;
    private Temporada temporada;
    private Aluno aluno;
    private List<Produto> produtos;
}
