package com.plano.saude.cadastro.domain.documento;

import com.plano.saude.cadastro.domain.beneficiario.Beneficiario;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Table(name = "documentos")
@Entity(name = "Documento")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Documento {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    private String numero;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataExpedicao;

    @ManyToOne
    @JoinColumn(name = "beneficiario_id")
    private Beneficiario beneficiario;

    private String descricao;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataInclusao;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataAtualizacao;

    private Boolean ativo;

    public Documento(DadosCadastroDocumento dados){
        this.tipoDocumento = dados.tipoDocumento();
        this.numero = dados.numero();
        this.dataExpedicao = dados.dataExpedicao();
        this.descricao = dados.descricao();
        this.dataInclusao = dados.dataInclusao();
        this.dataAtualizacao = dados.dataAtualizacao();
        this.ativo = true;
    }

    public Documento(DadosDetalhamentoDocumento dadosDetalhamentoDocumento) {
    }

    public void setBeneficiario(Beneficiario beneficiario){
        this.beneficiario = beneficiario;
    }
}
