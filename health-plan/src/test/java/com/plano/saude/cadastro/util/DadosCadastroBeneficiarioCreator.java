package com.plano.saude.cadastro.util;

import com.plano.saude.cadastro.domain.beneficiario.DadosCadastroBeneficiario;
import com.plano.saude.cadastro.domain.documento.DadosCadastroDocumento;
import com.plano.saude.cadastro.domain.documento.TipoDocumento;
import com.plano.saude.cadastro.domain.endereco.DadosEndereco;

import java.time.LocalDate;
import java.util.Collections;

public class DadosCadastroBeneficiarioCreator {

    static DadosCadastroDocumento dadosCadastroDocumento = new DadosCadastroDocumento(
            TipoDocumento.CARTEIRA_NACIONAL_HABILITACAO,
            "123456789",
            LocalDate.parse("2010-03-18"),
            "Documento Teste",
            LocalDate.parse("2023-12-18"),
            LocalDate.parse("2023-12-18")
    );

    static DadosEndereco dadosEndereco = new DadosEndereco(
            "Rua xpto",
            "Bairro",
            "12345-789",
            "Cidade",
            "SP",
            "Casa",
            "789"
    );

    public static DadosCadastroBeneficiario createDadosCadastroBeneficiarioToBeSaved(){
        return new DadosCadastroBeneficiario(
                "Nome Beneficiario",
                "1132878987",
                LocalDate.parse("2000-01-14"),
                LocalDate.parse("2020-08-17"),
                LocalDate.parse("2020-09-17"),
                Collections.singletonList(dadosCadastroDocumento),
                dadosEndereco
        );
    }

    public static DadosCadastroBeneficiario createDadosCadastroBeneficiarioValid(){
        return new DadosCadastroBeneficiario(
                "Nome Beneficiario",
                "1132878988",
                LocalDate.parse("2000-01-14"),
                LocalDate.parse("2020-08-17"),
                LocalDate.parse("2020-09-17"),
                Collections.singletonList(dadosCadastroDocumento),
                dadosEndereco
        );
    }

    public static DadosCadastroBeneficiario createDadosCadastroBeneficiarioValidUpdate(){
        return new DadosCadastroBeneficiario(
                "Nome Beneficiario 2",
                "1132878987",
                LocalDate.parse("2000-01-14"),
                LocalDate.parse("2020-08-17"),
                LocalDate.parse("2020-09-17"),
                Collections.singletonList(dadosCadastroDocumento),
                dadosEndereco
        );
    }
}
