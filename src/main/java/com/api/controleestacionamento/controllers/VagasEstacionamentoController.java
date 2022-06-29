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
        if (vagasEstacionamentoService.existsByPlacaCarro(vagasEstacionamentoDto.getPlacaCarro())){ return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflíto: A Placa do carro já está em uso !!!");}
//
//        if (vagasEstacionamentoService.existsByNumeroVaga(vagasEstacionamentoDto.getNumeroVaga())){ return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflíto: A vaga já está em uso !!!");}
//
//        if (vagasEstacionamentoService.existsByApartamentoBloco(vagasEstacionamentoDto.getApartamento(), vagasEstacionamentoDto.getBloco())){ return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflíto: A vaga já está cadastrada para outro apartamento !!!");}

        var vagasEstacionamentoModelo = new VagasEstacionamentoModelo();
        BeanUtils.copyProperties(vagasEstacionamentoDto, vagasEstacionamentoModelo);
//        vagasEstacionamentoModelo.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(vagasEstacionamentoService.save(vagasEstacionamentoModelo));
    }

}
