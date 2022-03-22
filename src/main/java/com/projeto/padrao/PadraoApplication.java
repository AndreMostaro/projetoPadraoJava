package com.projeto.padrao;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class PadraoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PadraoApplication.class, args);
        System.out.println("André Mostaro - PROJETO PADRÃO");
        System.out.println("Direitos reservados - 2022");
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }

}
