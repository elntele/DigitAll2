package br.DigitAll2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	 */
	
	@CrossOrigin
	@RequestMapping(method=RequestMethod.POST, headers="Accept=application/json")
	public ArquivoPdfDTO UparPdf(@RequestBody ArquivoPdfDTO pdfDTO){
		ArquivoPdf pdf = pdfService.converterArquivoPdfDTOParaArquivoPdf(pdfDTO);
		ArquivoPdf pdfUpado = pdfService.salvar(pdf);
		return pdfService.converteArquivoPdfParaArquivoPdfDTO(pdfUpado);
	}
	
	

}
