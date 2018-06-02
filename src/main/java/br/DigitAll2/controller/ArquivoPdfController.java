package br.DigitAll2.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.DigitAll2.dto.ArquivoPdfDTO;
import br.DigitAll2.entity.ArquivoPdf;
import br.DigitAll2.service.ArquivoPdfService;

/**
 * 
 * @author jorge
 *
 */
@RestController
@CrossOrigin
//@Component
@RequestMapping(path="/ArquivoPdf")
public class ArquivoPdfController {
	@Autowired
	ArquivoPdfService pdfService;
	/**
	 * End Points
	 * @throws IOException 
	 * @throws InvalidPasswordException 
	 */
	
	@CrossOrigin
	@RequestMapping(method=RequestMethod.POST, headers="Accept=application/json")
	public ArquivoPdfDTO UparPdf(@RequestBody ArquivoPdfDTO pdfDTO) throws InvalidPasswordException, IOException{
		
			ArquivoPdf pdf = pdfService.converterArquivoPdfDTOParaArquivoPdf(pdfDTO);
			ArquivoPdf pdfUpado = pdfService.salvar(pdf,1);
	
		return pdfService.converteArquivoPdfParaArquivoPdfDTO(pdfUpado);

	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path="/{id}")
	public ArquivoPdfDTO getPdf(@PathVariable Integer id){
		ArquivoPdf pdfEncontrado=this.pdfService.getPdfById(id);
	return  pdfService.converteArquivoPdfParaArquivoPdfDTO(pdfEncontrado);
		
	}
	
	
	@CrossOrigin
	@RequestMapping(method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE, headers="Accept=application/json")
	public List<ArquivoPdfDTO> getTodosPdfs(){
		
		List<ArquivoPdf> pdfsEncontrados = pdfService.getPdfs();
		return pdfsEncontrados.stream().map(pdf -> pdfService.converteArquivoPdfParaArquivoPdfDTO(pdf)).collect(Collectors.toList());
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, path="/{id}")
	public boolean excluirPdf(@PathVariable Integer id){
		ArquivoPdf pdfEncontrado=this.pdfService.getPdfById(id);
		if (pdfEncontrado.getID() != null) {
			pdfService.excluir(pdfEncontrado);
			return true;
		}
		return false;
	}

	

	
	

}
