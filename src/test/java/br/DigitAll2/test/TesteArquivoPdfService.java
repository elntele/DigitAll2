package br.DigitAll2.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.DigitAll2.dto.ArquivoPdfDTO;
import br.DigitAll2.entity.ArquivoPdf;
import br.DigitAll2.entity.PdfPagina;
import br.DigitAll2.service.ArquivoPdfService;
import br.DigitAll2.service.PdfPaginaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TesteArquivoPdfService {
	@Autowired
	public ArquivoPdfService arquivoPdfService;
	
	@Autowired
	public PdfPaginaService pdfPaginaService;

	// @Autowired
	private ModelMapper modelMapper;
	
	
	
	@Test
	public void testeMontarArquivoSalvarEmDisco() throws IOException{
		// garantido que no banco haverá apenas o arquivo que iremos salvar
		for (ArquivoPdf a : arquivoPdfService.getPdfs()) {
			arquivoPdfService.excluir(a);
		}
		// salvando no banco o arquivo que iremos dividir
		byte[] file = FileUtils.readFileToByteArray(new File("C:/sqlteste/multipage-txt.pdf"));
		ArquivoPdf arquivo = new ArquivoPdf();
		arquivo.setNome("multipage-txt");
		arquivo.setPdf(file);
		arquivo.setJaDivididEmPaginas(false);
		PDDocument documento=null;
		documento = PDDocument.load(file);
		if(documento.getNumberOfPages()>1){
			arquivo.setMultiPage(true);
		}else {
			arquivo.setMultiPage(false);
		}
		arquivo.setNumeroDepaginasDODocumento(documento.getNumberOfPages());
		arquivoPdfService.salvar(arquivo,arquivo.getNumeroDepaginasDODocumento());
		arquivoPdfService.ResizingFile();
		// parte nova
		String caminho="C:/sqlteste";
		String nome="testeMontaArquivo";
		ArquivoPdf p =arquivoPdfService.getPdfs().get(0);
		Integer id=p.getID();
		arquivoPdfService.MontarArquivoSalvarEmDisco(caminho, nome, id);
		byte[] file2 = FileUtils.readFileToByteArray(new File("C:/sqlteste/testeMontaArquivo.pdf"));

	}
	
	
	@Test
	public void testSalvarArquivoPdfRecuperarECompararComOriginal() throws Exception {
		// como incialmente não sabemos o id de um elemento autoincremente,
		// uma maneira prática de realizar este teste é garantir no banco
		// so haja o elemento que você esta tratando
		byte[] file = FileUtils.readFileToByteArray(new File("C:/multipage-txt.pdf"));
		// removendo todos os elementos do banco
		for (ArquivoPdf a : arquivoPdfService.getPdfs()) {
			arquivoPdfService.excluir(a);
		}
		// obtendo um PDDocument para contar o numero de páginas
		PDDocument document = null;
		document = PDDocument.load(file);
		int NumeroDeOPaginasDoDocumento=document.getNumberOfPages();
		// inserindo um único elemento no banco.
		arquivoPdfService.salvar(file, "multipageArray2-txt",NumeroDeOPaginasDoDocumento);
		
		// recupenrando o elemento que foi inserido para comparar os array de
		// bytes
		byte[] data= new byte [arquivoPdfService.getPdfs().get(0).getPdf().length];
		data = arquivoPdfService.getPdfs().get(0).getPdf();
		
		// Salvando o elmento que foi recuperando do banco em disco
		InputStream is = null;
		is = new ByteArrayInputStream(data);
		FileUtils.writeByteArrayToFile(new File("C:/sqlteste/multipage-txtRecuperadoDoDisco.pdf"), IOUtils.toByteArray(is));
		// comparando o array de bytes original, com o recuperando do elemento
		// salvo em disco
		// apos ser recuperado do banco
		assertArrayEquals(data, file);
	}

	
	@Test
	public void testSalvarArquivoPdf() throws Exception {
		byte[] file = FileUtils.readFileToByteArray(new File("C:/sqlteste/multipage-txt.pdf"));
		PDDocument document = null;
		document = PDDocument.load(file);
		int NumeroDeOPaginasDoDocumento=document.getNumberOfPages();
		ArquivoPdf a = new ArquivoPdf();
		a.setNome("multipage-txt");
		a.setPdf(file);
		arquivoPdfService.salvar(a,NumeroDeOPaginasDoDocumento);
		assertFalse(arquivoPdfService.getPdfs().contains(a));
	}


	@Test
	public void testExcluirArquivoPdf() throws Exception {
		ArquivoPdf arq = new ArquivoPdf();
		arq = arquivoPdfService.getPdfs().get(0);
		arquivoPdfService.excluir(arq);
		assertFalse(arquivoPdfService.getPdfs().contains(arq));
	}

	@Test
	public void testGetPdfs() throws Exception {
		Collection<ArquivoPdf> colecaoAq = arquivoPdfService.getPdfs();
		assertFalse(colecaoAq.isEmpty());
	}

	@Test
	public void testGetAPdfById() throws Exception {
		int id;
		List<ArquivoPdf> colecaoAq = new ArrayList<ArquivoPdf>();
		ArquivoPdf aQ = new ArquivoPdf();
		ArquivoPdf aQ1 = new ArquivoPdf();
		colecaoAq = arquivoPdfService.getPdfs();
		aQ = colecaoAq.get(0);
		id = aQ.getID();
		aQ1 = arquivoPdfService.getPdfById(id);
		assertTrue(aQ.getID().equals(aQ1.getID()));
	}
	

	@Test
	public void testConverterArquivoPdfParaArquivoPdfDTO() throws Exception {

		byte[] file = FileUtils.readFileToByteArray(new File("C:/sqlteste/multipage-txt.pdf"));
		
		ArquivoPdf arquivo = new ArquivoPdf();
		arquivo.setNome("multipage-txt");
		arquivo.setPdf(file);
		arquivo.setJaDivididEmPaginas(false);
		PDDocument documento=null;
		documento = PDDocument.load(file);
		if(documento.getNumberOfPages()>1){
			arquivo.setMultiPage(true);
		}else {
			arquivo.setMultiPage(false);
		}
		arquivo.setNumeroDepaginasDODocumento(documento.getNumberOfPages());
		
		ArquivoPdfDTO arquivoDTO = arquivoPdfService.converteArquivoPdfParaArquivoPdfDTO(arquivo);
		
		byte [] byteDto=arquivoDTO.getPdf();
		byte [] byteAquivo=arquivo.getPdf();
		assertEquals(arquivo.getNome(), arquivoDTO.getNome());
		assertArrayEquals(byteDto, byteAquivo);

	}
	

	@Test
	public void testConverterArquivoPdfDTOParaArquivoPdf() throws Exception {

		byte[] file = FileUtils.readFileToByteArray(new File("C:/sqlteste/multipage-txt.pdf"));
		
		ArquivoPdfDTO arquivoDTO = new ArquivoPdfDTO();
		arquivoDTO.setNome("multipage-txt");
		arquivoDTO.setPdf(file);
		arquivoDTO.setJaDivididEmPaginas(false);
		PDDocument documento=null;
		documento = PDDocument.load(file);
		if(documento.getNumberOfPages()>1){
			arquivoDTO.setMultiPage(true);
		}else {
			arquivoDTO.setMultiPage(false);
		}
		arquivoDTO.setNumeroDepaginasDODocumento(documento.getNumberOfPages());
		
		ArquivoPdf arquivo = arquivoPdfService.converterArquivoPdfDTOParaArquivoPdf(arquivoDTO);
		
		byte [] byteDto=arquivoDTO.getPdf();
		byte [] byteAquivo=arquivo.getPdf();
		assertEquals(arquivo.getNome(), arquivoDTO.getNome());
		assertArrayEquals(byteDto, byteAquivo);

	} 

	@Test
	public void testRsizingFile() throws Exception {
		// garantido que no banco haverá apenas o arquivo que iremos salvar
		for (ArquivoPdf a : arquivoPdfService.getPdfs()) {
			arquivoPdfService.excluir(a);
		}
		// salvando no banco o arquivo que iremos dividir
		byte[] file = FileUtils.readFileToByteArray(new File("C:/sqlteste/multipage-txt.pdf"));
		ArquivoPdf arquivo = new ArquivoPdf();
		arquivo.setNome("multipage-txt");
		arquivo.setPdf(file);
		arquivo.setJaDivididEmPaginas(false);
		PDDocument documento=null;
		documento = PDDocument.load(file);
		if(documento.getNumberOfPages()>1){
			arquivo.setMultiPage(true);
		}else {
			arquivo.setMultiPage(false);
		}
		arquivo.setNumeroDepaginasDODocumento(documento.getNumberOfPages());
		arquivoPdfService.salvar(arquivo,arquivo.getNumeroDepaginasDODocumento());
		arquivoPdfService.ResizingFile();
		
		for (PdfPagina p : pdfPaginaService.getPaginas()) {
			assertEquals(p.getIdOriginalDocument(),arquivoPdfService.getPdfs().get(0).getID());
		}
		assertEquals(documento.getNumberOfPages(),pdfPaginaService.getPaginas().size());
	}
}
