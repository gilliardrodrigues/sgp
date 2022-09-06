package br.com.sgp.application.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Aluno extends Pessoa {

    private String loginDCC;
    private List<Pedido> pedidos;
}
