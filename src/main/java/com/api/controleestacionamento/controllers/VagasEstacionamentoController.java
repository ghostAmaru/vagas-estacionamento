package com.api.controleestacionamento.controllers;

import com.api.controleestacionamento.dto.VagasEstacionamentoDto;
import com.api.controleestacionamento.modelo.VagasEstacionamentoModelo;
import com.api.controleestacionamento.services.VagasEstacionamentoService;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/vagas-estacionamento")
public class VagasEstacionamentoController {

    final VagasEstacionamentoService vagasEstacionamentoService;

    public VagasEstacionamentoController(VagasEstacionamentoService vagasEstacionamentoService) {
        this.vagasEstacionamentoService = vagasEstacionamentoService;
    }

    @PostMapping
    public ResponseEntity<Object> salvarVagasEstacionamento(@RequestBody @Valid VagasEstacionamentoDto vagasEstacionamentoDto){
        /*
        LicensePlateCar = PlacaCarro - placaCarro
        ParkingSpot = VagaEstacionamento
        SpotNumber = NumeroVaga - numeroVaga
        ApartmentAndBlock = Apartamento e bloco - apartamento - bloco

        * */
        if (vagasEstacionamentoService.existsByPlacaCarro(vagasEstacionamentoDto.getPlacaCarro())){ return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflíto: A Placa do carro já está em uso !!!");} // É esperado um código de conflito 409

        if (vagasEstacionamentoService.existsByNumeroVaga(vagasEstacionamentoDto.getNumeroVaga())){ return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflíto: A vaga já está em uso !!!");}

        if (vagasEstacionamentoService.existsByApartamentoAndBloco(vagasEstacionamentoDto.getApartamento(), vagasEstacionamentoDto.getBloco())){ return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflíto: A vaga já está cadastrada para outro apartamento !!!");}

        var vagasEstacionamentoModelo = new VagasEstacionamentoModelo();
        BeanUtils.copyProperties(vagasEstacionamentoDto, vagasEstacionamentoModelo);
//        vagasEstacionamentoModelo.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(vagasEstacionamentoService.save(vagasEstacionamentoModelo));
    }

    @GetMapping
    public ResponseEntity<List<VagasEstacionamentoModelo>> getAllVagasEstacionamento(){ return ResponseEntity.status(HttpStatus.OK).body(vagasEstacionamentoService.findAll()); }

    @GetMapping ("/{id}")
    public ResponseEntity<Object> getOneVagas(@PathVariable(value = "id") UUID id){
        Optional<VagasEstacionamentoModelo> vagasEstacionamentoModeloOptional = vagasEstacionamentoService.findById(id);
        if(!vagasEstacionamentoModeloOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga do Estacionamento não foi encontrada !");
        }
        return ResponseEntity.status(HttpStatus.OK).body(vagasEstacionamentoModeloOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOneVagas(@PathVariable(value = "id") UUID id){
        Optional<VagasEstacionamentoModelo> vagasEstacionamentoModeloOptional = vagasEstacionamentoService.findById(id);
        if(!vagasEstacionamentoModeloOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga do Estacionamento não foi encontrada !");
        }
        vagasEstacionamentoService.delete(vagasEstacionamentoModeloOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("A vaga selecionada foi apagada com sucesso ! ");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizaOneVagas(@PathVariable(value = "id") UUID id, @RequestBody @Valid VagasEstacionamentoDto vagasEstacionamentoDto){
        Optional<VagasEstacionamentoModelo> vagasEstacionamentoModeloOptional = vagasEstacionamentoService.findById(id);
        if(!vagasEstacionamentoModeloOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga do Estacionamento não foi encontrada !");
        }
        var vagasEstacionamentoModelo = vagasEstacionamentoModeloOptional.get();
        vagasEstacionamentoModelo.setNumeroVaga(vagasEstacionamentoDto.getNumeroVaga());
        vagasEstacionamentoModelo.setPlacaCarro(vagasEstacionamentoDto.getPlacaCarro());
        vagasEstacionamentoModelo.setMarcaCarro(vagasEstacionamentoDto.getMarcaCarro());
        vagasEstacionamentoModelo.setModeloCarro(vagasEstacionamentoDto.getModeloCarro());
        vagasEstacionamentoModelo.setCorCarro(vagasEstacionamentoDto.getCorCarro());
        vagasEstacionamentoModelo.setResponsavelNome(vagasEstacionamentoDto.getResponsavelNome());
        vagasEstacionamentoModelo.setApartamento(vagasEstacionamentoDto.getApartamento());
        vagasEstacionamentoModelo.setBloco(vagasEstacionamentoDto.getBloco());
        return ResponseEntity.status(HttpStatus.OK).body("As informações da vaga foram atualizadas com sucesso ! ");
    }


}
