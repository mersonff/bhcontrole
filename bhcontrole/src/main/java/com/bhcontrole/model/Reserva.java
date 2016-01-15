package com.bhcontrole.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "RESERVA")
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NOME_SOLICITANTE")
	@NotNull
	@Size(min = 4, max = 50, message = "{nome.error.tamanho}")
	private String nome;

	@Column(name = "TELEFONES")
	@Embedded
	private Telefones telefones;

	@Column(name = "EMAIL")
	@Email(message = "{email.invalido}")
	private String email;

	@Lob
	@Column(name = "COMENTARIO")
	@Size(min=0, max=200, message="{comentario.error.tamanho}")
	private String comentario;

	@Column(name = "DATA_SOLICITACAO")
	@Past
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
	private Calendar dataSolicitacao;

	@Column(name = "DATA_ENTRADA")
	@NotNull(message="{data.obrigatoria}")
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
	private Calendar dataEntrada;

	@Column(name = "QUANTIDADE_DE_QUARTOS")
	@Min(1)
	private int quantidadeDeQuartos;

	@Column(name = "QUANTIDADE_DE_HOSPEDES")
	@Min(1)
	private int quantidadeDeHospedes;

	@Column(name = "ESTADO")
	@NotEmpty
	private String estado = EstadoReserva.EMABERTO.getEstado();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "RESERVA_USUARIO", joinColumns = { @JoinColumn(name = "ID_RESERVA") }, inverseJoinColumns = {
			@JoinColumn(name = "ID_USUARIO") })
	private Usuario usuarioQueCadastrou;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Calendar dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public Calendar getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Calendar dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public int getQuantidadeDeQuartos() {
		return quantidadeDeQuartos;
	}

	public void setQuantidadeDeQuartos(int quantidadeDeQuartos) {
		this.quantidadeDeQuartos = quantidadeDeQuartos;
	}

	public int getQuantidadeDeHospedes() {
		return quantidadeDeHospedes;
	}

	public void setQuantidadeDeHospedes(int quantidadeDeHospedes) {
		this.quantidadeDeHospedes = quantidadeDeHospedes;
	}

	public Usuario getUsuarioQueCadastrou() {
		return usuarioQueCadastrou;
	}

	public void setUsuarioQueCadastrou(Usuario usuarioQueCadastrou) {
		this.usuarioQueCadastrou = usuarioQueCadastrou;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Telefones getTelefones() {
		return telefones;
	}

	public void setTelefones(Telefones telefones) {
		this.telefones = telefones;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", nome=" + nome + ", telefones=" + telefones + ", email=" + email
				+ ", comentario=" + comentario + ", dataSolicitacao=" + dataSolicitacao + ", dataEntrada=" + dataEntrada
				+ ", quantidadeDeQuartos=" + quantidadeDeQuartos + ", quantidadeDeHospedes=" + quantidadeDeHospedes
				+ ", estado=" + estado + ", usuarioQueCadastrou=" + usuarioQueCadastrou + "]";
	}

}
