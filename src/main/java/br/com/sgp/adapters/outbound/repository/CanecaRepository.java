package br.com.sgp.adapters.outbound.repository;


import br.com.sgp.adapters.inbound.entity.CanecaEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CanecaRepository extends JpaRepository<CanecaEntity, Long> {

}
