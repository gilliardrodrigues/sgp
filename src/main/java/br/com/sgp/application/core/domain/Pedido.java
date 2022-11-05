package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Pedido {

    public Pedido() {
        data = OffsetDateTime.now();
        valor = BigDecimal.ZERO;
        situacao = StatusPedido.AGUARDANDO_PAGAMENTO;
        statusPagamento = StatusPagamento.NAO_PAGO;
        valorPago = BigDecimal.ZERO;
        produtos = new ArrayList<Produto>();
    }

    private Long id;
    private OffsetDateTime data;
    private BigDecimal valor;
    private StatusPedido situacao;
    private StatusPagamento statusPagamento;
    private BigDecimal valorPago;
    private Temporada temporada;
    private Aluno aluno;
    private List<Produto> produtos;
    protected OffsetDateTime previsaoDeEntrega;

    public void setValorPago(BigDecimal valor) {
        this.valorPago = valor;

        if(this.valorPago == BigDecimal.ZERO) return;

        if(this.valorPago == this.valor)
            this.statusPagamento = StatusPagamento.INTEGRALMENTE_PAGO;
        else
            this.statusPagamento = StatusPagamento.PARCIALMENTE_PAGO;

        this.situacao = StatusPedido.CONFIRMADO;
    }

    // public Integer calcularDiasParaEntrega() {
    //     Integer maxDiasParaEntrega = 0;

    //     for(int i = 0; i < produtos.size(); i++) {
    //         Integer diasParaEntrega = produtos.get(i).calcularDiasParaEntrega();
    //         maxDiasParaEntrega = diasParaEntrega > maxDiasParaEntrega ? diasParaEntrega: maxDiasParaEntrega;
    //     }

    //     return maxDiasParaEntrega;
    // }

    public void adicionarProduto(Produto produto) {
        this.produtos.add(produto);
        this.valor = valor.add(produto.getValor());
        System.out.println(produto.getValor());
    }
}
