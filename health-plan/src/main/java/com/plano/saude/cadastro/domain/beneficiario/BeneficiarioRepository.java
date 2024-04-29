package com.plano.saude.cadastro.domain.beneficiario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {
    Page<Beneficiario> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            select b.ativo
            from Beneficiario b
            where
            b.id = :id
            """)
    Boolean findAtivoById(Long id);
}
