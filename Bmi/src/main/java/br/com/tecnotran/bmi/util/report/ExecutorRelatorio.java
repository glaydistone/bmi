package br.com.tecnotran.bmi.util.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.jdbc.Work;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class ExecutorRelatorio implements Work {
	private String caminhoRelatorio;
	private HttpServletResponse response;
	private Map<String, Object> parametros;
	private String nomeArquivoSaida;
	private Connection conexao;

	private boolean relatorioGerado;

	public ExecutorRelatorio(String caminhoRelatorio, HttpServletResponse response, Map<String, Object> parametros,
			String nomeArquivoSaida) {
		this.caminhoRelatorio = caminhoRelatorio;
		this.response = response;
		this.parametros = parametros;
		this.nomeArquivoSaida = nomeArquivoSaida;

		this.parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
	}


	public void execute(Connection connection) throws SQLException {
		try {
			this.conexao = connection;
			InputStream relatorioStream = this.getClass().getResourceAsStream(this.caminhoRelatorio);

			JasperPrint print = JasperFillManager.fillReport(relatorioStream, this.parametros, connection);
			this.relatorioGerado = print.getPages().size() > 0;

			if (this.relatorioGerado) {

				ServletOutputStream output = this.response.getOutputStream();
		//		FileOutputStream fos = new FileOutputStream(new File("c:/teste.pdf"));
				
				
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + this.nomeArquivoSaida + "\"");
				JasperExportManager.exportReportToPdfStream(print, output);

				output.flush();
				output.close();

			}
		} catch (Exception e) {
			throw new SQLException("Erro ao executar relatório " + this.caminhoRelatorio, e);
		}
	}

	public Connection getConexao() {
		return conexao;
	}


	public void setConexao(Connection conexao) {
		this.conexao = conexao;
	}


	public boolean isRelatorioGerado() {
		return relatorioGerado;
	}
}
