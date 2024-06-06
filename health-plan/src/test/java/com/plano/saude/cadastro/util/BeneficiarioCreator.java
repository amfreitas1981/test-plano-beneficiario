package com.plano.saude.cadastro.util;

import com.plano.saude.cadastro.domain.beneficiario.Beneficiario;
import com.plano.saude.cadastro.domain.documento.Documento;
import com.plano.saude.cadastro.domain.documento.TipoDocumento;
import com.plano.saude.cadastro.domain.endereco.Endereco;

import java.time.LocalDate;
import java.util.Collections;

public class BeneficiarioCreator {

    static Documento documentos = new Documento(
            1L,
            TipoDocumento.CARTEIRA_NACIONAL_HABILITACAO,
            "123456",
            LocalDate.parse("2023-11-01"),
            Beneficiario.builder().build(),
            "Descricao",
            LocalDate.parse("2023-11-11"),
            LocalDate.parse("2023-12-21"),
            true
    );

    static Endereco endereco = new Endereco(
            "Rua",
            "Vila da Esperanca",
            "01000-000",
            "Cidade da Oportunidade",
            "TO",
            "Casa",
            "123"
    );

    public static Beneficiario createBeneficiarioToBeSaved(){
        return new Beneficiario(
                null,
                "Nome Beneficiario",
                "1142521749",
                LocalDate.parse("1965-05-05"),
                LocalDate.parse("2023-12-09"),
                LocalDate.parse("2024-01-18"),
                Collections.singletonList(documentos),
                endereco,
                true
        );
    }

    public static Beneficiario createBeneficiarioValid(){
        return new Beneficiario(
                1L,
                "Nome Beneficiario",
                "1142521749",
                LocalDate.parse("1965-05-05"),
                LocalDate.parse("2023-12-09"),
                LocalDate.parse("2024-01-18"),
                Collections.singletonList(documentos),
                endereco,
                true
        );
    }

    public static Beneficiario createBeneficiarioValidUpdate(){
        return new Beneficiario(
                1L,
                "Nome Beneficiario 2",
                "1142521749",
                LocalDate.parse("1965-05-05"),
                LocalDate.parse("2023-12-09"),
                LocalDate.parse("2024-01-18"),
                Collections.singletonList(documentos),
                endereco,
                true
        );
    }
}
