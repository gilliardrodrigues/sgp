package br.com.sgp.adapters.outbound;

import br.com.sgp.adapters.inbound.entity.TemporadaEntity;
import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.adapters.outbound.repository.TemporadaRepository;
import br.com.sgp.application.core.domain.Temporada;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.out.TemporadaUseCaseOutboundPort;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@AllArgsConstructor
public class TemporadaAdapter implements TemporadaUseCaseOutboundPort {

    private final TemporadaRepository repository;
    private final GenericMapper mapper;

    @Override
    @Transactional
    public Temporada salvar(Temporada temporada) throws NegocioException {

        var temporadaEntity = mapper.mapTo(temporada, TemporadaEntity.class);
        var temporadaSalva = repository.save(temporadaEntity);
        return mapper.mapTo(temporadaSalva, Temporada.class);
    }

    @Override
    @Transactional
    public void excluir(Long id) {

        repository.deleteById(id);
    }

    @Override
    public Temporada buscarPeloId(Long id) {

        var temporadaEntity = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Temporada não encontrada!"));
        return mapper.mapTo(temporadaEntity, Temporada.class);
    }

    @Override
    public List<Temporada> buscarTodas() {

        var temporadas = repository.findAll();
        return mapper.mapToList(temporadas, new TypeToken<List<Temporada>>() {}.getType());
    }

    @Override
    public boolean temporadaExiste(Long id) {

        return repository.existsById(id);
    }

    @Override
    public Temporada buscarAtiva() {
        var temporadaEntity = repository.findByDataFimIsNull();
        return mapper.mapTo(temporadaEntity, Temporada.class);
    }

    
}
