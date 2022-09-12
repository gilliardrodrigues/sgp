package br.com.sgp.adapters.inbound.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FornecedorRequest {

    @NotBlank(message = "Campo obrigatório não preenchido!")
    private String razaoSocial;

    @NotBlank(message = "Campo obrigatório não preenchido!")
    private String CNPJ;

    @Email
    @NotBlank
    private String email;

    @NotNull(message = "Campo obrigatório não preenchido!")
    private Integer tempoEntregaEmDias;
}
