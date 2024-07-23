package com.marcelo.gestao.domain.vo;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProdutoAlteraVO {

	@NotBlank(message = "A descrição do produto é obrigatória")
	private String descricao;

	@NotNull(message = "O preço do produto é obrigatório")
	@Positive(message = "O preço do produto deve ser um valor positivo")
	private BigDecimal preco;

	@NotNull(message = "A quantidade do produto é obrigatória")
	@Positive(message = "A quantidade do produto deve ser um valor positivo")
	private Long quantidade;

}
