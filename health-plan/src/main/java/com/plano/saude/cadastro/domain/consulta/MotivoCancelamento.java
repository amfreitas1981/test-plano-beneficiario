package com.plano.saude.cadastro.domain.consulta;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MotivoCancelamento {
    BENEFICIARIO_DESISTIU,
    MEDICO_CANCELOU,
    OUTROS;

    @JsonCreator
    public static MotivoCancelamento fromString(String value) {
        for (MotivoCancelamento motivoCancelamento : MotivoCancelamento.values()) {
            if (motivoCancelamento.name().equalsIgnoreCase(value)) {
                return motivoCancelamento;
            }
        }
        throw new IllegalArgumentException("MotivoCancelamento inválido: " + value);
    }
}
