package com.projeto.padrao.service;

import com.projeto.padrao.dto.RelacionamentoDTO;
import com.projeto.padrao.exceptions.DefaultExceptionHandler;
import com.projeto.padrao.model.Relacionamento;
import com.projeto.padrao.repository.ModelRepository;
import com.projeto.padrao.repository.RelacionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class RelacionamentoService extends BaseService {

    @Autowired
    private RelacionamentoRepository relacionamentoRepository;
    @Autowired
    private ModelRepository modelRepository;

    public List<Relacionamento> listarTodos() throws DefaultExceptionHandler {
        List<Relacionamento> relacionamentoList = relacionamentoRepository.findAll();
        if (!relacionamentoList.isEmpty() || relacionamentoList != null) {
            try {
                return relacionamentoList;
            } catch (Exception e) {
                throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(), "Operação inválida! Erro leitura relacionamento.");
            }
        } else {
            throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(), "Operação inválida! Lista vazia.");
        }
    }

    public Relacionamento listarPorId(final Integer id) throws DefaultExceptionHandler {
        if (ObjectUtils.isEmpty(id)) {
            throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(), "Operação inválida! O campo 'ID' é obrigatório.");
        }
        try {
            // tendo ID informado, faz a pesquisa no repositório
            // SE tiver dados, RETURN
            // SENÃO cai na DefaultExceptionHandler (ternário)
            return this.relacionamentoRepository.findById(id).orElseThrow(
                    () -> new DefaultExceptionHandler(HttpStatus.NOT_FOUND.value(), "Nenhuma informação encontrada para o ID informado.")
            );
        } catch (Exception e) {
            throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(), "Operação inválida! Erro leitura relacionamento.");
        }
    }

    @Transactional(rollbackFor = DefaultExceptionHandler.class)
    public Relacionamento cadastrar(RelacionamentoDTO relacionamentoDTO) throws DefaultExceptionHandler {
        if (ObjectUtils.isEmpty(relacionamentoDTO)) {
            throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(),
                    "Operação inválida! O objeto tem que estar preenchido.");
        }
        try {
            Integer modelInt = relacionamentoDTO.getModelDTO().getId();
            this.modelRepository.findById(modelInt).get();
            if (modelInt == null) {
                throw new DefaultExceptionHandler(HttpStatus.BAD_REQUEST.value(),
                        "Operação inválida. O ID do model informado não existe.");
            }
            relacionamentoDTO.setId(null);
            Relacionamento relacionamento = convertToModel(relacionamentoDTO, Relacionamento.class);
            return this.relacionamentoRepository.save(relacionamento);
        } catch (Exception e) {
            throw new DefaultExceptionHandler(HttpStatus.EXPECTATION_FAILED.value(), e.getMessage());
        }
    }
}
