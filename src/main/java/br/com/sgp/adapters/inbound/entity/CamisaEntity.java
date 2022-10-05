package br.com.sgp.adapters.inbound.entity;

import br.com.sgp.application.core.domain.CorCamisa;
import br.com.sgp.application.core.domain.Curso;
import br.com.sgp.application.core.domain.TamanhoCamisa;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "CAMISA")
public class CamisaEntity extends ProdutoEntity {

    @Column(name = "CURSO")
    @Enumerated(value = EnumType.STRING)
    private Curso curso;

    @Column(name = "TAMANHO_CAMISA")
    @Enumerated(value = EnumType.STRING)
    private TamanhoCamisa tamanho;

    @Column(name = "COR_CAMISA")
    @Enumerated(value = EnumType.STRING)
    private CorCamisa cor;

}
