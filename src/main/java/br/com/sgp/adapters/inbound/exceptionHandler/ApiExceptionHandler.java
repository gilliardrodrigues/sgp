package br.com.sgp.adapters.inbound.exceptionHandler;

import br.com.sgp.application.core.exception.EntidadeNaoEncontradaException;
import br.com.sgp.application.core.exception.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource msgSrc;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleExceptionInternal(exception, montarCorpoDoErro(exception, "Erro: um ou mais campos incorretos!", status), headers, status, request);
    }

    private Erro montarCorpoDoErro(MethodArgumentNotValidException exception, String mensagem, HttpStatus status){

        var erro = new Erro();
        erro.setStatus(status.value());
        erro.setDataHora(OffsetDateTime.now());
        erro.setTitulo(mensagem);
        erro.setCampos(retornarListaDeErros(exception));

        return erro;
    }

    private List<Campo> retornarListaDeErros(MethodArgumentNotValidException exception){

        List<Campo> erros = new ArrayList<Campo>();
        for(ObjectError erro : exception.getBindingResult().getAllErrors()){
            String nomeCampo = ((FieldError) erro).getField();
            String mensagem = msgSrc.getMessage(erro, LocaleContextHolder.getLocale());
            erros.add(new Campo(nomeCampo, mensagem));
        }
        return erros;
    }

    private Erro montarCorpoDoErro(String mensagem, HttpStatus status){

        var erro = new Erro();
        erro.setStatus(status.value());
        erro.setDataHora(OffsetDateTime.now());
        erro.setTitulo(mensagem);
        return erro;
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocioException(NegocioException exception, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handleExceptionInternal(exception, montarCorpoDoErro(exception.getMessage(), status), new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException exception, WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        return handleExceptionInternal(exception, montarCorpoDoErro(exception.getMessage(), status), new HttpHeaders(), status, request);
    }
}
