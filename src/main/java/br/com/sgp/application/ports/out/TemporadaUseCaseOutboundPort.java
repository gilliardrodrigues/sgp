package br.com.sgp.application.ports.out;

import br.com.sgp.application.core.domain.Temporada;

import java.util.List;

public interface TemporadaUseCaseOutboundPort {

    Temporada salvar(Temporada temporada);
    void excluir(Long id);
    Temporada buscarPeloId(Long id);
    List<Temporada> buscarTodas();
    boolean temporadaExiste(Long id);
    Temporada buscarAtiva();
    boolean existeTemporadaAtiva();
}
