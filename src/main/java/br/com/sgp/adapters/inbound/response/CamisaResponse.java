package br.com.sgp.adapters.inbound.response;

import br.com.sgp.application.core.domain.CorCamisa;
import br.com.sgp.application.core.domain.Curso;
import br.com.sgp.application.core.domain.TamanhoCamisa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CamisaResponse extends ProdutoResponse {

    private Curso curso;
    private TamanhoCamisa tamanho;
    private CorCamisa cor;
}
