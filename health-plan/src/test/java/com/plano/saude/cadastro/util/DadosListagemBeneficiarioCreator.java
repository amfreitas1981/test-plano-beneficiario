package com.plano.saude.cadastro.util;

import com.plano.saude.cadastro.domain.beneficiario.DadosListagemBeneficiario;
import com.plano.saude.cadastro.domain.documento.DadosListagemDocumento;
import com.plano.saude.cadastro.domain.documento.TipoDocumento;
import com.plano.saude.cadastro.domain.endereco.Endereco;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class DadosListagemBeneficiarioCreator {

    public static List<DadosListagemDocumento> dadosListagemDocumento = Collections.singletonList(new DadosListagemDocumento(
            null,
            TipoDocumento.CARTEIRA_IDENTIDADE,
            "43789563",
            LocalDate.parse("2019-09-18"),
            "Test description",
            LocalDate.parse("2024-04-16"),
            LocalDate.parse("2024-04-16")
    ));

    public static Endereco endereco = new Endereco(
            "Rua",
            "Vila da Esperanca",
            "01000-000",
            "Cidade da Oportunidade",
            "TO",
            "Casa",
            "123"
    );

    public static DadosListagemBeneficiario createDadosListagemBeneficiarioToBeSaved(){
        return new DadosListagemBeneficiario(
                null,
                "Nome Completo Beneficiario",
                "11987456321",
                LocalDate.parse("1981-06-25"),
                LocalDate.parse("2022-04-20"),
                LocalDate.parse("2022-04-22"),
                dadosListagemDocumento,
                endereco
        );
    }

    public static DadosListagemBeneficiario createDadosListagemBeneficiarioValid(){
        return new DadosListagemBeneficiario(
                1L,
                "Nome Completo Beneficiario",
                "11987456321",
                LocalDate.parse("1981-06-25"),
                LocalDate.parse("2022-04-20"),
                LocalDate.parse("2022-04-22"),
                dadosListagemDocumento,
                endereco
        );
    }

    public static DadosListagemBeneficiario createDadosListagemBeneficiarioValidUpdate(){
        return new DadosListagemBeneficiario(
                1L,
                "Nome Completo Beneficiario 2",
                "11987456321",
                LocalDate.parse("1981-06-25"),
                LocalDate.parse("2022-04-20"),
                LocalDate.parse("2022-04-22"),
                dadosListagemDocumento,
                endereco
        );
    }
}
