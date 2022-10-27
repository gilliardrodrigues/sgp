package br.com.sgp.adapters.inbound.request;

import br.com.sgp.application.core.domain.TipoProduto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class FornecedorRequest {

    @NotBlank(message = "Campo obrigatório não preenchido!")
    private String razaoSocial;

    @CNPJ
    @NotBlank(message = "Campo obrigatório não preenchido!")
    private String CNPJ;

    @Email
    @NotBlank
    private String email;

    @NotNull(message = "Campo obrigatório não preenchido!")
    private Integer tempoEntregaEmDias;

    List<TipoProduto> produtosOferecidos;
}
