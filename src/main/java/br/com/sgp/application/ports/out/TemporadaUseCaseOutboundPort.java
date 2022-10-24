package br.com.sgp.application.ports.out;

import br.com.sgp.application.core.domain.Temporada;
import br.com.sgp.application.core.exception.NegocioException;

import java.util.List;

public interface TemporadaUseCaseOutboundPort {

    Temporada salvar(Temporada temporada) throws NegocioException;
    void excluir(Long id);
    Temporada buscarPeloId(Long id);
    List<Temporada> buscarTodas();
    boolean temporadaExiste(Long id);
}
