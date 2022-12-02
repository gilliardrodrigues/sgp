package br.com.sgp.adapters.outbound.repository;

import br.com.sgp.adapters.inbound.entity.TemporadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemporadaRepository extends JpaRepository<TemporadaEntity, Long> {
    

    Optional<TemporadaEntity> findByDataFimIsNull();
    List<TemporadaEntity> findAllByDataFimIsNull();
}
