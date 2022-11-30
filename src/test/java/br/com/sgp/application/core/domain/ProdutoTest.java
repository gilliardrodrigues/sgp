package br.com.sgp.application.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.List;

public class ProdutoTest {
    @Test
    void calcularPrevisaoDeEntrega() {
        Produto produto = new Caneca();

        var fornecedor = new Fornecedor(1L, "razaoSocial", "123456789", "email", 20);
        var previsao = produto
                .calcularPrevisaoDeEntrega(List.of(fornecedor));
        assertEquals(null, previsao);

        fornecedor.setProdutosOferecidos(List.of(TipoProduto.CANECA));
        previsao = produto
                .calcularPrevisaoDeEntrega(List.of(fornecedor));

        OffsetDateTime dataAtual = OffsetDateTime.now().plusDays(20).minusMinutes(1);
        assertTrue(previsao.isAfter(dataAtual));
    }
}
