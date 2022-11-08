package br.com.sgp.adapters.inbound.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class IdPedidoRequest {

    @NotNull(message = "Campo obrigatório não preenchido!")
    Long id;
}
