package com.plano.saude.cadastro.util;

import com.plano.saude.cadastro.domain.consulta.DadosConsulta;
import com.plano.saude.cadastro.domain.consulta.MotivoCancelamento;
import com.plano.saude.cadastro.domain.endereco.DadosEndereco;
import com.plano.saude.cadastro.domain.medico.DadosCadastroMedico;
import com.plano.saude.cadastro.domain.medico.Especialidade;

import java.time.LocalDateTime;

import static com.plano.saude.cadastro.util.DadosCadastroBeneficiarioCreator.createDadosCadastroBeneficiarioToBeSaved;

public class DadosConsultaCreator {

    static DadosEndereco dadosEndereco = new DadosEndereco(
            "Rua xpto",
            "Bairro",
            "12345-789",
            "Cidade",
            "SP",
            "Casa",
            "789"
    );

    static DadosCadastroMedico dadosCadastroMedico = new DadosCadastroMedico(
            "Nome Cadastro Medico",
            "email@email.com",
            "654321",
            "1147859635",
            Especialidade.DERMATOLOGIA,
            dadosEndereco
    );

    public static DadosConsulta createDadosConsultaToBeSaved(){
        return new DadosConsulta(
                null,
                dadosCadastroMedico,
                createDadosCadastroBeneficiarioToBeSaved(),
                LocalDateTime.now(),
                MotivoCancelamento.MEDICO_CANCELOU
        );
    }
    public static DadosConsulta createDadosConsultaValid(){
        return new DadosConsulta(
                1L,
                dadosCadastroMedico,
                createDadosCadastroBeneficiarioToBeSaved(),
                LocalDateTime.now(),
                MotivoCancelamento.MEDICO_CANCELOU
        );
    }
    public static DadosConsulta createDadosConsultaValidUpdate(){
        return new DadosConsulta(
                1L,
                dadosCadastroMedico,
                createDadosCadastroBeneficiarioToBeSaved(),
                LocalDateTime.of(2024, 02, 22, 10, 00),
                MotivoCancelamento.MEDICO_CANCELOU
        );
    }
}
