package com.plano.saude.cadastro.domain.consulta;

import com.plano.saude.cadastro.domain.beneficiario.DadosCadastroBeneficiario;
import com.plano.saude.cadastro.domain.medico.DadosCadastroMedico;

import java.time.LocalDateTime;

public record DadosConsulta(
        Long id,
        DadosCadastroMedico medico,
        DadosCadastroBeneficiario beneficiario,
        LocalDateTime data,
        MotivoCancelamento motivo
) {
}
