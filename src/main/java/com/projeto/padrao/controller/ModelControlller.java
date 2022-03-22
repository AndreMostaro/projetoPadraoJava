package com.projeto.padrao.controller;

import com.projeto.padrao.dto.ModelDTO;
import com.projeto.padrao.exceptions.DefaultExceptionHandler;
import com.projeto.padrao.model.Model;
import com.projeto.padrao.repository.ModelRepository;
import com.projeto.padrao.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/models")
public class ModelControlller extends BaseController{

    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private ModelService modelService;

    @GetMapping("/listar-todos")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<Model>> listarTodos() throws DefaultExceptionHandler {
        List<Model> modelList = this.modelService.listarTodos();
        return ResponseEntity.ok(modelList);
    }

    @GetMapping("/listar/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Model> listarPorId(@PathVariable(value = "id")Integer id) throws DefaultExceptionHandler {
        Model model = this.modelService.listarPorId(id);
        return ResponseEntity.ok(model);
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ModelDTO> cadastrar(@Valid @RequestBody ModelDTO modelDTO) throws DefaultExceptionHandler {
        Model model = this.modelService.cadastrar(modelDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(model.getId()).toUri();
        return ResponseEntity.created(location).body(super.convertTo(model, ModelDTO.class));
    }

    @PutMapping("/atualizar")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ModelDTO> atualizar(@Valid @RequestBody ModelDTO modelDTO) throws DefaultExceptionHandler {
        Model model = this.modelService.atualizar(modelDTO);
        return ResponseEntity.ok(super.convertTo(model, ModelDTO.class));
    }

    @PutMapping("/ativar/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void ativarPorId(@Valid @PathVariable("id") Integer id) throws DefaultExceptionHandler {
        this.modelService.ativarPorId(id);
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deletarPorId(@Valid @PathVariable("id") Integer id) throws DefaultExceptionHandler {
        this.modelService.deletarPorId(id);
    }
}
