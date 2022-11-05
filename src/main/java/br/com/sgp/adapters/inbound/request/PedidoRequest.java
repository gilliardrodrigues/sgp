package br.com.sgp.adapters.inbound.request;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import br.com.sgp.application.core.domain.Aluno;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoRequest {

    private Aluno aluno;

    @NotNull(message = "Campo obrigatório não preenchido!")
    private BigDecimal valorPago;

    private List<ProdutoRequest> produtos;
}
