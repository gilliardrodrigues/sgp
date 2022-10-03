package br.com.sgp.adapters.outbound;

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
    public Produto salvar(Produto produto) throws NegocioException {

        if(produto.getTipo().equals(TipoProduto.CAMISA)) {
            var camisaEntity = mapper.mapTo(produto, CamisaEntity.class);
            var camisaSalva = repository.save(camisaEntity);
            return mapper.mapTo(camisaSalva, Camisa.class);
        }
        else if (produto.getTipo().equals(TipoProduto.CANECA)) {
            var canecaEntity = mapper.mapTo(produto, CanecaEntity.class);
            var canecaSalva = repository.save(canecaEntity);
            return mapper.mapTo(canecaSalva, Caneca.class);
        }
        else {
            var tiranteEntity = mapper.mapTo(produto, TiranteEntity.class);
            var tiranteSalvo = repository.save(tiranteEntity);
            return mapper.mapTo(tiranteSalvo, Tirante.class);
        }
    }

    @Override
    public List<Produto> buscarTodos() {

       var produtos = repository.findAll();
       return mapper.mapToList(produtos, new TypeToken<List<Produto>>() {}.getType());
    }

    @Override
    public List<Camisa> buscarTodasCamisas() {

        var camisas = repository.findAllCamisas();
        return mapper.mapToList(camisas, new TypeToken<List<Camisa>>() {}.getType());
    }

    @Override
    public List<Caneca> buscarTodasCanecas() {

        var canecas = repository.findAllCanecas();
        return mapper.mapToList(canecas, new TypeToken<List<Caneca>>() {}.getType());
    }

    @Override
    public List<Tirante> buscarTodosTirantes() {

        var tirantes = repository.findAllTirantes();
        return mapper.mapToList(tirantes, new TypeToken<List<Tirante>>() {}.getType());
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

    /*
    @Override
    public List<Produto> buscarPeloIdPedido(Long idPedido) throws NegocioException {

        var produto = repository.buscarPeloIdPedido(idPedido);
    }
    */
    @Override
    public List<Produto> buscarInventario() {

        var inventario = repository.buscarInventario();
        return mapper.mapToList(inventario, new TypeToken<List<Produto>>() {}.getType());
    }

    @Override
    public List<Tirante> buscarTirantePeloModelo(String modelo) {

        var tirantes = repository.findTiranteByModelo(modelo);
        return mapper.mapToList(tirantes, new TypeToken<List<Tirante>>() {}.getType());
    }

    @Override
    public List<Caneca> buscarCanecaPeloModelo(String modelo) {

        var canecas = repository.findCanecaByModelo(modelo);
        return mapper.mapToList(canecas, new TypeToken<List<Caneca>>() {}.getType());
    }

    @Override
    public List<Camisa> buscarPelaCor(CorCamisa cor) {

        var camisas = repository.findByCor(cor.getDescricao());
        return mapper.mapToList(camisas, new TypeToken<List<Camisa>>() {}.getType());
    }

    @Override
    public List<Camisa> buscarPeloTamanho(TamanhoCamisa tamanho) {

        var camisas = repository.findByTamanho(tamanho.getDescricao());
        return mapper.mapToList(camisas, new TypeToken<List<Camisa>>() {}.getType());
    }

    @Override
    public List<Camisa> buscarPeloCurso(Curso curso) {

        var camisas = repository.findByCurso(curso.getDescricao());
        return mapper.mapToList(camisas, new TypeToken<List<Camisa>>() {}.getType());
    }
}
