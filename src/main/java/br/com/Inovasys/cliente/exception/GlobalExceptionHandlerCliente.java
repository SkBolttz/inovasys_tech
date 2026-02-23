package br.com.Inovasys.cliente.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerCliente {

    @ExceptionHandler(DuplicidadeCnpjCpfException.class)
    public ResponseEntity<Error> handlerCnpjDuplicado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "CNPJ/CPF já cadastro em sistema.",
                        "Ocorreu um erro com o CNPJ/CPF informado, por favor tente novamente."));
    }

    @ExceptionHandler(DuplicidadeEmailClienteException.class)
    public ResponseEntity<Error> handlerEmailDuplicado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Email já cadastro em sistema.",
                        "Ocorreu um erro com o email informado, por favor tente novamente."));
    }

    @ExceptionHandler(ClienteNaoLocalizadoException.class)
    public ResponseEntity<Error> handlerClienteNaoLocalizado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Cliente não localizado em sistema.",
                        "Ocorreu um erro com o cliente informado, por favor tente novamente."));
    }

    @ExceptionHandler(DuplicidadeTelefoneClienteException.class)
    public ResponseEntity<Error> handlerTelefoneDuplicado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Telefone já cadastrado em sistema.",
                        "Ocorreu um erro com o telefone informado, por favor tente novamente."));
    }
}