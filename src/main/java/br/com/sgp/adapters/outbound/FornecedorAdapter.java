package br.com.sgp.adapters.outbound;

import br.com.sgp.adapters.inbound.mapper.FornecedorMapper;
import br.com.sgp.adapters.outbound.repository.FornecedorRepository;
import br.com.sgp.application.core.domain.Fornecedor;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.out.FornecedorUseCaseOutboundPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@AllArgsConstructor
public class FornecedorAdapter implements FornecedorUseCaseOutboundPort {

    private final FornecedorRepository repository;
    private final FornecedorMapper mapper;

    public Boolean fornecedorExiste(Long id) {

        return repository.existsById(id);
    }

    @Override
    @Transactional
    public Fornecedor salvar(Fornecedor fornecedor) throws NegocioException {

        var cnpjEmUso = repository.existsByCNPJ(fornecedor.getCNPJ());
        if(cnpjEmUso)
            throw new NegocioException("Já existe um fornecedor cadastrado com esse CNPJ!");

        var fornecedorEntity = mapper.domainToEntity(fornecedor);
        return mapper.entityToDomain(repository.save(fornecedorEntity));
    }

    @Override
    public List<Fornecedor> buscarTodos() {

        var fornecedores = repository.findAll();
        return mapper.entityListToDomainList(fornecedores);
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
        return mapper.entityToDomain(fornecedorEntity);
    }

    @Override
    public List<Fornecedor> buscarPeloNome(String nome) {

        var fornecedores = repository.findByRazaoSocialStartingWithIgnoreCase(nome);
        return mapper.entityListToDomainList(fornecedores);
    }

    @Override
    public List<Fornecedor> buscarPeloCNPJ(String CNPJ) {

        var fornecedores = repository.findByCNPJStartingWithIgnoreCase(CNPJ);
        return mapper.entityListToDomainList(fornecedores);
    }
}
