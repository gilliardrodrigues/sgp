package br.com.sgp.adapters.inbound.request;

import java.math.BigDecimal;
import java.util.List;

import br.com.sgp.application.core.domain.Aluno;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoRequest {
    private Aluno aluno;
}
