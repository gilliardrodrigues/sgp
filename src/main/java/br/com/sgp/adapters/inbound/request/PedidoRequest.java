package br.com.sgp.adapters.inbound.request;

import br.com.sgp.application.core.domain.Aluno;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PedidoRequest {

    private Aluno aluno;

    @NotNull(message = "Campo obrigatório não preenchido!")
    private int valorPago;
}
