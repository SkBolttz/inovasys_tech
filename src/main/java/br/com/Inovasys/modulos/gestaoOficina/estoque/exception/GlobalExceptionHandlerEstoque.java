package br.com.Inovasys.modulos.gestaoOficina.estoque.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerEstoque {

    @ExceptionHandler(DuplicidadeDescricaoException.class)
    public ResponseEntity<Error> handlerDescricaoDuplicidade(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Descrição já cadastrada em sistema.",
                        "Ocorreu um erro com a descrição informado, por favor tente novamente."));
    }

    @ExceptionHandler(DuplicidadeCodigoException.class)
    public ResponseEntity<Error> handlerCodigoDuplicidade(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Código já cadastrado em sistema.",
                        "Ocorreu um erro com o código informado, por favor tente novamente."));
    }

    @ExceptionHandler(ItemEstoqueNaoLocalizadoException.class)
    public ResponseEntity<Error> handlerItemEstoqueNaoLocalizado(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Item estoque não localizado.",
                        "Ocorreu um erro com o item informado, por favor tente novamente."));
    }

    @ExceptionHandler(EstoqueNuloException.class)
    public ResponseEntity<Error> handlerEstoqueNulo(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Estoque atual nao pode ser negativo.",
                        "Ocorreu um erro com o item informado, por favor tente novamente."));
    }
}
