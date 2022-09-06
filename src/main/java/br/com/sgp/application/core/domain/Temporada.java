package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Temporada {

    private Long id;
    private Date dataInicio;
    private Date dataFim;
    List<Pedido> pedidos;
}
