package br.com.sgp.adapters.outbound.repository;

import br.com.sgp.adapters.inbound.entity.ObservacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservacaoRepository extends JpaRepository<ObservacaoEntity, Long> {


}
