package com.plano.saude.cadastro.domain.documento;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoDocumento {
    CARTEIRA_IDENTIDADE,
    CPF,
    CREA,
    CRM,
    CRO,
    CRP,
    CRF,
    CARTEIRA_NACIONAL_HABILITACAO,
    CARTEIRA_IDENTIFICACAO_FUNCIONAL,
    CARTEIRA_DE_TRABALHO,
    PASSAPORTE,
    REGISTRO_NACIONAL_ESTRANGEIRO;

    @JsonCreator
    public static TipoDocumento fromString(String value) {
        for (TipoDocumento tipoDocumento : TipoDocumento.values()) {
            if (tipoDocumento.name().equalsIgnoreCase(value)) {
                return tipoDocumento;
            }
        }
        throw new IllegalArgumentException("TipoDocumento inválido: " + value);
    }
}
