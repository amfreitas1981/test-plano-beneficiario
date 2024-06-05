package com.plano.saude.cadastro.util;

import com.plano.saude.cadastro.domain.endereco.DadosEndereco;
import com.plano.saude.cadastro.domain.medico.DadosCadastroMedico;
import com.plano.saude.cadastro.domain.medico.Especialidade;

public class DadosCadastroMedicoCreator {

    static DadosEndereco dadosEndereco = new DadosEndereco(
            "Rua xpto",
            "Bairro",
            "12345-789",
            "Cidade",
            "SP",
            "Casa",
            "789"
    );

    public static DadosCadastroMedico createDadosCadastroMedicoToBeSaved(){
        return new DadosCadastroMedico(
                "Nome Cadastro Medico",
                "email@email.com",
                "654321",
                "1147859635",
                Especialidade.DERMATOLOGIA,
                dadosEndereco
        );
    }
    public static DadosCadastroMedico createDadosCadastroMedicoValid(){
        return new DadosCadastroMedico(
                "Nome Cadastro Medico",
                "email@email.com",
                "653421",
                "1147859635",
                Especialidade.DERMATOLOGIA,
                dadosEndereco
        );
    }
    public static DadosCadastroMedico createDadosCadastroMedicoValidUpdate(){
        return new DadosCadastroMedico(
                "Nome Cadastro Medico 2",
                "email@email.com",
                "653421",
                "1147859635",
                Especialidade.ORTOPEDIA,
                dadosEndereco
        );
    }
}
