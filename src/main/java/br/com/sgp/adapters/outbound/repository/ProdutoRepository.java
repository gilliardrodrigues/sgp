package br.com.sgp.adapters.outbound.repository;

import br.com.sgp.adapters.inbound.entity.CamisaEntity;
import br.com.sgp.adapters.inbound.entity.CanecaEntity;
import br.com.sgp.adapters.inbound.entity.ProdutoEntity;
import br.com.sgp.adapters.inbound.entity.TiranteEntity;
import br.com.sgp.application.core.domain.CorCamisa;
import br.com.sgp.application.core.domain.Curso;
import br.com.sgp.application.core.domain.TamanhoCamisa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    List<ProdutoEntity> findByPedidoId(Long pedidoId);
    @Query("SELECT c FROM CamisaEntity c WHERE c.pedido.id = :pedidoId")
    List<CamisaEntity> findAllCamisasByPedidoId(@Param("pedidoId") Long pedidoId);
    @Query("SELECT c FROM CanecaEntity c WHERE c.pedido.id = :pedidoId")
    List<CanecaEntity> findAllCanecasByPedidoId(@Param("pedidoId") Long pedidoId);
    @Query("SELECT t FROM TiranteEntity t WHERE t.pedido.id = :pedidoId")
    List<TiranteEntity> findAllTirantesByPedidoId(@Param("pedidoId") Long pedidoId);
    @Query("SELECT c FROM CamisaEntity c WHERE c.prontaEntrega = True AND c.pedido IS NULL")
    List<CamisaEntity> buscarCamisasDoInventario();
    @Query("SELECT c FROM CanecaEntity c WHERE c.prontaEntrega = True AND c.pedido IS NULL")
    List<CanecaEntity> buscarCanecasDoInventario();
    @Query("SELECT t FROM TiranteEntity t WHERE t.prontaEntrega = True AND t.pedido IS NULL")
    List<TiranteEntity> buscarTirantesDoInventario();
    @Query("SELECT c FROM CanecaEntity c WHERE c.modelo = :modelo")
    List<CanecaEntity> findCanecaByModelo(@Param("modelo") String modelo);
    @Query("SELECT c FROM TiranteEntity c WHERE c.modelo = :modelo")
    List<TiranteEntity> findTiranteByModelo(@Param("modelo") String modelo);
    List<CamisaEntity> findByCor(CorCamisa corCamisa);
    List<CamisaEntity> findByTamanho(TamanhoCamisa tamanhoCamisa);
    List<CamisaEntity> findByCurso(Curso curso);
    List<CamisaEntity> findByCorAndTamanhoAndCurso(CorCamisa corCamisa, TamanhoCamisa tamanhoCamisa, Curso curso);
    List<CamisaEntity> findByCorAndTamanho(CorCamisa corCamisa, TamanhoCamisa tamanhoCamisa);
    List<CamisaEntity> findByCorAndCurso(CorCamisa corCamisa, Curso curso);
    List<CamisaEntity> findByTamanhoAndCurso(TamanhoCamisa tamanhoCamisa, Curso curso);
}
