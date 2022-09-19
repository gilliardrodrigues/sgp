package br.com.sgp.adapters.inbound.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CanecaRequest {

    private Double valor;
    private Boolean prontaEntrega;
    private String modelo;
}