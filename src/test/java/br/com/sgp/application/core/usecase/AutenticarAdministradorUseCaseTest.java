package br.com.sgp.application.core.usecase;

public class AutenticarAdministradorUseCaseTest {
    @Test
    void autenticarAdministrador() {
        Fornecedor fornecedor = new Fornecedor(1L, "razaoSocial", "123456789", "email", 20);

        assertEquals(0, fornecedor.getObservacoes().size());
        fornecedor.cadastrarObservacao("Ótimo fornecedor");
        assertEquals(1, fornecedor.getObservacoes().size());
    }
}
