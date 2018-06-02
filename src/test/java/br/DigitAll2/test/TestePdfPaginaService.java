package br.DigitAll2.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//import br.DigitAll2.dto.ArquivoPdfDTO;
import br.DigitAll2.dto.PdfPaginaDTO;
import br.DigitAll2.entity.ArquivoPdf;
import br.DigitAll2.entity.PdfPagina;
import br.DigitAll2.service.ArquivoPdfService;
import br.DigitAll2.service.PdfPaginaService;

@RunWith(SpringRunner.class)
@SpringBootTest

public class TestePdfPaginaService {

		
		@Autowired
		public PdfPaginaService pdfPaginaService;

		// @Autowired
		private ModelMapper modelMapper;
		
		@Autowired
		public ArquivoPdfService arquivoPdfService;
		

		//este
		@Test
		public void testSalvarArquivoPdf() throws Exception {
			byte[] file = FileUtils.readFileToByteArray(new File("C:/sqlteste/multipage-txt.pdf"));
			byte[] file2 = FileUtils.readFileToByteArray(new File("C:/sqlteste/singlepage-txt.pdf"));			
			PDDocument document = null;
			document = PDDocument.load(file);
			int NumeroDeOPaginasDoDocumento=document.getNumberOfPages();
			ArquivoPdf a = new ArquivoPdf();
			a.setNome("multipage-txt");
			a.setPdf(file);
			arquivoPdfService.salvar(a,NumeroDeOPaginasDoDocumento);
			
			PDDocument document2 = null;
			document2 = PDDocument.load(file2);
			int NumeroDeOPaginasDoDocumento2=document.getNumberOfPages();
			PdfPagina a2 = new PdfPagina();
			a2.setDocumentOriginal(a);
			a2.setNome("singlepage-txt");
			a2.setPdf(file2);
			a2.setNumeroDaPagina(1);
			a2.setNumeroDepaginasDODocumento(1);
			a2.setStartPage(false);
			a2.setIdOriginalDocument(1);
			pdfPaginaService.salvarPagina(a2);

			assertFalse(pdfPaginaService.getPaginas().contains(a));
		}

		

		@Test
		public void testConverterPdfPaginaDTOParaPdfPagina() throws Exception {

			byte[] file = FileUtils.readFileToByteArray(new File("C:/sqlteste/multipage-txt.pdf"));
			
			PdfPaginaDTO paginaDTO = new PdfPaginaDTO();
			paginaDTO.setNome("multipage-txt");
			paginaDTO.setPdf(file);
			PDDocument documento=null;
			paginaDTO.setNumeroDepaginasDODocumento(1);
			
			PdfPagina pagina = pdfPaginaService.converterPdfPaginaDTOParaPdfPagina(paginaDTO);
			
			byte [] byteDto=paginaDTO.getPdf();
			byte [] byteAquivo=pagina.getPdf();
			assertEquals(pagina.getNome(), paginaDTO.getNome());
			assertArrayEquals(byteDto, byteAquivo);

		} 

	
	
}
