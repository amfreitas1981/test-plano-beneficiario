package com.plano.saude.cadastro.domain.beneficiario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {
    Page<Beneficiario> findAllByAtivoTrue(Pageable paginacao);
}
