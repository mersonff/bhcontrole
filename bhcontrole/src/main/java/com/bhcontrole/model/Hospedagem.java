package com.bhcontrole.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "HOSPEDAGEM")
public class Hospedagem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull(message = "{cliente.obrigatorio}")
	@JoinTable(name = "HOSPEDAGEM_CLIENTE", joinColumns = {
			@JoinColumn(name = "ID_HOSPEDAGEM") }, inverseJoinColumns = { @JoinColumn(name = "ID_CLIENTE") })
	private Cliente cliente;

	@OneToOne(fetch = FetchType.EAGER)
	@NotNull(message = "{apartamento.obrigatorio}")
	@JoinTable(name = "HOSPEDAGEM_APARTAMENTO", joinColumns = {
			@JoinColumn(name = "ID_HOSPEDAGEM") }, inverseJoinColumns = { @JoinColumn(name = "ID_APARTAMENTO") })
	private Apartamento apartamento;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "DATA_ENTRADA")
	@NotNull(message = "{data.obrigatoria}")
	private Calendar dataEntrada;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "DATA_SAIDA")
	private Calendar dataSaida;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "HOSPEDAGEM_USUARIO", joinColumns = {
			@JoinColumn(name = "ID_HOSPEDAGEM") }, inverseJoinColumns = { @JoinColumn(name = "ID_USUARIO") })
	private Usuario usuarioQueCadastrou;

	@DecimalMin(value = "45.00", message = "{valor.minimo}")
	@NotNull(message = "{valor.obrigatorio}")
	@Column(name = "VALOR_DIARIA", scale = 2)
	private BigDecimal valorDiaria;

	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinTable(name = "HOSPEDAGEM_DESPESA", joinColumns = {
			@JoinColumn(name = "ID_HOSPEDAGEM") }, inverseJoinColumns = { @JoinColumn(name = "ID_DESPESA") })
	private List<Despesa> despesas = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Apartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}

	public Calendar getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Calendar dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Calendar getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Calendar dataSaida) {
		this.dataSaida = dataSaida;
	}

	public Usuario getUsuarioQueCadastrou() {
		return usuarioQueCadastrou;
	}

	public void setUsuarioQueCadastrou(Usuario usuarioQueCadastrou) {
		this.usuarioQueCadastrou = usuarioQueCadastrou;
	}

	public BigDecimal getValorDiaria() {
		return valorDiaria;
	}

	public void setValorDiaria(BigDecimal valorDiaria) {
		this.valorDiaria = valorDiaria;
	}

	public List<Despesa> getDespesas() {
		return despesas;
	}

	public void setDespesas(List<Despesa> despesas) {
		this.despesas = despesas;
	}

	public double calculaDiarias() {
		double diarias;
		int tempoDia = 1000 * 60 * 60 * 24;
		Calendar dataHoje = Calendar.getInstance();
		// LocalDate dataHoje = LocalDate.now();
		// LocalDate dataEntrada =
		// this.dataEntrada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		if (this.dataSaida == null) {
			double dt = (dataHoje.getTimeInMillis() - this.dataEntrada.getTimeInMillis());
			diarias = dt / tempoDia;
			// Period periodo = Period.between(dataEntrada, dataHoje);
			// diarias = periodo.getDays();
			System.out.println("Diária " + diarias);

			if (diarias < 1) {
				return 1;
			}

			if (Math.ceil(diarias) - diarias > 0.01) {
				System.out.println("TETO: " + Math.ceil(diarias)+"\n");
				return Math.ceil(diarias);
			}
			System.out.println("CHAO: " + Math.floor(diarias)+"\n");
			return Math.floor(diarias);
		}

		double dt = (this.dataSaida.getTimeInMillis() - this.dataEntrada.getTimeInMillis());
		diarias = dt / tempoDia;
		// LocalDate dataSaida =
		// this.dataSaida.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		// Period periodo = Period.between(dataEntrada, dataSaida);
		// diarias = periodo.getDays();
		System.out.println("Diária " + diarias);

		if (diarias < 1) {
			return 1;
		}

		if (Math.ceil(diarias) - diarias > 0.01) {
			System.out.println("TETO: " + Math.ceil(diarias)+"\n");
			return Math.ceil(diarias);
		}

		
		System.out.println("CHAO: " + Math.floor(diarias)+"\n");
		return Math.floor(diarias);

	}

	public BigDecimal calculaTotalDiarias() {
		double diarias = calculaDiarias();
		BigDecimal valorTotal = this.valorDiaria.multiply(new BigDecimal(diarias));
		return valorTotal;
	}

	public BigDecimal somaDespesas() {
		BigDecimal total = new BigDecimal(0);
		for (Despesa despesa : this.despesas) {
			total = total.add(despesa.getValor());
		}
		return total;
	}

	public BigDecimal calculaTotal() {
		BigDecimal total = calculaTotalDiarias().add(somaDespesas());
		return total;
	}

	@Override
	public String toString() {
		return "Hospedagem [id=" + id + ", cliente=" + cliente + ", apartamento=" + apartamento + ", dataEntrada="
				+ dataEntrada + ", dataSaida=" + dataSaida + ", usuarioQueCadastrou=" + usuarioQueCadastrou
				+ ", valorDiaria=" + valorDiaria + ", despesas=" + despesas + "]";
	}

}
