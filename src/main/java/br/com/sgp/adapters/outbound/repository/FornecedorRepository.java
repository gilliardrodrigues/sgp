package br.com.sgp.adapters.outbound.repository;

import br.com.sgp.adapters.inbound.entity.FornecedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FornecedorRepository extends JpaRepository<FornecedorEntity, Long> {

    public Boolean existsByCNPJ(String CNPJ);
    public List<FornecedorEntity> findByCNPJStartingWithIgnoreCase(String CNPJ);
    public List<FornecedorEntity> findByRazaoSocialStartingWithIgnoreCase(String razaoSocial);
}
