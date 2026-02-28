package br.com.Inovasys.modulos.gestaoOficina.funcionarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerFuncionario {

    @ExceptionHandler(FuncionarioNaoLocalizadoException.class)
    public ResponseEntity<Error> handlerFuncionarioNaoLocalizado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Funcionário não localizado, tente novamente.",
                        "Ocorreu um erro com o funcionário informado, por favor tente novamente."));
    }

    @ExceptionHandler(FuncionarioStatusException.class)
    public ResponseEntity<Error> handleFuncionarioStatus(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Não foi possível alterar o status do funcionário.",
                        "Ocorreu um erro com o funcionário informado, por favor tente novamente."));
    }
}