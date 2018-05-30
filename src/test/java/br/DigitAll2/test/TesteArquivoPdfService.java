package br.DigitAll2.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.itextpdf.text.log.SysoCounter;

import br.DigitAll2.dto.ArquivoPdfDTO;
import br.DigitAll2.entity.ArquivoPdf;
import br.DigitAll2.service.ArquivoPdfService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TesteArquivoPdfService {
	@Autowired
	public ArquivoPdfService arquivoPdfService;// alus;

	// @Autowired
	private ModelMapper modelMapper;

	@Test
	public void testSalvarArquivoPdf() throws Exception {
		byte[] file = FileUtils.readFileToByteArray(new File("C:/sqlteste/multipage-txt.pdf"));
		PDDocument document = null;
		document = PDDocument.load(file);
		int NumeroDeOPaginasDoDocumento=document.getNumberOfPages();
		ArquivoPdf a = new ArquivoPdf();
		a.setNome("multipage-txt");
		a.setPdf(document);
		a.setStartPage(false);
		arquivoPdfService.salvar(a,NumeroDeOPaginasDoDocumento);
		assertFalse(arquivoPdfService.getPdfs().contains(a));
	}

	@Test
	public void testSalvarArquivoPdfArray() throws Exception {
		// como incialmente não sabemos o id de um elemento autoincremente,
		// uma maneira prática de realizar este teste é garantir no banco
		// so haja o elemento que você esta tratando
		byte[] file = FileUtils.readFileToByteArray(new File("C:/multipage-txt.pdf"));
		// removendo todos os elementos do banco
		for (ArquivoPdf a : arquivoPdfService.getPdfs()) {
			arquivoPdfService.excluir(a);
		}
		// tranformando o PDDdocumente em array de bytes
		PDDocument document = null;
		document = PDDocument.load(file);
		int NumeroDeOPaginasDoDocumento=document.getNumberOfPages();
		ByteArrayOutputStream out = null;
		out = new ByteArrayOutputStream();
		document.save(out);
		byte[] data = out.toByteArray();
		// inserindo um único elemento no banco.
		arquivoPdfService.salvar(data, "multipageArray2-txt",NumeroDeOPaginasDoDocumento);
		// recupenrando o elemento que foi inserido para comparar os array de
		// bytes
		ByteArrayOutputStream out2 = null;
		out2 = new ByteArrayOutputStream();
		PDDocument pdd = new PDDocument();
		pdd = arquivoPdfService.getPdfs().get(0).getPdf();
		pdd.save(out2);
		byte[] data2 = out2.toByteArray();
		// Salvando o elmento que foi recuperando do banco em disco
		InputStream is = null;
		is = new ByteArrayInputStream(data2);
		FileUtils.writeByteArrayToFile(new File("C:/sqlteste/multipage-txt2.pdf"), IOUtils.toByteArray(is));
		// recuperando o elemento que foi salvo em disco e tranformando ele
		// outra vez em array de bytes
		byte[] file3 = FileUtils.readFileToByteArray(new File("C:/sqlteste/multipage-txt2.pdf"));
		PDDocument document3 = null;
		document3 = PDDocument.load(file3);
		ByteArrayOutputStream out3 = null;
		out3 = new ByteArrayOutputStream();
		document.save(out3);
		byte[] data3 = out.toByteArray();
		// comparando o array de bytes original, com o recuperando do elemento
		// salvo em disco
		// apos ser recuperado do banco
		assertArrayEquals(data, data3);
	}

	//
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
		PDDocument document = null;
		document = PDDocument.load(file);
		ArquivoPdf arquivo = new ArquivoPdf();
		arquivo.setNome("multipage-txt");
		arquivo.setPdf(document);
		arquivo.setStartPage(false);
		ArquivoPdfDTO arquivoDTO = arquivoPdfService.converteArquivoPdfParaArquivoPdfDTO(arquivo);
		PDDocument pdf = arquivo.getPdf();
		PDDocument pdfDTO = arquivoDTO.getPdf();
		ByteArrayOutputStream out = null;
		ByteArrayOutputStream outDTO = null;
		out = new ByteArrayOutputStream();
		outDTO = new ByteArrayOutputStream();
		pdf.save(out);
		pdfDTO.save(outDTO);
		byte[] data = out.toByteArray();
		byte[] dataDTO = outDTO.toByteArray();
		assertEquals(arquivo.getNome(), arquivoDTO.getNome());
		assertArrayEquals(data, dataDTO);
		assertEquals(arquivo.isStartPage(), arquivoDTO.isStartPage());

	}

	@Test
	public void testConverterArquivoPdfDTOParaArquivoPdf() throws Exception {

		byte[] file = FileUtils.readFileToByteArray(new File("C:/sqlteste/multipage-txt.pdf"));
		PDDocument document = null;
		document = PDDocument.load(file);
		ArquivoPdfDTO arquivoDTO = new ArquivoPdfDTO();
		arquivoDTO.setNome("multipage-txt");
		arquivoDTO.setPdf(document);
		arquivoDTO.setStartPage(false);
		ArquivoPdf arquivo = arquivoPdfService.converterArquivoPdfDTOParaArquivoPdf(arquivoDTO);
		PDDocument pdf = arquivo.getPdf();
		PDDocument pdfDTO = arquivoDTO.getPdf();
		ByteArrayOutputStream out = null;
		ByteArrayOutputStream outDTO = null;
		out = new ByteArrayOutputStream();
		outDTO = new ByteArrayOutputStream();
		pdf.save(out);
		pdfDTO.save(outDTO);
		byte[] data = out.toByteArray();
		byte[] dataDTO = outDTO.toByteArray();
		assertEquals(arquivo.getNome(), arquivoDTO.getNome());
		// assertArrayEquals(dataDTO, data);
		assertEquals(arquivo.isStartPage(), arquivoDTO.isStartPage());

	}

	@Test
	public void testRsizingFile() throws Exception {

		// garantido que no banco haverá apenas o arquivo que iremos salvar
		for (ArquivoPdf a : arquivoPdfService.getPdfs()) {
			arquivoPdfService.excluir(a);
		}

		// salvando no banco o arquivo que iremos dividir
		byte[] file = FileUtils.readFileToByteArray(new File("C:/sqlteste/multipage-txt.pdf"));
//		File filenovo = new File("C:/sqlteste/multipage-img.pdf");
//		PDFParser parser = new PDFParser(new RandomAccessBufferedFileInputStream(filenovo));

//		parser.parse();
//		COSDocument cosDoc = parser.getDocument();
//		PDFTextStripper pdfStripper = new PDFTextStripper();
		PDDocument document = new PDDocument(/*cosDoc*/);
//		PDDocument document=null;
		document = PDDocument.load(file);
		int NumeroDeOPaginasDoDocumento= document.getNumberOfPages();
		System.out.println("numero de pagina "+ document.getNumberOfPages() );
		
		ArquivoPdf arquivo = new ArquivoPdf();
		arquivo.setNome("multipage-txt");
		arquivo.setPdf(document);
		arquivo.setStartPage(false);
		arquivoPdfService.salvar(arquivo,NumeroDeOPaginasDoDocumento);
		Integer id = arquivoPdfService.getPdfs().get(0).getID();
		arquivoPdfService.ResizingFile();
		arquivoPdfService.setAllId();
		List<ArquivoPdf> listArq = new ArrayList();
		for (ArquivoPdf a : arquivoPdfService.getPdfs()) {
			if (!a.getID().equals(id)) {
				listArq.add(a);
			}
		}
		ArquivoPdf pdfPg1 = new ArquivoPdf();
		ArquivoPdf pdfPg2 = new ArquivoPdf();
		ArquivoPdf pdfPgOriginal = new ArquivoPdf();
		pdfPgOriginal = arquivoPdfService.getPdfById(id);
		for (ArquivoPdf p : listArq) {
			if (p.isStartPage()) {
				pdfPg1 = p;
			} else {
				pdfPg2 = p;
			}
		}
		// preparando os Array de byte stream para serem passados para os
		// pddDocuments
		ByteArrayOutputStream outOriginal = null;
		ByteArrayOutputStream outPg1 = null;
		ByteArrayOutputStream outPg2 = null;
		outOriginal = new ByteArrayOutputStream();
		outPg1 = new ByteArrayOutputStream();
		outPg2 = new ByteArrayOutputStream();
		// preparando os pddDocuments para carregar os arrays de batestream
		PDDocument pddOriginal = new PDDocument();
		PDDocument pddPg1 = new PDDocument();
		PDDocument pddPg2 = new PDDocument();
		pddOriginal = pdfPgOriginal.getPdf();
		pddPg1 = pdfPg1.getPdf();
		pddPg2 = pdfPg2.getPdf();
		pddOriginal.save(outOriginal);
		pddPg1.save(outPg1);
		pddPg2.save(outPg2);
		// carregando os array de bytes
		byte[] dataOriginal = outOriginal.toByteArray();
		byte[] dataPg1 = outPg1.toByteArray();
		byte[] dataPg2 = outPg2.toByteArray();
		// Salvando o elmento que foi recuperando do banco em disco
		InputStream isOrigina = null;
		InputStream isPg1 = null;
		InputStream isPg2 = null;
		isOrigina = new ByteArrayInputStream(dataOriginal);
		FileUtils.writeByteArrayToFile(new File("C:/sqlteste/multipage-img_original.pdf"),
				IOUtils.toByteArray(isOrigina));
		isPg1 = new ByteArrayInputStream(dataPg1);
		FileUtils.writeByteArrayToFile(new File("C:/sqlteste/multipage-img_Pg1.pdf"), IOUtils.toByteArray(isPg1));
		isPg2 = new ByteArrayInputStream(dataPg2);
		FileUtils.writeByteArrayToFile(new File("C:/sqlteste/multipage-img_Pg1.pdf"), IOUtils.toByteArray(isPg2));
		// asserts de confirmação de sucesso das caracteristicas dos arquivo
		// gradados em banco
		assertEquals(pdfPg1.getIdOriginalDocument(), id);
		assertEquals(pdfPg2.getIdOriginalDocument(), id);
		assertEquals(pdfPg1.getIdNextPage(), pdfPg2.getID());

	}

}
