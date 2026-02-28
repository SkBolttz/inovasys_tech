package br.com.Inovasys.infra.Exceptions;

import br.com.Inovasys.infra.Exceptions.ClienteException.ClienteNaoLocalizadoException;
import br.com.Inovasys.infra.Exceptions.ClienteException.DuplicidadeCnpjCpfException;
import br.com.Inovasys.infra.Exceptions.ClienteException.DuplicidadeEmailClienteException;
import br.com.Inovasys.infra.Exceptions.ClienteException.DuplicidadeTelefoneClienteException;
import br.com.Inovasys.infra.Exceptions.EmpresaException.CNPJDuplicadoException;
import br.com.Inovasys.infra.Exceptions.EmpresaException.EmailDuplicadoException;
import br.com.Inovasys.infra.Exceptions.EmpresaException.EmpresaNaoLocalizadaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Exceptions para Clientes:

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

    //----------------------------//----------------------------//----------------------------/----------------------------//

    // Exceptions para Empresas:

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

    //----------------------------//----------------------------//----------------------------/----------------------------//

    // Exceptions para Genéricos:

    @ExceptionHandler(CNPJDuplicadoException.class)
    public ResponseEntity<Error> handlerCnpjDuplicado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "CNPJ já cadastro em sistema.",
                        "Ocorreu um erro com o CNPJ informado, por favor tente novamente."));
    }

    //----------------------------//----------------------------//----------------------------/----------------------------//
}
