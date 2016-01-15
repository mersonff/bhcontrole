package com.bhcontrole.jasper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;

public class GeradorRelatorios {

	public void generateReportPDF(HttpServletResponse resp, Map parameters, JasperReport report, Connection conn)
			throws JRException, NamingException, SQLException, IOException {
		byte[] bytes = null;
		bytes = JasperRunManager.runReportToPdf(report, parameters, conn);
		resp.reset();
		resp.resetBuffer();
		resp.setContentType("application/pdf");
		resp.setContentLength(bytes.length);
		ServletOutputStream ouputStream = resp.getOutputStream();
		ouputStream.write(bytes, 0, bytes.length);
		ouputStream.flush();
		ouputStream.close();
	}

	public void generateReportHTML(JasperPrint jasperPrint, HttpServletRequest req, HttpServletResponse resp)
			throws IOException, JRException {

		HtmlExporter exporter = new HtmlExporter();

		List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
		jasperPrintList.add(jasperPrint);
		exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
		SimpleHtmlExporterOutput exporterOutput = new SimpleHtmlExporterOutput(resp.getWriter());
		exporterOutput.setImageHandler(new WebHtmlResourceHandler("../resources/imgs/logomarca.png"));
		exporter.setExporterOutput(exporterOutput);
		SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
		exporter.setConfiguration(configuration);
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		exporter.exportReport();

	}

	public void generateReportDOCX(JasperPrint jasperPrint, String fileName, HttpServletRequest req,
			HttpServletResponse resp) throws IOException, JRException {
		JRDocxExporter exporter = new JRDocxExporter();

		try {
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(resp.getOutputStream()));
			resp.setContentType("application/force-download");
			resp.setHeader("Content-Transfer-Encoding", "binary");
			resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".docx\"");
			exporter.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	public void generateReportXLS(JasperPrint jasperPrint, String fileName, HttpServletRequest req,
			HttpServletResponse resp) throws IOException, JRException {
		JRXlsExporter exporter = new JRXlsExporter();
		try {
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(resp.getOutputStream()));
			SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
			configuration.setOnePagePerSheet(true);
			exporter.setConfiguration(configuration);
			resp.setContentType("application/force-download");
			resp.setHeader("Content-Transfer-Encoding", "binary");
			resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xls\"");
			exporter.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
