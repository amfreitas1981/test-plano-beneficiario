package com.plano.saude.cadastro.domain.consulta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.plano.saude.cadastro.util.BeneficiarioCreator.createBeneficiarioToBeSaved;
import static com.plano.saude.cadastro.util.ConsultaCreator.medico;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConsultaTest {

    @Test
    @DisplayName("Teste para mapear retorno sucesso")
    void testConsultaSucesso(){
        Consulta consulta = new Consulta(
                null,
                medico,
                createBeneficiarioToBeSaved(),
                LocalDateTime.now(),
                MotivoCancelamento.OUTROS
        );

        assertNotNull(consulta);
    }
}