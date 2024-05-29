package com.plano.saude.cadastro.domain.beneficiario;

import com.plano.saude.cadastro.domain.documento.DadosDetalhamentoDocumento;
import com.plano.saude.cadastro.domain.documento.TipoDocumento;
import com.plano.saude.cadastro.domain.endereco.DadosEndereco;
import com.plano.saude.cadastro.domain.endereco.Endereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DadosDetalhamentoBeneficiarioTest {

    @Test
    @DisplayName("Teste para mapear retorno sucesso")
    void testDadosDetalhamentoBeneficiarioSucesso(){

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

        // considerando que sua classe Endereco tenha um construtor que receba um objeto DadosEndereco:
        var enderecoEsperado = new Endereco(dadosAtualizacaoBeneficiario.endereco());

        // considerando que sua classe DadosDetalhamentoDocumento tenha um construtor que receba uma lista DadosCadastroDocumento:
        List<DadosDetalhamentoDocumento> documentosEsperados = dadosAtualizacaoBeneficiario.documentos().stream().map(DadosDetalhamentoDocumento::new).toList();

        DadosDetalhamentoBeneficiario dadosDetalhamentoBeneficiario = new DadosDetalhamentoBeneficiario(
                null,
                dadosAtualizacaoBeneficiario.nome(),
                dadosAtualizacaoBeneficiario.telefone(),
                null,
                null,
                null,
                documentosEsperados,
                enderecoEsperado);

        assertNotNull(dadosDetalhamentoBeneficiario);
    }
}
