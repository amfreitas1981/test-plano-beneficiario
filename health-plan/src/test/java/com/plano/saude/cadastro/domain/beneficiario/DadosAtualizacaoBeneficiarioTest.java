package com.plano.saude.cadastro.domain.beneficiario;

import com.plano.saude.cadastro.domain.documento.TipoDocumento;
import com.plano.saude.cadastro.domain.endereco.DadosEndereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DadosAtualizacaoBeneficiarioTest {

    @Test
    @DisplayName("Teste para mapear retorno sucesso")
    void testDadosAtualizacaoBeneficiarioSucesso(){
        DadosAtualizacaoDocumento dadosAtualizacaoDocumento = new DadosAtualizacaoDocumento(
                null,
                TipoDocumento.CARTEIRA_IDENTIDADE,
                "43789563",
                LocalDate.parse("2019-09-18"),
                "Test description",
                LocalDate.parse("2024-04-16"),
                LocalDate.parse("2024-04-16"));

        DadosEndereco dadosEndereco = new DadosEndereco(
                "Rua Casas Unidas",
                "Bairro",
                "12345-789",
                "Cidade",
                "SP",
                "Casa",
                "789");

        DadosAtualizacaoBeneficiario dadosAtualizacaoBeneficiario = new DadosAtualizacaoBeneficiario(
                null,
                "Nome",
                "112345678",
                dadosEndereco,
                Collections.singletonList(dadosAtualizacaoDocumento)
        );

        assertNotNull(dadosAtualizacaoBeneficiario);
    }

}