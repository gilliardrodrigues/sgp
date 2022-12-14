package br.com.sgp.adapters.inbound.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "OBSERVACAO")
@AllArgsConstructor
@NoArgsConstructor
public class ObservacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "COMENTARIO")
    private String comentario;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "DATA")
    private OffsetDateTime data;

    @ManyToOne
    @JoinColumn(name = "fornecedorId", nullable = false)
    @NotNull(message = "Campo obrigatório não preenchido!")
    private FornecedorEntity fornecedor;

    //@ManyToOne
    //@JoinColumn(name = "administradorId", nullable = false)
    //@NotNull(message = "Campo obrigatório não preenchido!")
    //private AdministradorEntity autor;
}
