package br.com.drogaria.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Usuario extends GenereciDomain {

	@Column(nullable = false, length = 32)
	private String senha;

	@Column(nullable = false)
	private Character tipo;

	@Column(nullable = false)
	private Boolean ativo;

	@OneToOne
	@JoinColumn(nullable = false)
	private Pessoa pessoa;

	@Transient
	private String senhaSemCriptografia;

	public String getSenhaSemCriptografia() {
		return senhaSemCriptografia;
	}

	public void setSenhaSemCriptografia(String senhaSemCriptografia) {
		this.senhaSemCriptografia = senhaSemCriptografia;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Transient
	public String getTipoFormatado() {

		String tipoFormatado = null;

		if (tipo == 'A') {
			tipoFormatado = "Administrador";
		} else if (tipo == 'B') {
			tipoFormatado = "Balconista";
		} else if (tipo == 'G') {
			tipoFormatado = "Gerente";
		}

		return tipoFormatado;
	}

	@Transient
	public String getAtivoFormatado() {

		String ativoFormatado = "Não";

		if (ativo == true) {
			ativoFormatado = "Sim";
		}

		return ativoFormatado;
	}

}
