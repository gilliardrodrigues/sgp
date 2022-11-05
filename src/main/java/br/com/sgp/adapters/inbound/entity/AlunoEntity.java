package br.com.sgp.adapters.inbound.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "ALUNO")
public class AlunoEntity extends PessoaEntity {

    @Column(name = "LOGIN_DCC")
    private String loginDCC;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aluno")
    List<PedidoEntity> pedidos;
}
