package br.DigitAll2.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.DigitAll2.dto.ArquivoPdfDTO;
import br.DigitAll2.entity.ArquivoPdf;
import br.DigitAll2.repository.ArquivoPdfRepository;

/**
 * 
 * @author jorge
 *
 */
@Service
//@Component
public class ArquivoPdfService {
	@Autowired
	private ArquivoPdfRepository arquivoPdfRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Regras de Negócio
	 */

	public ArquivoPdf salvar(ArquivoPdf pdf) {

		return arquivoPdfRepository.save(pdf);
	}

	public List<ArquivoPdf> invertSortList(List<ArquivoPdf> l) {
		List<ArquivoPdf> invertedSortList = new ArrayList<ArquivoPdf>();
		try {
			int i = this.getPdfById(l.get(0).getIdOriginalDocument()).getPdf().getNumberOfPages();
			int tamanhoDoDocumento = i;
			int maiorPagina = i;

			while (i < tamanhoDoDocumento) {
				if (l.get(i).getNumeroDaPagina() == maiorPagina) {
					invertedSortList.add(l.get(i));
					maiorPagina -= 1;
				}
				i += 1;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return invertedSortList;

	}
	
	/**
	 * método seta a lista encadeada dos arquivos multipage quando divididos em páginas
	 * 
	 */

	public void setAllId() {
		List<ArquivoPdf> pdfList = new ArrayList<ArquivoPdf>();
		List<ArquivoPdf> copyPdfList = new ArrayList<ArquivoPdf>();
		pdfList = this.getPdfs();
		copyPdfList.addAll(pdfList);

		for (ArquivoPdf a : pdfList) {
			try {
				// se o numero da pagina for igual a null, então, é o documento original
				if (!a.getNumeroDaPagina().equals(null) && a.getPdf().getNumberOfPages() > 0 && !a.isJaEncadeado()) {
					List<ArquivoPdf> miniListToPutId = new ArrayList<ArquivoPdf>();
					a.setJaEncadeado(true);
					this.salvar(a);
					for (ArquivoPdf aq : copyPdfList) {
						if (aq.getIdOriginalDocument() == a.getIdOriginalDocument()) {
							aq.setJaEncadeado(true);
							miniListToPutId.add(aq);
						}
					}
					// a lista será ordenada de forma invertida pelo numero da pagina	
					miniListToPutId = this.invertSortList(miniListToPutId);
					// updating toda a lista no banco
					this.salvar(miniListToPutId.get(0));
					for (int w = 0; w < miniListToPutId.size(); w++) {
						miniListToPutId.get(w + 1).setIdNextPage(miniListToPutId.get(w).getID());
						this.salvar(miniListToPutId.get(w + 1));
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}

	}
	/**
	 * método divide os arquivo de mais de uma pagina em vários
	 * arquivos de uma página
	 */

	public void ResizingFile() {
		List<ArquivoPdf> pdfList = new ArrayList<ArquivoPdf>();
		pdfList = this.getPdfs();
		for (ArquivoPdf p : pdfList) {
			try {
				if (p.getPdf().getNumberOfPages() > 1 && !p.isJaEncadeado()) {
					List<PDDocument> pages = new ArrayList<PDDocument>();
					Splitter splitter = new Splitter(); // de la
					pages = splitter.split(p.getPdf());// de la
					Integer nPage = 1;
					for (PDDocument d : pages) {
						ArquivoPdf A = new ArquivoPdf();
						A.setIdOriginalDocument(p.getID());
						A.setPdf(d);
						A.setNumeroDaPagina(nPage);
						A.setNome(p.getNome()+"panina"+nPage);
						this.salvar(A);
						if(nPage==1){
							A.setStartPage(true);
						}
						nPage += 1;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			this.setAllId();
		}
	}

	public ArquivoPdf salvar(byte[] pdf,String nome) {
		ArquivoPdf pdf1 = new ArquivoPdf();
		PDDocument pd = null;
		try {
			pd = PDDocument.load(pdf);
			pdf1.setNome(nome);
			pdf1.setPdf(pd);
			pdf1.setJaEncadeado(false);
		} catch (Exception e) {
			e.fillInStackTrace();
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
		ArquivoPdfDTO arquivoPdfDTO=new ArquivoPdfDTO();
		arquivoPdfDTO = modelMapper.map(pdf, ArquivoPdfDTO.class);
		return arquivoPdfDTO;
	}

	public ArquivoPdf converterArquivoPdfDTOParaArquivoPdf(ArquivoPdfDTO pdfDTO) {
		ArquivoPdf arquivoPdf=new ArquivoPdf();
		arquivoPdf = modelMapper.map(pdfDTO, ArquivoPdf.class);
		return arquivoPdf;
	}

}
