package com.api.controleestacionamento.services;

import com.api.controleestacionamento.modelo.VagasEstacionamentoModelo;
import com.api.controleestacionamento.repository.VagasEstacionamentoRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class VagasEstacionamentoService {

    @Autowired
    final VagasEstacionamentoRepository vagasEstacionamentoRepository;

    public VagasEstacionamentoService(VagasEstacionamentoRepository vagasEstacionamentoRepository) {
        this.vagasEstacionamentoRepository = vagasEstacionamentoRepository;
    }

//   QUANDO USAR MÉTODOS CONSTRUTIVOS OU DESTRUTIVOS RECOMENDA-SE USAR O @TRANSACTIONAL PARA QUE CASO DE ERRADO O ROLLBACK SEJA EXECUTADO A FIM DE GARANTIR QUE OS DADOS NÃO FIQUEM QUEBRADOS
    @Transactional
    public VagasEstacionamentoModelo save(VagasEstacionamentoModelo vagasEstacionamentoModelo) {
        return vagasEstacionamentoRepository.save(vagasEstacionamentoModelo);
    }

    public boolean existsByPlacaCarro(String placaCarro) {
        return vagasEstacionamentoRepository.existsByPlacaCarro(placaCarro);
    }

   /* public boolean existsByPlacaCarro(String placaCarro){ return vagasEstacionamentoRepository.existsByPlacaCarro(placaCarro);}
    public boolean existsByNumeroVaga(Object numeroVaga) { return vagasEstacionamentoRepository.existsByNumeroVaga(numeroVaga); }
    public boolean existsByApartamentoBloco(Object apartamento, Object bloco) {
        return vagasEstacionamentoRepository.existsByApartamentoBloco(apartamento, bloco) ;}*/
}
