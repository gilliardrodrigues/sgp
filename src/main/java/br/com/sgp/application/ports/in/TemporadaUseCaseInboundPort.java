package br.com.sgp.application.ports.in;

import br.com.sgp.application.core.domain.Temporada;
import br.com.sgp.application.core.domain.TipoProduto;
import br.com.sgp.application.core.exception.NegocioException;

import java.util.List;

public interface TemporadaUseCaseInboundPort {

    Temporada salvar(Temporada temporada) throws NegocioException;
    void excluir(Long id);
    Temporada encerrarTemporada(Long id);
    Temporada adicionarProdutos(Temporada temporada, List<TipoProduto> produtos);
    Temporada buscarPeloId(Long id);
    List<Temporada> buscarTodas();

    Temporada alterarTemporada(Temporada temporada);

    boolean temporadaExiste(Long id);
}
