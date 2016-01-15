package com.bhcontrole.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bhcontrole.config.ConnectionConfig;
import com.bhcontrole.config.StringToCliente;
import com.bhcontrole.jasper.GeradorRelatorios;
import com.bhcontrole.model.Cliente;
import com.bhcontrole.model.DateForm;
import com.bhcontrole.model.DateForm2;
import com.bhcontrole.model.Hospedagem;
import com.bhcontrole.service.ClienteService;
import com.bhcontrole.service.HospedagemService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
@Configuration
@RequestMapping("/relatorios")
public class JasperController {

	@Autowired
	private HospedagemService hospedagemService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private StringToCliente stringToCliente;

	@Autowired
	Environment environment;

	@Autowired
	ConnectionConfig connectionConfig;

	private GeradorRelatorios geradorRelatorios = new GeradorRelatorios();

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Cliente.class, this.stringToCliente);
	}

	@RequestMapping("/hospedagem_{id}.pdf")
	public String relatorioHospedagemPDF(@PathVariable("id") Long id, RedirectAttributes redirectAttributes,
			Locale locale, HttpServletRequest request, HttpServletResponse response)
					throws ParseException, URISyntaxException, SQLException, MalformedURLException {

		String nomeRelatorio = "hospedagem_detalhada";
		String subRelatorio = "hospedagem_detalhada_despesas";

		Connection conn = connectionConfig.getConnection();

		Hospedagem hospedagem = hospedagemService.find(id);

		if (hospedagem == null) {
			redirectAttributes.addFlashAttribute("error",
					messageSource.getMessage("hospedagem.inexistente", new String[] { id.toString() }, locale));
			return "redirect:/hospedagens";
		}

		URL logo_path = request.getServletContext().getResource("/jasper/logomarca.png");
		URL path = request.getServletContext().getResource("/jasper/" + subRelatorio + ".jasper");
		
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("ID_HOSPEDAGEM", id);
		parametros.put("TOTAL_DIARIAS", hospedagem.calculaTotalDiarias());
		parametros.put("TOTAL_GERAL", hospedagem.calculaTotal());
		parametros.put("LOGO_URL", logo_path.toString());
		parametros.put("SUBREPORT_DIR", path.toString());

		try

		{
			JasperReport report = getCompiledFile(nomeRelatorio, request);
			try {
				geradorRelatorios.generateReportPDF(response, parametros, report, conn);
			} catch (NamingException | SQLException | IOException e) {
				e.printStackTrace();
			}

		} catch (

		JRException e)

		{
			e.printStackTrace();
		} finally

		{
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException expSQL) {
				System.out.println("SQLExp::CLOSING::" + expSQL.toString());
			}
		}

		return null;

	}

	@RequestMapping("/hospedagem_{id}.html")
	public String relatorioHospedagemHTML(@PathVariable("id") Long id, RedirectAttributes redirectAttributes,
			Locale locale, HttpServletRequest request, HttpServletResponse response)
					throws ParseException, IOException, URISyntaxException, SQLException {

		String nomeRelatorio = "hospedagem_detalhada";
		String subRelatorio = "hospedagem_detalhada_despesas";

		Connection conn = connectionConfig.getConnection();

		Hospedagem hospedagem = hospedagemService.find(id);

		if (hospedagem == null) {
			redirectAttributes.addFlashAttribute("error",
					messageSource.getMessage("hospedagem.inexistente", new String[] { id.toString() }, locale));
			return "redirect:/hospedagens";
		}

		URL logo_path = request.getServletContext().getResource("/jasper/logomarca.png");
		URL path = request.getServletContext().getResource("/jasper/" + subRelatorio + ".jasper");
		
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("ID_HOSPEDAGEM", id);
		parametros.put("TOTAL_DIARIAS", hospedagem.calculaTotalDiarias());
		parametros.put("TOTAL_GERAL", hospedagem.calculaTotal());
		parametros.put("LOGO_URL", logo_path.toString());
		parametros.put("SUBREPORT_DIR", path.toString());

		try {
			JasperReport report = getCompiledFile(nomeRelatorio, request);

			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, conn);

			geradorRelatorios.generateReportHTML(jasperPrint, request, response);

		} catch (JRException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException expSQL) {
				System.out.println("SQLExp::CLOSING::" + expSQL.toString());
			}
		}

		return null;
	}

	@RequestMapping("/hospedagem_{id}.docx")
	public String relatorioHospedagemDOCX(@PathVariable("id") Long id, RedirectAttributes redirectAttributes,
			Locale locale, HttpServletRequest request, HttpServletResponse response)
					throws ParseException, IOException, URISyntaxException, SQLException {

		String nomeRelatorio = "hospedagem_detalhada";
		String subRelatorio = "hospedagem_detalhada_despesas";

		System.err.println(environment.getActiveProfiles().toString());

		Connection conn = connectionConfig.getConnection();

		Hospedagem hospedagem = hospedagemService.find(id);

		if (hospedagem == null) {
			redirectAttributes.addFlashAttribute("error",
					messageSource.getMessage("hospedagem.inexistente", new String[] { id.toString() }, locale));
			return "redirect:/hospedagens";
		}
		
		URL path = request.getServletContext().getResource("/jasper/" + subRelatorio + ".jasper");
		URL logo_path = request.getServletContext().getResource("/jasper/logomarca.png");

		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("ID_HOSPEDAGEM", id);
		parametros.put("TOTAL_DIARIAS", hospedagem.calculaTotalDiarias());
		parametros.put("TOTAL_GERAL", hospedagem.calculaTotal());
		parametros.put("LOGO_URL", logo_path.toString());
		parametros.put("SUBREPORT_DIR", path.toString());

		try {
			JasperReport report = getCompiledFile(nomeRelatorio, request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, conn);

			geradorRelatorios.generateReportDOCX(jasperPrint, "hospedagem_" + id, request, response);

		} catch (JRException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException expSQL) {
				System.out.println("SQLExp::CLOSING::" + expSQL.toString());
			}
		}

		return null;
	}

	@RequestMapping("/hospedagem_{id}.xls")
	public String relatorioHospedagemXLS(@PathVariable("id") Long id, RedirectAttributes redirectAttributes,
			Locale locale, HttpServletRequest request, HttpServletResponse response)
					throws ParseException, IOException, URISyntaxException, SQLException {

		String nomeRelatorio = "hospedagem_detalhada";
		String subRelatorio = "hospedagem_detalhada_despesas";

		Connection conn = connectionConfig.getConnection();

		Hospedagem hospedagem = hospedagemService.find(id);

		if (hospedagem == null) {
			redirectAttributes.addFlashAttribute("error",
					messageSource.getMessage("hospedagem.inexistente", new String[] { id.toString() }, locale));
			return "redirect:/hospedagens";
		}
		
		URL path = request.getServletContext().getResource("/jasper/" + subRelatorio + ".jasper");
		URL logo_path = request.getServletContext().getResource("/jasper/logomarca.png");

		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("ID_HOSPEDAGEM", id);
		parametros.put("TOTAL_DIARIAS", hospedagem.calculaTotalDiarias());
		parametros.put("TOTAL_GERAL", hospedagem.calculaTotal());
		parametros.put("LOGO_URL", logo_path.toString());
		parametros.put("SUBREPORT_DIR", path.toString());
		
		try {
			JasperReport report = getCompiledFile(nomeRelatorio, request);
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, conn);

			geradorRelatorios.generateReportXLS(jasperPrint, "hospedagem_" + id, request, response);

		} catch (JRException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException expSQL) {
				System.out.println("SQLExp::CLOSING::" + expSQL.toString());
			}
		}

		return null;
	}

	@RequestMapping("/form1")
	public ModelAndView form1(@ModelAttribute DateForm dateForm) {
		ModelAndView modelAndView = new ModelAndView("relatorios/form1");
		return modelAndView;
	}

	@RequestMapping(value = "/relatorioClienteHospedagens", method = RequestMethod.POST)
	public ModelAndView clienteHospedagemPeriodo(@ModelAttribute("dateForm") @Valid DateForm dateForm,
			BindingResult result, RedirectAttributes redirectAttributes, HttpServletRequest request,
			HttpServletResponse response)
					throws ParseException, URISyntaxException, SQLException, MalformedURLException {

		List<Hospedagem> hospedagens = hospedagemService.findHospedagemByClienteAndDatas(dateForm.getCliente(),
				dateForm.getDataInicial(), dateForm.getDataFinal());

		if (dateForm.getDataInicial() != null && dateForm.getDataFinal() != null) {
			if (dateForm.getDataInicial().after(dateForm.getDataFinal())) {
				result.rejectValue("dataInicial", "dataInicial.menor.dataFinal");
			}
		}

		if (hospedagens.isEmpty() && dateForm.getDataInicial() != null && dateForm.getDataFinal() != null
				&& !dateForm.getDataInicial().after(dateForm.getDataFinal())) {
			result.rejectValue("cliente", "cliente.hospedagem.periodo.inexistentes");
		}

		if (result.hasErrors()) {
			return form1(dateForm);
		}

		String nomeRelatorio = "cliente_hospedagem_periodo";
		Long id = dateForm.getCliente().getId();
		String formato = dateForm.getFormato();

		Connection conn = connectionConfig.getConnection();

		URL logo_path = request.getServletContext().getResource("/jasper/logomarca.png");

		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("ID_CLIENTE", id);
		parametros.put("DATA_INICIAL", dateForm.getDataInicial().getTime());
		parametros.put("DATA_FINAL", dateForm.getDataFinal().getTime());
		parametros.put("LOGO_URL", logo_path.toString());

		try {
			JasperReport report = getCompiledFile(nomeRelatorio, request);
			try {
				if (formato.equalsIgnoreCase("html")) {
					JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, conn);
					geradorRelatorios.generateReportHTML(jasperPrint, request, response);
				} else if (formato.equalsIgnoreCase("docx")) {
					JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, conn);
					geradorRelatorios.generateReportDOCX(jasperPrint, "cliente_" + id + "_hospedagens", request,
							response);
				} else if (formato.equalsIgnoreCase("xls")) {
					JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, conn);
					geradorRelatorios.generateReportXLS(jasperPrint, "cliente_" + id + "_hospedagens", request,
							response);
				} else if (formato.equalsIgnoreCase("pdf")) {
					geradorRelatorios.generateReportPDF(response, parametros, report, conn);
				}
			} catch (NamingException | SQLException | IOException e) {
				e.printStackTrace();
			}
		} catch (JRException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException expSQL) {
				System.out.println("SQLExp::CLOSING::" + expSQL.toString());
			}
		}

		return null;
	}

	@RequestMapping("/form2")
	public ModelAndView form2(@ModelAttribute DateForm2 dateForm) {
		ModelAndView modelAndView = new ModelAndView("relatorios/form2");
		return modelAndView;
	}

	@RequestMapping(value = "/relatorioHospedagens", method = RequestMethod.POST)
	public ModelAndView hospedagensPeriodo(@ModelAttribute("dateForm2") @Valid DateForm2 dateForm2,
			BindingResult result, RedirectAttributes redirectAttributes, Locale locale, HttpServletRequest request,
			HttpServletResponse response)
					throws ParseException, URISyntaxException, SQLException, MalformedURLException {

		List<Hospedagem> hospedagens = hospedagemService.findByDatas(dateForm2.getDataInicial(),
				dateForm2.getDataFinal());

		if (dateForm2.getDataInicial() != null && dateForm2.getDataFinal() != null) {
			if (dateForm2.getDataInicial().after(dateForm2.getDataFinal())) {
				result.rejectValue("dataInicial", "dataInicial.menor.dataFinal");
			}
		}

		if (hospedagens.isEmpty() && dateForm2.getDataInicial() != null && dateForm2.getDataFinal() != null
				&& !dateForm2.getDataInicial().after(dateForm2.getDataFinal())) {
			result.rejectValue("dataInicial", "hospedagem.periodo.inexistentes");
		}

		if (result.hasErrors()) {
			return form2(dateForm2);
		}

		String nomeRelatorio = "hospedagens_periodo";
		String formato = dateForm2.getFormato();

		Connection conn = connectionConfig.getConnection();

		URL logo_path = request.getServletContext().getResource("/jasper/logomarca.png");

		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("DATA_INICIAL", dateForm2.getDataInicial().getTime());
		parametros.put("DATA_FINAL", dateForm2.getDataFinal().getTime());
		parametros.put("LOGO_URL", logo_path.toString());

		try {
			JasperReport report = getCompiledFile(nomeRelatorio, request);
			try {
				if (formato.equalsIgnoreCase("html")) {
					JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, conn);
					geradorRelatorios.generateReportHTML(jasperPrint, request, response);
				} else if (formato.equalsIgnoreCase("docx")) {
					JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, conn);
					geradorRelatorios.generateReportDOCX(jasperPrint, "hospedagens", request, response);
				} else if (formato.equalsIgnoreCase("xls")) {
					JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, conn);
					geradorRelatorios.generateReportXLS(jasperPrint, "hospedagens", request, response);
				} else if (formato.equalsIgnoreCase("pdf")) {
					geradorRelatorios.generateReportPDF(response, parametros, report, conn);
				}
			} catch (NamingException | SQLException | IOException e) {
				e.printStackTrace();
			}
		} catch (JRException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException expSQL) {
				System.out.println("SQLExp::CLOSING::" + expSQL.toString());
			}
		}

		return null;
	}

	private JasperReport getCompiledFile(String fileName, HttpServletRequest request)
			throws JRException, MalformedURLException {

		// compita relatorio
		InputStream sourceReport = request.getServletContext().getResourceAsStream("/jasper/" + fileName + ".jrxml");
		System.out.println(sourceReport);

		JasperReport report = JasperCompileManager.compileReport(sourceReport);

		return report;
	}

	@ModelAttribute("clientes")
	public List<Cliente> inicializaHospedes() {
		return clienteService.findAll();
	}

	@ModelAttribute("formatos")
	public ArrayList getJasperRptFormats() {
		ArrayList<String> jasperRptFormats = new ArrayList<String>();
		jasperRptFormats.add("HTML");
		jasperRptFormats.add("PDF");
		jasperRptFormats.add("DOCX");
		jasperRptFormats.add("XLS");

		return jasperRptFormats;
	}
}
