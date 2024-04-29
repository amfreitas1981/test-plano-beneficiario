package com.plano.saude.cadastro.domain.consulta;

import com.plano.saude.cadastro.domain.medico.Especialidade;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        Long idMedico,
        @NotNull
        Long idBeneficiario,
        @NotNull
        @Future
        LocalDateTime data,
        Especialidade especialidade) {
}
