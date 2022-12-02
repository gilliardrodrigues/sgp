package br.com.sgp.application.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FornecedorTest {

    @Test
    void testCadastroDeObservacao() {
        Fornecedor fornecedor = new Fornecedor(1L, "razaoSocial", "123456789", "email", 20);

        assertEquals(0, fornecedor.getObservacoes().size());
        fornecedor.cadastrarObservacao("Ótimo fornecedor");
        assertEquals(1, fornecedor.getObservacoes().size());
    }
}