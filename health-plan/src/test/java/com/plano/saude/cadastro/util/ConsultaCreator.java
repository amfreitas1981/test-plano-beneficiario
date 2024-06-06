package com.plano.saude.cadastro.util;

import com.plano.saude.cadastro.domain.consulta.Consulta;
import com.plano.saude.cadastro.domain.consulta.MotivoCancelamento;
import com.plano.saude.cadastro.domain.endereco.Endereco;
import com.plano.saude.cadastro.domain.medico.Especialidade;
import com.plano.saude.cadastro.domain.medico.Medico;

import java.time.LocalDateTime;

import static com.plano.saude.cadastro.util.BeneficiarioCreator.*;

public class ConsultaCreator {

    static Endereco endereco = new Endereco(
            "Rua",
            "Vila da Esperanca",
            "01000-000",
            "Cidade da Oportunidade",
            "TO",
            "Casa",
            "123"
    );

    public static Medico medico = new Medico(
            1L,
            "Nome Medico",
            "medico@email.com",
            "879146",
            "1142397813",
            Especialidade.CARDIOLOGIA,
            endereco,
            true
    );

    public static Consulta createConsultaToBeSaved(){
        return new Consulta(
                null,
                medico,
                createBeneficiarioToBeSaved(),
                LocalDateTime.now(),
                MotivoCancelamento.OUTROS
        );
    }

    public static Consulta createConsultaValid(){
        return new Consulta(
                1L,
                medico,
                createBeneficiarioValid(),
                LocalDateTime.now(),
                MotivoCancelamento.BENEFICIARIO_DESISTIU
        );
    }

    public static Consulta createConsultaValidUpdate(){
        return new Consulta(
                1L,
                medico,
                createBeneficiarioValidUpdate(),
                LocalDateTime.now(),
                MotivoCancelamento.MEDICO_CANCELOU
        );
    }
}
