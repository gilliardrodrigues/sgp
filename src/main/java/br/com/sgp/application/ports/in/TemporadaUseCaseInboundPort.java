package br.com.sgp.application.ports.in;

import br.com.sgp.application.core.domain.Temporada;
import br.com.sgp.application.core.domain.TipoProduto;
import br.com.sgp.application.core.exception.NegocioException;

import java.util.HashMap;
import java.util.List;

public interface TemporadaUseCaseInboundPort {

    Temporada salvar(Temporada temporada) throws NegocioException;
    void excluir(Long id);
    Temporada encerrarTemporadaAtual();
    Temporada adicionarProdutos(Temporada temporada, HashMap<TipoProduto, Integer> produtos);
    Temporada buscarPeloId(Long id);
    Temporada buscarAtiva();
    List<Temporada> buscarTodas();
    Temporada alterarTemporada(Temporada temporada);
    boolean temporadaExiste(Long id);
    boolean existeTemporadaAtiva();
}
