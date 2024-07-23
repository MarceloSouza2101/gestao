package com.marcelo.gestao.service.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.marcelo.gestao.domain.dto.nested.PaisFonteDTO;
import com.marcelo.gestao.exception.NaoEncontradoException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GenericClient {

	@Value("${paisurl}")
	private String paisurl;

	private WebClient client;

	public WebClient createHeader(String url) {

		return WebClient.builder().baseUrl(url)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.exchangeStrategies(ExchangeStrategies.builder()
						.codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(10000 * 1024)).build())
				.build();
	}

	public List<PaisFonteDTO> getLista(String url) {

		this.client = this.createHeader(this.paisurl + url);

		return client.get().retrieve()
				.onStatus(HttpStatus.NOT_FOUND::equals,
						response -> response.bodyToMono(String.class).map(NaoEncontradoException::new))
				.onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals,
						response -> response.bodyToMono(String.class).map(Exception::new))
				.bodyToMono(new ParameterizedTypeReference<List<PaisFonteDTO>>() {
				}).block();
	}

	public <T> T getValidarCodigo(String url, Class<T> responseClass) {

		this.client = this.createHeader(this.paisurl + url);

		return client.get().exchangeToMono(response -> {
			if (!response.statusCode().is2xxSuccessful()) {
				return response.bodyToMono(String.class) // Pode ser String ou outro tipo para capturar a mensagem de
															// erro
						.flatMap(errorMsg -> {
							return Mono.empty();
						});
			}
			return response.bodyToMono(responseClass);
		}).block();
	}
}
