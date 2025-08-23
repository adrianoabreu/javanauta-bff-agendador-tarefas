package com.javanauta.bffagendadortarefas.infrastructure.client.config;

import com.javanauta.bffagendadortarefas.infrastructure.exceptions.BusinessException;
import com.javanauta.bffagendadortarefas.infrastructure.exceptions.ConflictException;
import com.javanauta.bffagendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.bffagendadortarefas.infrastructure.exceptions.UnauthorizedException;
import com.javanauta.bffagendadortarefas.infrastructure.exceptions.IllegalArgumentException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FeignError implements ErrorDecoder {


    @Override
    public Exception decode(String s, Response response) {

        String erro = "Erro: ";
        String mensagemErro = erro + mensagemErro(response);


        switch (response.status()){
            case 409:
                return new ConflictException(mensagemErro);
            case 403:
                return new ResourceNotFoundException(mensagemErro);
            case 401:
                return new UnauthorizedException(mensagemErro);
            case 400:
                return new IllegalArgumentException(mensagemErro);
            default:
                return new BusinessException(mensagemErro);
        }
    }

    private String mensagemErro(Response response){
        try {
            if(Objects.isNull(response.body())){
                return "";
            }
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
