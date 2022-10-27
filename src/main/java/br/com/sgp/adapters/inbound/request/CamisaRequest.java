package br.com.sgp.adapters.inbound.request;

import br.com.sgp.application.core.domain.CorCamisa;
import br.com.sgp.application.core.domain.Curso;
import br.com.sgp.application.core.domain.TamanhoCamisa;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class CamisaRequest extends ProdutoRequest{

    @Enumerated(EnumType.STRING)
    private Curso curso;

    @Enumerated(EnumType.STRING)
    private TamanhoCamisa tamanho;

    @Enumerated(EnumType.STRING)
    private CorCamisa cor;
}
