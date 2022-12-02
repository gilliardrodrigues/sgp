package br.com.sgp.application.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.OffsetDateTime;

public class PedidoTest {

    @Test
    void testIncrementarPedido() {
        Pedido pedido = new Pedido();

        assertEquals(0, pedido.getValor());

        pedido.incrementarValor(10);
        assertEquals(10, pedido.getValor());
    }

    @Test
    void testPagarIntegralmente() {
        Pedido pedido = new Pedido();

        pedido.incrementarValor(10);
        pedido.setValorPago(10);
        assertEquals(StatusPagamento.INTEGRALMENTE_PAGO, pedido.getStatusPagamento());
        assertEquals(StatusPedido.CONFIRMADO, pedido.getSituacao());
    }

    @Test
    void testPagarParcialmente() {
        Pedido pedido = new Pedido();

        pedido.incrementarValor(10);
        pedido.setValorPago(5);
        assertEquals(StatusPagamento.PARCIALMENTE_PAGO, pedido.getStatusPagamento());
        assertEquals(StatusPedido.CONFIRMADO, pedido.getSituacao());
    }

    @Test
    void testNaoPagar() {
        Pedido pedido = new Pedido();

        pedido.incrementarValor(10);
        pedido.setValorPago(0);
        assertEquals(StatusPagamento.NAO_PAGO, pedido.getStatusPagamento());
        assertEquals(StatusPedido.AGUARDANDO_PAGAMENTO, pedido.getSituacao());
    }

    @Test
    void testCalcularPrevisaoDeEntrega() {
        Pedido pedido = new Pedido();

        var date = OffsetDateTime.now();
        pedido.addPrevisaoDeEntrega(date);
        assertEquals(date, pedido.getPrevisaoDeEntrega());

        var date2 = OffsetDateTime.now().plusDays(1);
        pedido.addPrevisaoDeEntrega(date2);
        pedido.addPrevisaoDeEntrega(date);
        assertEquals(date2, pedido.getPrevisaoDeEntrega());
    }
}
