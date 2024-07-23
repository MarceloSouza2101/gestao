package com.marcelo.gestao.exception.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.marcelo.gestao.domain.dto.ExceptionResponseDTO;
import com.marcelo.gestao.exception.DadosJaCadastradosException;
import com.marcelo.gestao.exception.NaoEncontradoException;

import lombok.Generated;

@Generated
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String HEADER_MESSAGE = "mensagem";

	private static final String TITLE_NAO_ENCONTRADO = "Registro não encontrado";
	private static final String TYPE_NAO_ENCONTRADO = "Registro não encontrado";

	private static final String TITLE_DADOS_JA_CADASTRADOS = "Dados já cadastrados";
	private static final String TYPE_DADOS_JA_CADASTRADOS = "Dados já cadastrados";

	private static final String TITLE_DADOS_INVALIDOS = "Dados inválidos";
	private static final String TYPE_DADOS_INVALIDOS = "Um ou mais campos estão inválidos";

	private static final String TITLE_PARAMETROS_INVALIDOS = "Parâmetros inválidos";
	private static final String TYPE_VALIDACAO_PARAMETROS = "Validação de Parâmetros";

	@ExceptionHandler(DadosJaCadastradosException.class)
	public ResponseEntity<Object> handleDadosJaCadastradosException(DadosJaCadastradosException e,
			ServletWebRequest request) {
		logger.warn(e.getMessage());

		ExceptionResponseDTO bodyExceptionResponse = criarExceptionResponse(TITLE_DADOS_JA_CADASTRADOS,
				TYPE_DADOS_JA_CADASTRADOS, Arrays.asList(e.getMessage()), request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, bodyExceptionResponse, header, BAD_REQUEST, request);
	}

	@ExceptionHandler(NaoEncontradoException.class)
	public ResponseEntity<Object> handleNaoEncontradoException(NaoEncontradoException e, ServletWebRequest request) {
		logger.warn(e.getMessage());

		ExceptionResponseDTO bodyExceptionResponse = criarExceptionResponse(TITLE_NAO_ENCONTRADO, TYPE_NAO_ENCONTRADO,
				Arrays.asList(e.getMessage()), request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, bodyExceptionResponse, header, NOT_FOUND, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		logger.warn(e.getMessage());

		ExceptionResponseDTO bodyExceptionResponse = criarExceptionResponse(TITLE_PARAMETROS_INVALIDOS,
				TYPE_VALIDACAO_PARAMETROS, e.getAllErrors().stream()
						.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()),
				request.getDescription(false).replace("uri=", ""));

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getObjectName());

		return handleExceptionInternal(e, bodyExceptionResponse, header, HttpStatus.BAD_REQUEST, request);
	}

	private ExceptionResponseDTO criarExceptionResponse(String title, String type, List<String> detail,
			String instance) {
		return ExceptionResponseDTO.builder().detail(detail).instance(instance).title(title).type(type).build();

	}
}
