package com.projeto.padrao.controller;

import com.projeto.padrao.dto.RelacionamentoDTO;
import com.projeto.padrao.exceptions.DefaultExceptionHandler;
import com.projeto.padrao.model.Relacionamento;
import com.projeto.padrao.repository.RelacionamentoRepository;
import com.projeto.padrao.service.RelacionamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/relacionamentos")
public class RelacionamentoControlller extends BaseController{

    @Autowired
    private RelacionamentoRepository relacionamentolRepository;
    @Autowired
    private RelacionamentoService relacionamentoService;

    @GetMapping("/listarTodos")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<Relacionamento>> listarTodos() throws DefaultExceptionHandler {
        List<Relacionamento> relacionamentoList = this.relacionamentoService.listarTodos();
        return ResponseEntity.ok(relacionamentoList);
    }

    @GetMapping("/listar/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Relacionamento> listarPorId(@PathVariable(value = "id")Integer id) throws DefaultExceptionHandler {
        Relacionamento relacionamento = this.relacionamentoService.listarPorId(id);
        return ResponseEntity.ok(relacionamento);
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<RelacionamentoDTO> cadastrar(@Valid @RequestBody RelacionamentoDTO relacionamentoDTO) throws DefaultExceptionHandler {
        Relacionamento relacionamento = this.relacionamentoService.cadastrar(relacionamentoDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(relacionamento.getId()).toUri();
        return ResponseEntity.created(location).body(super.convertTo(relacionamento, RelacionamentoDTO.class));
    }
}
