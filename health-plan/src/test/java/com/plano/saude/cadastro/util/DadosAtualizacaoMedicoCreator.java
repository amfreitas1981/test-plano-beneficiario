package com.plano.saude.cadastro.util;

import com.plano.saude.cadastro.domain.endereco.DadosEndereco;
import com.plano.saude.cadastro.domain.medico.DadosAtualizacaoMedico;

public class DadosAtualizacaoMedicoCreator {

    public static DadosEndereco dadosEndereco = new DadosEndereco(
            "Rua Morada do Beneficiario",
            "Bairro",
            "12345-789",
            "Cidade",
            "SP",
            "Casa",
            "789"
    );

    public static DadosAtualizacaoMedico createDadosAtualizacaoMedicoToBeSaved(){
        return new DadosAtualizacaoMedico(
                null,
                "Nome Medico Atualizado",
                "1174588639",
                dadosEndereco
        );
    }

    public static DadosAtualizacaoMedico createDadosAtualizacaoMedicoValid(){
        return new DadosAtualizacaoMedico(
                1L,
                "Nome Medico Atualizado",
                "1174588639",
                dadosEndereco
        );
    }

    public static DadosAtualizacaoMedico createDadosAtualizacaoMedicoValidUpdate(){
        return new DadosAtualizacaoMedico(
                1L,
                "Nome Medico Atualizado 2",
                "1174588639",
                dadosEndereco
        );
    }
}
