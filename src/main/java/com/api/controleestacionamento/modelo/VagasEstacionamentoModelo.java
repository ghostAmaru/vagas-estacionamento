package com.api.controleestacionamento.modelo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "TB_VAGAS_DE_ESTACIONAMENTO")
public class VagasEstacionamentoModelo implements Serializable {
    private static final long serialVersionUID = 1L;

    // ADICIONAR TODAS AS VALIDAÇÕES PARA OS CAMPOS ABAIXO
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, unique = true, length = 10)
    private String numeroVaga;
    @Column(nullable = false, unique = true, length = 7)
    private String placaCarro;
    @Column(nullable = false, length = 70)
    private String marcaCarro;
    @Column(nullable = false, length = 70)
    private String modeloCarro;
    @Column(nullable = false, length = 70)
    private String corCarro;
//    @Column(nullable = false)
//    private LocalDateTime dataDoRegistro;
    @Column(nullable = false, length = 130)
    private String responsavelNome;
    @Column(nullable = false, length = 30)
    private String apartamento;
    @Column(nullable = false, length = 30)
    private String bloco;

   // private LocalDateTime registrationDate;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumeroVaga() {
        return numeroVaga;
    }

    public void setNumeroVaga(String numeroVaga) {
        this.numeroVaga = numeroVaga;
    }

    public String getPlacaCarro() {
        return placaCarro;
    }

    public void setPlacaCarro(String placaCarro) {
        this.placaCarro = placaCarro;
    }

    public String getMarcaCarro() {
        return marcaCarro;
    }

    public void setMarcaCarro(String marcaCarro) {
        this.marcaCarro = marcaCarro;
    }

    public String getModeloCarro() {
        return modeloCarro;
    }

    public void setModeloCarro(String modeloCarro) {
        this.modeloCarro = modeloCarro;
    }

    public String getCorCarro() {
        return corCarro;
    }

    public void setCorCarro(String corCarro) {
        this.corCarro = corCarro;
    }

    public String getResponsavelNome() {
        return responsavelNome;
    }

    public void setResponsavelNome(String responsavelNome) {
        this.responsavelNome = responsavelNome;
    }

    public String getApartamento() {
        return apartamento;
    }

    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    public String getBloco() {
        return bloco;
    }

    public void setBloco(String bloco) {
        this.bloco = bloco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VagasEstacionamentoModelo)) return false;
        VagasEstacionamentoModelo that = (VagasEstacionamentoModelo) o;
        return id.equals(that.id) &&
                numeroVaga.equals(that.numeroVaga) &&
                placaCarro.equals(that.placaCarro) &&
                marcaCarro.equals(that.marcaCarro) &&
                modeloCarro.equals(that.modeloCarro) &&
                corCarro.equals(that.corCarro) &&
//                dataDoRegistro.equals(that.dataDoRegistro) &&
                responsavelNome.equals(that.responsavelNome) &&
                apartamento.equals(that.apartamento) &&
                bloco.equals(that.bloco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroVaga, placaCarro, marcaCarro, modeloCarro, corCarro, responsavelNome, apartamento, bloco);
    }



}
