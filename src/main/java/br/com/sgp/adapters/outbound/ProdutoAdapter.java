package br.com.sgp.adapters.outbound;

import br.com.sgp.adapters.inbound.entity.ProdutoEntity;
import br.com.sgp.adapters.inbound.entity.CamisaEntity;
import br.com.sgp.adapters.inbound.entity.CanecaEntity;
import br.com.sgp.adapters.inbound.entity.TiranteEntity;
import br.com.sgp.adapters.inbound.mapper.GenericMapper;
import br.com.sgp.adapters.outbound.repository.ProdutoRepository;
import br.com.sgp.application.core.domain.*;
import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
import br.com.sgp.application.core.exception.NegocioException;
import br.com.sgp.application.ports.out.ProdutoUseCaseOutboundPort;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@AllArgsConstructor
public class ProdutoAdapter implements ProdutoUseCaseOutboundPort {

    private final ProdutoRepository repository;
    private final GenericMapper mapper;

    public Boolean produtoExiste(Long id) {

        return repository.existsById(id);
    }

    @Override
    @Transactional
    public Camisa salvarCamisa(Camisa camisa) throws NegocioException {

        var camisaEntity = mapper.mapTo(camisa, CamisaEntity.class);
        var camisaSalva = repository.save(camisaEntity);
        return mapper.mapTo(camisaSalva, Camisa.class);
    }

    @Override
    @Transactional
    public Caneca salvarCaneca(Caneca caneca) throws NegocioException {

        var canecaEntity = mapper.mapTo(caneca, CanecaEntity.class);
        var canecaSalva = repository.save(canecaEntity);
        return mapper.mapTo(canecaSalva, Caneca.class);
    }

    @Override
    @Transactional
    public Tirante salvarTirante(Tirante tirante) throws NegocioException {

        var tiranteEntity = mapper.mapTo(tirante, TiranteEntity.class);
        var tiranteSalvo = repository.save(tiranteEntity);
        return mapper.mapTo(tiranteSalvo, Tirante.class);
    }

    @Override
    public List<Produto> buscarTodos() {

        var produtos = repository.findAll();
        return mapper.mapToList(produtos, new TypeToken<List<Produto>>() {
        }.getType());
    }

    @Override
    public List<Camisa> buscarTodasCamisas() {

        var camisas = repository.findAllCamisas();
        return mapper.mapToList(camisas, new TypeToken<List<Camisa>>() {
        }.getType());
    }

    @Override
    public List<Caneca> buscarTodasCanecas() {

        var canecas = repository.findAllCanecas();
        return mapper.mapToList(canecas, new TypeToken<List<Caneca>>() {
        }.getType());
    }

    @Override
    public List<Tirante> buscarTodosTirantes() {

        var tirantes = repository.findAllTirantes();
        return mapper.mapToList(tirantes, new TypeToken<List<Tirante>>() {
        }.getType());
    }

    @Override
    @Transactional
    public void excluir(Long id) {

        repository.deleteById(id);
    }

    @Override
    public Produto buscarPeloId(Long id) throws Throwable {

        var produto = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado!"));
        return mapper.mapTo(produto, Produto.class);
    }


     @Override
     public List<Produto> buscarPeloIdPedido(Long idPedido) throws NegocioException {

        var produtos = repository.buscarPeloIdPedido(idPedido);
         return mapper.mapToList(produtos, new TypeToken<List<Produto>>() {}.getType());
     }
    @Override
    public List<Produto> buscarInventario() {

        var inventario = repository.buscarInventario();
        return mapper.mapToList(inventario, new TypeToken<List<Produto>>() {}.getType());
    }

    @Override
    public List<Tirante> buscarTirantePeloModelo(String modelo) {

        var tirantes = repository.findTiranteByModelo(modelo);
        return mapper.mapToList(tirantes, new TypeToken<List<Tirante>>() {
        }.getType());
    }

    @Override
    public List<Caneca> buscarCanecaPeloModelo(String modelo) {

        var canecas = repository.findCanecaByModelo(modelo);
        return mapper.mapToList(canecas, new TypeToken<List<Caneca>>() {
        }.getType());
    }

    @Override
    public List<Camisa> buscarCamisas(String cor, String tamanho, String curso) {
        try {
            if (cor != null && tamanho != null && curso != null) {
                var camisas = repository.findByCorAndTamanhoAndCurso(CorCamisa.valueOf(cor.toUpperCase()),
                        TamanhoCamisa.valueOf(tamanho.toUpperCase()),
                        Curso.valueOf(curso.toUpperCase()));
                return mapper.mapToList(camisas, new TypeToken<List<Camisa>>() {
                }.getType());
            } else if (cor != null && tamanho != null) {
                var camisas = repository.findByCorAndTamanho(CorCamisa.valueOf(cor.toUpperCase()),
                        TamanhoCamisa.valueOf(tamanho.toUpperCase()));
                return mapper.mapToList(camisas, new TypeToken<List<Camisa>>() {
                }.getType());
            } else if (tamanho != null && curso != null) {
                var camisas = repository.findByTamanhoAndCurso(TamanhoCamisa.valueOf(tamanho.toUpperCase()),
                        Curso.valueOf(curso.toUpperCase()));
                return mapper.mapToList(camisas, new TypeToken<List<Camisa>>() {
                }.getType());
            } else if (cor != null && curso != null) {
                var camisas = repository.findByCorAndCurso(CorCamisa.valueOf(cor.toUpperCase()),
                        Curso.valueOf(curso.toUpperCase()));
                return mapper.mapToList(camisas, new TypeToken<List<Camisa>>() {
                }.getType());
            } else if (cor != null) {
                var camisas = repository.findByCor(CorCamisa.valueOf(cor.toUpperCase()));
                return mapper.mapToList(camisas, new TypeToken<List<Camisa>>() {
                }.getType());
            } else if (tamanho != null) {
                var camisas = repository.findByTamanho(TamanhoCamisa.valueOf(tamanho.toUpperCase()));
                return mapper.mapToList(camisas, new TypeToken<List<Camisa>>() {
                }.getType());
            } else if (curso != null) {
                var camisas = repository.findByCurso(Curso.valueOf(curso.toUpperCase()));
                return mapper.mapToList(camisas, new TypeToken<List<Camisa>>() {
                }.getType());
            } else {
                var camisas = repository.findAllCamisas();
                return mapper.mapToList(camisas, new TypeToken<List<Camisa>>() {
                }.getType());
            }
        } catch (Throwable e) {
            throw new EntidadeNaoEncontradaException("Camisa não encontrada.");
        }

    }
}
