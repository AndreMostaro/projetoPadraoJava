package com.projeto.padrao.comum;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseExtensions {

    @Autowired
    private ModelMapper modelMapper;

    public <D> D convertToModel(Object dto, Class<D> model) { return this.convertTo(dto, model); }

    public <D> D convertToDTO(Object model, Class<D> dto) { return this.convertTo(model, dto); }

    public <D> D convertTo(Object source, Class<D> destinationType) { return this.modelMapper.map(source, destinationType); }

    public <S, D> List<D> convertListTo(List<S> source, Class<D> destinationType) {
        return source.stream().map(element -> this.convertTo(element, destinationType)).collect(Collectors.toList()); }
}
