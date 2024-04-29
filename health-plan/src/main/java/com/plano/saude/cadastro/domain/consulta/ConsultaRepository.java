package com.plano.saude.cadastro.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    boolean existsByBeneficiarioIdAndDataBetween(Long idBeneficiario, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
    boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(Long idMedico, LocalDateTime data);
}
