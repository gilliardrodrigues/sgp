package br.com.sgp.adapters.outbound.repository;

import br.com.sgp.adapters.inbound.entity.AdministradorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministradorRepository extends JpaRepository<AdministradorEntity, Long> {

    Optional<AdministradorEntity> findByUsernameAndPassword(String username, String password);
}
