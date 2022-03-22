package com.projeto.padrao.service;

import com.projeto.padrao.dto.ModelDTO;
import com.projeto.padrao.dto.PageDTO;
import com.projeto.padrao.enums.EnumAtivoInativo;
import com.projeto.padrao.exceptions.DefaultExceptionHandler;
import com.projeto.padrao.model.Model;
import com.projeto.padrao.repository.ModelRepository;
import com.projeto.padrao.utils.CpfCnpjUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModelService extends BaseService {

    @Autowired
    private ModelRepository modelRepository;

    public List<Model> listarTodos() throws DefaultExceptionHandler {
        List<Model> modelList = modelRepository.findAll();
        if (!modelList.isEmpty() || modelList != null) {
            try {
                return modelList;
            } catch (Exception e) {
                throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(), "Operação inválida! Erro leitura model.");
            }
        } else {
            throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(), "Operação inválida! Lista vazia.");
        }
    }

    public Model listarPorId(final Integer id) throws DefaultExceptionHandler {
        if (ObjectUtils.isEmpty(id)) {
            throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(), "Operação inválida! O campo 'ID' é obrigatório.");
        }
        try {
            // tendo ID informado, faz a pesquisa no repositório
            // SE tiver dados, RETURN
            // SENÃO cai na DefaultExceptionHandler (ternário)
            return this.modelRepository.findById(id).orElseThrow(
                    () -> new DefaultExceptionHandler(HttpStatus.NOT_FOUND.value(), "Nenhuma informação encontrada para o ID informado.")
            );
        } catch (Exception e) {
            throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(), "Operação inválida! Erro leitura model.");
        }
    }

    public PageDTO<ModelDTO> consultarPaginado(int page, int size, ModelDTO request) throws DefaultExceptionHandler {
        try {
            PageRequest pageable = PageRequest.of(
                    page,
                    size,
                    Sort.Direction.ASC, "nome");

            Page<Model> pages;
            if (!org.apache.commons.lang3.ObjectUtils.isEmpty(request)) {
                pages = this.modelRepository.findAll(Example.of(super.convertToModel(request, Model.class)), pageable);
            } else {
                pages = this.modelRepository.findAll(pageable);
            }
            if (!pages.isEmpty()) {
                final long totalElements = pages.getTotalElements();
                final int totalPages = pages.getTotalPages();
                final boolean isFirst = pages.isFirst();
                final boolean isLast = pages.isLast();

                List<ModelDTO> out = pages.stream()
                        .map(entity -> super.convertToDTO(entity, ModelDTO.class)).collect(Collectors.toList());

                PageDTO<ModelDTO> pageDTO = new PageDTO<ModelDTO>();
                pageDTO.setTotalElements(BigDecimal.valueOf(totalElements));
                pageDTO.setTotalPages(BigDecimal.valueOf(totalPages));
                pageDTO.setFirst(isFirst);
                pageDTO.setLast(isLast);
                pageDTO.setContent(out);

                return pageDTO;
            }
        } catch (Exception e) {
            throw new DefaultExceptionHandler(HttpStatus.EXPECTATION_FAILED.value(), e.getMessage());
        }
        return null;
    }

    @Transactional(rollbackFor = DefaultExceptionHandler.class)
    public Model cadastrar(ModelDTO modelDTO) throws DefaultExceptionHandler {
        this.validaCadastrarModel(modelDTO);
        try {
            Optional<Model> model = this.modelRepository.findByCpf(modelDTO.getCpf().replaceAll("\\D", ""));
            if (model.isEmpty()) {
                modelDTO.setId(null);
                modelDTO.setNome(modelDTO.getNome());
                modelDTO.setCpf(modelDTO.getCpf().replaceAll("\\D", ""));
                modelDTO.setEmail(modelDTO.getEmail());
                if (StringUtils.isEmpty(modelDTO.getAtivoInativo())) {
                    modelDTO.setAtivoInativo(EnumAtivoInativo.ATIVO.getCodigo());
                } else {
                    modelDTO.setAtivoInativo(modelDTO.getAtivoInativo());
                }
            } else {
                throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(),
                        "Operação inválida! CPF já cadastrado.");
            }
            return this.modelRepository.save(super.convertToModel(modelDTO, Model.class));
        } catch (Exception e) {
            throw new DefaultExceptionHandler(HttpStatus.EXPECTATION_FAILED.value(), e.getMessage());
        }
    }

    private void validaCadastrarModel(ModelDTO request) throws DefaultExceptionHandler{
        if(StringUtils.isEmpty(request.getNome().toLowerCase())){
            throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(),
                    "Operação inválida! O nome do model não pode ser nulo.");
        }
        if(StringUtils.isEmpty(request.getCpf().toLowerCase())){
            throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(),
                    "Operação inválida! O cpf do model não pode ser nulo.");
        }
        if (!CpfCnpjUtil.isCPF(request.getCpf())) {
            throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(),
                    "Operação inválida! O cpf inserido é inválido.");
        }
        if(StringUtils.isEmpty(request.getEmail().toLowerCase())){
            throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(),
                    "Operação inválida! O email do model não pode ser nulo.");
        }
    }

    @Transactional(rollbackFor = DefaultExceptionHandler.class)
    public Model atualizar(ModelDTO modelDTO) throws DefaultExceptionHandler {
        this.validaAtualizarModel(modelDTO);
        if (ObjectUtils.isEmpty(modelDTO) || ObjectUtils.isEmpty(modelDTO.getId())) {
            throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(), "Operação inválida! O campo ID é obrigatório.");
        }
        try {
            Model model = this.consultarPorId(modelDTO.getId());
            model.setNome(modelDTO.getNome());
            model.setEmail(modelDTO.getEmail());
            return modelRepository.save(model);
        } catch (DefaultExceptionHandler e) {
            throw e;
        } catch (Exception e) {
            throw new DefaultExceptionHandler(HttpStatus.EXPECTATION_FAILED.value(), e.getMessage());
        }
    }

    public Model consultarPorId(final Integer id) throws DefaultExceptionHandler{
        if (ObjectUtils.isEmpty(id)) {
            throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(),
                    "Operação inválida! O campo 'id' é obrigatório.");
        }
        try {
            // tendo ID informado, faz a pesquisa no repositório
            // SE tiver dados, RETURN
            // SENÃO cai na DefaultExceptionHandler (ternário)
            return this.modelRepository.findById(id).orElseThrow(
                    () -> new DefaultExceptionHandler(HttpStatus.NOT_FOUND.value(),
                            "Nenhuma informação encontrada para o ID informado.")
            );
        } catch (DefaultExceptionHandler e) {
            throw e;
        } catch (Exception e) {
            throw new DefaultExceptionHandler(HttpStatus.EXPECTATION_FAILED.value(), e.getMessage());
        }
    }

    private void validaAtualizarModel(ModelDTO request) throws DefaultExceptionHandler{
        if(StringUtils.isEmpty(request.getNome().toLowerCase())){
            throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(),
                    "Operação inválida! O nome do model não pode ser nulo.");
        }
        if(StringUtils.isEmpty(request.getEmail().toLowerCase())){
            throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(),
                    "Operação inválida! O email do model não pode ser nulo.");
        }
    }

    public void ativarPorId(final Integer id) throws DefaultExceptionHandler {
        if (ObjectUtils.isEmpty(id)) {
            throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(), "Operação inválida! O campo ID é obrigatório.");
        }
        try {
            // tendo ID informado, faz a pesquisa no repositório
            // SE tiver dados, Model model recebe...
            // SENÃO cai na DefaultExceptionHandler (ternário)
            Model model = this.modelRepository.findById(id).orElseThrow(
                    () -> new DefaultExceptionHandler(HttpStatus.NOT_FOUND.value(), "Nenhuma informação encontrada para os parâmetros informados.")
            );
            model.setAtivoInativo(EnumAtivoInativo.ATIVO.getCodigo());
            this.modelRepository.save(model);
        } catch (DefaultExceptionHandler e) {
            throw e;
        } catch (Exception e) {
            throw new DefaultExceptionHandler(HttpStatus.EXPECTATION_FAILED.value(), e.getMessage());
        }
    }

    @Transactional(rollbackFor = DefaultExceptionHandler.class)
    public void deletarPorId(final Integer id) throws DefaultExceptionHandler {
        if (ObjectUtils.isEmpty(id)) {
            throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(), "Operação inválida! O campo ID é obrigatório.");
        }
        try {
            // tendo ID informado, faz a pesquisa no repositório
            // SE tiver dados, Model model recebe...
            // SENÃO cai na DefaultExceptionHandler (ternário)
            Model model = this.modelRepository.findById(id).orElseThrow(
                    () -> new DefaultExceptionHandler(HttpStatus.NOT_FOUND.value(), "Nenhuma informação encontrada para os parâmetros informados.")
            );
            // inativar - NAO DELETA DO BANCO, APENAS INATIVA PACIENTE
            model.setAtivoInativo(EnumAtivoInativo.INATIVO.getCodigo());
            this.modelRepository.save(model);
            // deletar - DELETA DO BANCO - comentar linha acima e descomentar linha abaixo
//            this.modelRepository.delete(model);
        } catch (DefaultExceptionHandler e) {
            throw e;
        } catch (Exception e) {
            throw new DefaultExceptionHandler(HttpStatus.EXPECTATION_FAILED.value(), e.getMessage());
        }
    }
}
