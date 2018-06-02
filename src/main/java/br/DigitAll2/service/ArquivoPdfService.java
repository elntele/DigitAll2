package br.DigitAll2.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.DigitAll2.dto.ArquivoPdfDTO;
import br.DigitAll2.entity.ArquivoPdf;
import br.DigitAll2.entity.PdfPagina;
import br.DigitAll2.repository.ArquivoPdfRepository;

/**
 * 
 * @author jorge
 *
 */
@Service
public class ArquivoPdfService {
	@Autowired
	private ArquivoPdfRepository arquivoPdfRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PdfPaginaService pdfPaginaService;

	/**
	 * Regras de Negócio
	 * 
	 * @throws IOException
	 * @throws InvalidPasswordException
	 */

	public ArquivoPdf salvar(ArquivoPdf pdf, int numeroDePaginasDoDocumento)
			throws InvalidPasswordException, IOException {
		pdf.setNumeroDepaginasDODocumento(numeroDePaginasDoDocumento);

		if (numeroDePaginasDoDocumento > 1) {
			pdf.setMultiPage(true);
		}
		return arquivoPdfRepository.save(pdf);
	}

	public void salvarArquivoEmDisco(String caminho, Integer id) throws IOException {
		ArquivoPdf arq = this.getPdfById(id);
		byte[] data = arq.getPdf();
		InputStream is = null;
		is = new ByteArrayInputStream(data);
		FileUtils.writeByteArrayToFile(new File(caminho + "/" + arq.getNome() + ".pdf"), IOUtils.toByteArray(is));

	}

	@SuppressWarnings("deprecation")
	public void MontarArquivoSalvarEmDisco(String caminho, String nome, Integer id) throws IOException {
		ArquivoPdf arq = this.getPdfById(id);
		PDFMergerUtility merge = new PDFMergerUtility();
		List<PdfPagina> listaPagina = new ArrayList<>();
		listaPagina = arq.getListaDePaginas();
		for (PdfPagina pd : listaPagina) {
			byte[] data = arq.getPdf();
			InputStream is = null;
			is = new ByteArrayInputStream(data);
			merge.addSource(is);
		}
		merge.setDestinationFileName(caminho + "/" + nome + ".pdf");
		merge.mergeDocuments();

		// byte [] data=arq.getPdf();
		// InputStream is = null;
		// is = new ByteArrayInputStream(data);
		// FileUtils.writeByteArrayToFile(new
		// File(caminho+"/"+arq.getNome()+".pdf"),
		// IOUtils.toByteArray(is));

	}

	/**
	 * método divide os arquivo de mais de uma pagina em vários arquivos de uma
	 * página
	 * 
	 * @throws IOException
	 * @throws InvalidPasswordException
	 */

	public void ResizingFile() throws InvalidPasswordException, IOException {
		List<ArquivoPdf> pdfList = new ArrayList<ArquivoPdf>();
		pdfList = this.getPdfs();
		for (ArquivoPdf p : pdfList) {
			try {
				if (!p.isJaDivididEmPaginas() && p.isMultiPage()) {
					List<PDDocument> pages = new ArrayList<PDDocument>();
					Splitter splitter = new Splitter();
					PDDocument document = null;
					document = PDDocument.load(p.getPdf());
					pages = splitter.split(document);
					Integer nPage = 1;
					for (PDDocument d : pages) {
						PdfPagina pp = new PdfPagina();
						pp.setDocumentOriginal(p);
						pp.setNome(p.getNome() + "pg" + nPage);
						pp.setNumeroDaPagina(nPage);
						ByteArrayOutputStream out = null;
						out = new ByteArrayOutputStream();
						d.save(out);
						byte[] data = out.toByteArray();
						pp.setPdf(data);
						pp.setIdOriginalDocument(p.getID());
						pp.setNumeroDepaginasDODocumento(1);
						if (nPage == 1) {
							pp.setStartPage(true);
						} else {
							pp.setStartPage(false);
						}
						pdfPaginaService.salvarPagina(pp);
						nPage += 1;
					}
					p.setJaDivididEmPaginas(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			this.pdfPaginaService.setAllId();
		}
	}

	public ArquivoPdf salvar(byte[] pdf, String nome, int numeroDePaginas) {
		ArquivoPdf pdf1 = new ArquivoPdf();
		pdf1.setNome(nome);
		pdf1.setPdf(pdf);
		pdf1.setNumeroDepaginasDODocumento(numeroDePaginas);
		if (numeroDePaginas > 1) {
			pdf1.setMultiPage(true);
		}
		if (numeroDePaginas > 1) {
			pdf1.setMultiPage(true);
		}
		return arquivoPdfRepository.save(pdf1);
	}

	public List<ArquivoPdf> getPdfs() {
		return arquivoPdfRepository.findAll();
	}

	public void excluir(ArquivoPdf pdf) {
		arquivoPdfRepository.delete(pdf);
	}

	public ArquivoPdf getPdfById(Integer id) {
		return arquivoPdfRepository.findOne(id);
	}

	public ArquivoPdfDTO converteArquivoPdfParaArquivoPdfDTO(ArquivoPdf pdf) {
		ArquivoPdfDTO arquivoPdfDTO = new ArquivoPdfDTO();
		arquivoPdfDTO = modelMapper.map(pdf, ArquivoPdfDTO.class);
		return arquivoPdfDTO;
	}

	public ArquivoPdf converterArquivoPdfDTOParaArquivoPdf(ArquivoPdfDTO pdfDTO) {
		ArquivoPdf arquivoPdf = new ArquivoPdf();
		arquivoPdf = modelMapper.map(pdfDTO, ArquivoPdf.class);
		return arquivoPdf;
	}

}
