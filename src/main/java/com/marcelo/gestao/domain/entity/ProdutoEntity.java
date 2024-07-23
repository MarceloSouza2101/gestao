package com.marcelo.gestao.domain.entity;

import static com.marcelo.gestao.util.Constante.AMERICA_SAO_PAULO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_PRODUTO", schema = "dbo")
public class ProdutoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "PRECO")
	private BigDecimal preco;

	@Column(name = "QUANTIDADE")
	private Long quantidade;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORIA", nullable = false)
	private CategoriaEntity categoria;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FORNECEDOR", nullable = false)
	private FornecedorEntity fornecedor;

	@Column(name = "DATA_CRIACAO")
	private LocalDateTime dataCriacao;

	@PrePersist
	private void onCreate() {
		dataCriacao = LocalDateTime.now(ZoneId.of(AMERICA_SAO_PAULO));
	}
}
