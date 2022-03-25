package com.projeto.padrao.controller;

import com.projeto.padrao.dto.ModelDTO;
import com.projeto.padrao.dto.PageDTO;
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

@CrossOrigin(origins = "http://localhost:4200")
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

//    @RequestMapping(value = "/consultar-paginado", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PostMapping("/consultar-paginado")
    public ResponseEntity<PageDTO<ModelDTO>> consultarPaginado(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                               @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                               @RequestBody(required = false) ModelDTO modelDTO) throws DefaultExceptionHandler {
        PageDTO<ModelDTO> out = this.modelService.consultarPaginado(page, size, modelDTO);
        return ResponseEntity.ok(out);
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ModelDTO> cadastrar(@Valid @RequestBody ModelDTO modelDTO) throws DefaultExceptionHandler {
        Model model = this.modelService.cadastrar(modelDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(model.getId()).toUri();
        return ResponseEntity.created(location).body(super.convertTo(model, ModelDTO.class));
    }

    // TODO - analisar a possibilidade de mandar ID via url ou deixar como está (visão de login)
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
