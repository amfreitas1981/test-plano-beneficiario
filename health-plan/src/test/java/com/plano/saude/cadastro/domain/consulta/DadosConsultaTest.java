package com.plano.saude.cadastro.domain.consulta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.plano.saude.cadastro.util.DadosCadastroBeneficiarioCreator.createDadosCadastroBeneficiarioToBeSaved;
import static com.plano.saude.cadastro.util.DadosCadastroMedicoCreator.createDadosCadastroMedicoToBeSaved;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DadosConsultaTest {

    @Test
    @DisplayName("Teste para mapear retorno sucesso")
    void testDadosConsultaSucesso(){
        DadosConsulta dadosConsulta = new DadosConsulta(
                1L,
                createDadosCadastroMedicoToBeSaved(),
                createDadosCadastroBeneficiarioToBeSaved(),
                LocalDateTime.now(),
                MotivoCancelamento.BENEFICIARIO_DESISTIU
        );

        assertNotNull(dadosConsulta);
    }

}