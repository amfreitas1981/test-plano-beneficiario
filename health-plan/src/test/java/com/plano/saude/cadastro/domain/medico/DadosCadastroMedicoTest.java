package com.plano.saude.cadastro.domain.medico;

import com.plano.saude.cadastro.domain.endereco.DadosEndereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DadosCadastroMedicoTest {

    @Test
    @DisplayName("Teste para mapear retorno sucesso")
    void testDadosCadastroMedico(){
        DadosEndereco dadosEndereco = new DadosEndereco(
                "Rua xpto",
                "Bairro",
                "12345-789",
                "Cidade",
                "SP",
                "Casa",
                "789"
        );

        DadosCadastroMedico dadosCadastroMedico = new DadosCadastroMedico(
                "Nome Cadastro Medico",
                "email@email.com",
                "654321",
                "1147859635",
                Especialidade.DERMATOLOGIA,
                dadosEndereco
        );

        assertNotNull(dadosCadastroMedico);
    }
}