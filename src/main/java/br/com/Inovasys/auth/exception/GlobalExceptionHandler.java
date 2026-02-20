package br.com.Inovasys.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CPFInvalidoException.class)
    public ResponseEntity<Error> handleCpfInvalido(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "CPF inválido, tente novamente.",
                        "Ocorreu um erro com o CPF informado, por favor tente novamente."));
    }

    @ExceptionHandler(CPFDuplicadoException.class)
    public ResponseEntity<Error> handleCpfDuplicado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "CPF duplicado, tente novamente.",
                        "Ocorreu um erro com o CPF informado, por favor tente novamente."));
    }

    @ExceptionHandler(UsuarioNaoLocalizadoException.class)
    public ResponseEntity<Error> handleUsuarioNaoLocalizado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Usuário não foi localizado em sistema, tente novamente.",
                        "Ocorreu um erro com o usuário informado, por favor tente novamente."));
    }

    @ExceptionHandler(ErroSenhaException.class)
    public ResponseEntity<Error> handleErroSenha(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Usuario já registrou alteração de senha.",
                        "Ocorreu um erro com o usuário informado, por favor tente novamente."));
    }

    @ExceptionHandler(EmailDuplicadoException.class)
    public ResponseEntity<Error> handleEmailDuplicado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Email já registrado em sistema.",
                        "Ocorreu um erro com o email informado, por favor tente novamente."));
    }

    @ExceptionHandler(TelefoneDuplicadoException.class)
    public ResponseEntity<Error> handleTelefoneDuplicado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Telefone já registrado em sistema.",
                        "Ocorreu um erro com o telefone informado, por favor tente novamente."));
    }
}
