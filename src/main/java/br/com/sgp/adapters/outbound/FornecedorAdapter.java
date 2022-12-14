package br.com.sgp.adapters.outbound;

import br.com.sgp.adapters.inbound.entity.FornecedorEntity;
import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.adapters.outbound.repository.FornecedorRepository;
import br.com.sgp.application.core.domain.Fornecedor;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.out.FornecedorUseCaseOutboundPort;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@AllArgsConstructor
public class FornecedorAdapter implements FornecedorUseCaseOutboundPort {

    private final FornecedorRepository repository;
    private final GenericMapper mapper;

    public Boolean fornecedorExiste(Long id) {

        return repository.existsById(id);
    }

    @Override
    public Boolean existePeloCNPJ(String CNPJ) {

        return repository.existsByCNPJ(CNPJ);
    }

    @Override
    @Transactional
    public Fornecedor salvar(Fornecedor fornecedor) throws NegocioException {

        var fornecedorEntity = mapper.mapTo(fornecedor, FornecedorEntity.class);
        var fornecedorSalvo = repository.save(fornecedorEntity);
        return mapper.mapTo(fornecedorSalvo, Fornecedor.class);
    }

    @Override
    public List<Fornecedor> buscarTodos() {

        var fornecedores = repository.findAll();
        return mapper.mapToList(fornecedores, new TypeToken<List<Fornecedor>>() {}.getType());
    }

    @Override
    @Transactional
    public void excluir(Long id) {

        repository.deleteById(id);
    }

    @Override
    public Fornecedor buscarPeloId(Long id) {


        var fornecedorEntity = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Fornecedor não encontrado!"));
        return mapper.mapTo(fornecedorEntity, Fornecedor.class);
    }

    @Override
    public List<Fornecedor> buscarPeloNome(String nome) {

        var fornecedores = repository.findByRazaoSocialStartingWithIgnoreCase(nome);
        return mapper.mapToList(fornecedores, new TypeToken<List<Fornecedor>>() {}.getType());
    }

    @Override
    public List<Fornecedor> buscarPeloCNPJ(String CNPJ) {

        var fornecedores = repository.findByCNPJStartingWithIgnoreCase(CNPJ);
        return mapper.mapToList(fornecedores, new TypeToken<List<Fornecedor>>() {}.getType());
    }
}
