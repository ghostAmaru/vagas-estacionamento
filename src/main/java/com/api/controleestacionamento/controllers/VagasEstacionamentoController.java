package com.api.controleestacionamento.controllers;

import com.api.controleestacionamento.dto.VagasEstacionamentoDto;
import com.api.controleestacionamento.modelo.VagasEstacionamentoModelo;
import com.api.controleestacionamento.services.VagasEstacionamentoService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
        if (vagasEstacionamentoService.existsByPlacaCarro(vagasEstacionamentoDto.getPlacaCarro())){ return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflíto: A Placa do carro já está em uso !!!");} // É esperado um código de conflito 409

        if (vagasEstacionamentoService.existsByNumeroVaga(vagasEstacionamentoDto.getNumeroVaga())){ return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflíto: A vaga já está em uso !!!");}

        if (vagasEstacionamentoService.existsByApartamentoAndBloco(vagasEstacionamentoDto.getApartamento(), vagasEstacionamentoDto.getBloco())){ return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflíto: A vaga já está cadastrada para outro apartamento !!!");}

        var vagasEstacionamentoModelo = new VagasEstacionamentoModelo();
        BeanUtils.copyProperties(vagasEstacionamentoDto, vagasEstacionamentoModelo);
        vagasEstacionamentoModelo.setLocalDateTime(LocalDateTime.now(ZoneId.of("UTC"))); //LocalDateTime.now(ZoneId.of("GMT")));
        return ResponseEntity.status(HttpStatus.CREATED).body(vagasEstacionamentoService.save(vagasEstacionamentoModelo));
    }

    @GetMapping
    public ResponseEntity<Page<VagasEstacionamentoModelo>> getAllVagasEstacionamento(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(vagasEstacionamentoService.findAll(pageable)); }

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

    //    @PutMapping("/{id}")
//    public ResponseEntity<Object> atualizaOneVagas(@PathVariable(value = "id") UUID id, @RequestBody @Valid VagasEstacionamentoDto vagasEstacionamentoDto){
//        Optional<VagasEstacionamentoModelo> vagasEstacionamentoModeloOptional = vagasEstacionamentoService.findById(id);
//        if(!vagasEstacionamentoModeloOptional.isPresent()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga do Estacionamento não foi encontrada !");
//        }
//        var vagasEstacionamentoModelo = vagasEstacionamentoModeloOptional.get();
//        vagasEstacionamentoModelo.setNumeroVaga(vagasEstacionamentoDto.getNumeroVaga());
//        vagasEstacionamentoModelo.setPlacaCarro(vagasEstacionamentoDto.getPlacaCarro());
//        vagasEstacionamentoModelo.setMarcaCarro(vagasEstacionamentoDto.getMarcaCarro());
//        vagasEstacionamentoModelo.setModeloCarro(vagasEstacionamentoDto.getModeloCarro());
//        vagasEstacionamentoModelo.setCorCarro(vagasEstacionamentoDto.getCorCarro());
//        vagasEstacionamentoModelo.setResponsavelNome(vagasEstacionamentoDto.getResponsavelNome());
//        vagasEstacionamentoModelo.setApartamento(vagasEstacionamentoDto.getApartamento());
//        vagasEstacionamentoModelo.setBloco(vagasEstacionamentoDto.getBloco());
//        return ResponseEntity.status(HttpStatus.OK).body(vagasEstacionamentoService.save(vagasEstacionamentoModelo));
//    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizaOneVagas(@PathVariable(value = "id") UUID id, @RequestBody @Valid VagasEstacionamentoDto vagasEstacionamentoDto){
        Optional<VagasEstacionamentoModelo> vagasEstacionamentoModeloOptional = vagasEstacionamentoService.findById(id);
        if(!vagasEstacionamentoModeloOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga do Estacionamento não foi encontrada !");
        }
        var vagasEstacionamentoModelo = new VagasEstacionamentoModelo();
        BeanUtils.copyProperties(vagasEstacionamentoDto, vagasEstacionamentoModelo);
        vagasEstacionamentoModelo.setId(vagasEstacionamentoModeloOptional.get().getId());
        vagasEstacionamentoModelo.setLocalDateTime(vagasEstacionamentoModeloOptional.get().getLocalDateTime());
        return ResponseEntity.status(HttpStatus.OK).body(vagasEstacionamentoService.save(vagasEstacionamentoModelo));
    }

}
