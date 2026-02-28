package br.com.Inovasys.modulos.gestaoOficina.empresa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerEmpresa {

    @ExceptionHandler(CNPJDuplicadoException.class)
    public ResponseEntity<Error> handlerCnpjDuplicado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "CNPJ já cadastro em sistema.",
                        "Ocorreu um erro com o CNPJ informado, por favor tente novamente."));
    }

    @ExceptionHandler(EmailDuplicadoException.class)
    public ResponseEntity<Error> handleEmailDuplicado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Email já cadastro em sistema.",
                        "Ocorreu um erro com o email informado, por favor tente novamente."));
    }

    @ExceptionHandler(EmpresaNaoLocalizadaException.class)
    public ResponseEntity<Error> handleEmpresaNaoLocalizada(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Empresa não localizada.",
                        "Ocorreu um erro com a empresa informado, por favor tente novamente."));
    }
}
