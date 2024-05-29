package com.plano.saude.cadastro.domain.beneficiario;

import com.plano.saude.cadastro.domain.documento.DadosListagemDocumento;
import com.plano.saude.cadastro.domain.documento.TipoDocumento;
import com.plano.saude.cadastro.domain.endereco.Endereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DadosListagemBeneficiarioTest {

    @Test
    @DisplayName("Teste para mapear retorno sucesso")
    void testDadosListagemBeneficiarioSucesso(){
        List<DadosListagemDocumento> dadosListagemDocumento = Collections.singletonList(new DadosListagemDocumento(
                null,
                TipoDocumento.CARTEIRA_IDENTIDADE,
                "43789563",
                LocalDate.parse("2019-09-18"),
                "Test description",
                LocalDate.parse("2024-04-16"),
                LocalDate.parse("2024-04-16")
        ));

        Endereco endereco = new Endereco(
                "Rua",
                "Vila da Esperanca",
                "01000-000",
                "Cidade da Oportunidade",
                "TO",
                "Casa",
                "123"
        );

        DadosListagemBeneficiario dadosListagemBeneficiario = new DadosListagemBeneficiario(
                1L,
                "Nome Beneficiario Completo",
                "1123447894",
                LocalDate.parse("1999-12-02"),
                LocalDate.parse("2023-11-22"),
                LocalDate.parse("2024-01-22"),
                dadosListagemDocumento,
                endereco
        );

        assertNotNull(dadosListagemBeneficiario);
    }
}
