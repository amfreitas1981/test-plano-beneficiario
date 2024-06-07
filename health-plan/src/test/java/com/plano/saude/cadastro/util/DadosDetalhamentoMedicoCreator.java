package com.plano.saude.cadastro.util;

import com.plano.saude.cadastro.domain.endereco.DadosEndereco;
import com.plano.saude.cadastro.domain.endereco.Endereco;
import com.plano.saude.cadastro.domain.medico.DadosAtualizacaoMedico;
import com.plano.saude.cadastro.domain.medico.DadosDetalhamentoMedico;
import com.plano.saude.cadastro.domain.medico.Especialidade;

public class DadosDetalhamentoMedicoCreator {

    public static DadosEndereco dadosEndereco = new DadosEndereco(
            "Rua Morada do Beneficiario",
            "Bairro",
            "12345-789",
            "Cidade",
            "SP",
            "Casa",
            "789"
    );

    public static DadosAtualizacaoMedico dadosAtualizacaoMedico = new DadosAtualizacaoMedico(
            1L,
            "Nome Medico Atualizado",
            "1174588639",
            dadosEndereco
    );

    // Considerando que sua classe Endereco tenha um construtor que receba um objeto DadosEndereco:
    static Endereco enderecoEsperado = new Endereco(dadosAtualizacaoMedico.endereco());

    public static DadosDetalhamentoMedico createDadosDetalhamentoMedicoToBeSaved(){
        return new DadosDetalhamentoMedico(
                null,
                dadosAtualizacaoMedico.nome(),
                "email@email.com",
                "456123",
                dadosAtualizacaoMedico.telefone(),
                Especialidade.DERMATOLOGIA,
                enderecoEsperado
        );
    }

    public static DadosDetalhamentoMedico createDadosDetalhamentoMedicoValid(){
        return new DadosDetalhamentoMedico(
                1L,
                dadosAtualizacaoMedico.nome(),
                "email@email.com",
                "456123",
                dadosAtualizacaoMedico.telefone(),
                Especialidade.DERMATOLOGIA,
                enderecoEsperado
        );
    }

    public static DadosDetalhamentoMedico createDadosDetalhamentoMedicoValidUpdate(){
        return new DadosDetalhamentoMedico(
                1L,
                dadosAtualizacaoMedico.nome(),
                "email@email2.com",
                "456123",
                dadosAtualizacaoMedico.telefone(),
                Especialidade.ORTOPEDIA,
                enderecoEsperado
        );
    }
}
