package com.api.controleestacionamento.repository;

import com.api.controleestacionamento.modelo.VagasEstacionamentoModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VagasEstacionamentoRepository extends JpaRepository <VagasEstacionamentoModelo, UUID> {

    boolean existsByPlacaCarro(String placaCarro);

    boolean existsByNumeroVaga(String numeroVaga);

    boolean existsByApartamentoAndBloco(String apartamento, String bloco);

}
