package com.plano.saude.cadastro.domain.endereco;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EnderecoTest {

    @Test
    @DisplayName("Deveria retornar sucesso")
    void testEnderecoSucesso(){
        Endereco endereco = new Endereco(
                "Rua",
                "Vila da Esperanca",
                "01000-000",
                "Cidade da Oportunidade",
                "TO",
                "Casa",
                "123"
        );

        assertNotNull(endereco);
    }
}