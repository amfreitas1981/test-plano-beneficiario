package com.plano.saude.cadastro.domain.endereco;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DadosEnderecoTest {

    @Test
    @DisplayName("Teste para mapear retorno sucesso")
    void testDadosEnderecoSucesso(){
        DadosEndereco dadosEndereco = new DadosEndereco(
                "Rua Casas Unidas",
                "Bairro",
                "12345-789",
                "Cidade",
                "SP",
                "Casa",
                "789");

        assertNotNull(dadosEndereco);
    }
}