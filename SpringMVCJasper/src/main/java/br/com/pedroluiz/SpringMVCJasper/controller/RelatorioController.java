package br.com.pedroluiz.SpringMVCJasper.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.pedroluiz.SpringMVCJasper.persistence.GenericDao;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class RelatorioController {
	
	@Autowired
	GenericDao gDao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(name = "relatorio", value = "/relatorio", method = RequestMethod.POST) 
	public ResponseEntity geraRelatorio(@RequestParam Map<String, String> allRequestParams) {
		String erro = "";
		String mid = allRequestParams.get("mid");
		String pid = allRequestParams.get("pid");
		String cid = allRequestParams.get("cid");
		String tratamento = allRequestParams.get("tratamento");
		
		//Definindo os elementos que serão passados como parâmetros para o Jasper
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("mid", mid);
		param.put("pid", pid);
		param.put("cid", cid);
		param.put("tratamento", tratamento);
		
		byte[] bytes = null;
		
		//Inicializando elementos do response
		InputStreamResource resource = null;
		HttpStatus status = null;
		HttpHeaders header = new HttpHeaders();
		
		try {
			Connection c = gDao.getConnection();
			File arquivo = ResourceUtils.getFile("classpath:relatorioClinica.jasper");
			JasperReport report = (JasperReport) JRLoader.loadObjectFromFile(arquivo.getAbsolutePath());
			bytes = JasperRunManager.runReportToPdf(report, param, c); 
		} catch (FileNotFoundException | JRException | ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		} finally {
			if (erro.equals("")) {
				InputStream inputStream = new ByteArrayInputStream(bytes);
				resource = new InputStreamResource(inputStream);
				header.setContentLength(bytes.length);
				header.setContentType(MediaType.APPLICATION_PDF);
				status = HttpStatus.OK;
			}
		}
		
		return new ResponseEntity(resource, header, status);
	}

}
