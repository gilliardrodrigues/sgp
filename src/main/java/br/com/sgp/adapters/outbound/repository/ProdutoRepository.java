package br.com.sgp.adapters.outbound.repository;

import br.com.sgp.adapters.inbound.entity.CamisaEntity;
import br.com.sgp.adapters.inbound.entity.CanecaEntity;
import br.com.sgp.adapters.inbound.entity.ProdutoEntity;
import br.com.sgp.adapters.inbound.entity.TiranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository<E extends ProdutoEntity> extends JpaRepository<E, Long> {

    @Query("from CamisaEntity")
    List<CamisaEntity> findAllCamisas();

    @Query("from CanecaEntity")
    List<CanecaEntity> findAllCanecas();

    @Query("from TiranteEntity")
    List<TiranteEntity> findAllTirantes();

    //@Query("SELECT pr FROM ProdutoEntity pr WHERE pr.pedido.id = :idPedido")
    //List<ProdutoEntity> buscarPeloIdPedido(Long idPedido);

    @Query("SELECT pr FROM ProdutoEntity pr WHERE pr.prontaEntrega = True")
    List<ProdutoEntity> buscarInventario();

    @Query("SELECT c FROM CanecaEntity c WHERE c.modelo = :modelo")
    List<CanecaEntity> findCanecaByModelo(String modelo);

    @Query("SELECT c FROM TiranteEntity c WHERE c.modelo = :modelo")
    List<TiranteEntity> findTiranteByModelo(String modelo);

    List<CamisaEntity> findByCor(String corCamisa);

    List<CamisaEntity> findByTamanho(String tamanhoCamisa);

    List<CamisaEntity> findByCurso(String curso);
}
