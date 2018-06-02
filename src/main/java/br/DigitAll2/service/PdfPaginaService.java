package br.DigitAll2.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.DigitAll2.dto.PdfPaginaDTO;
import br.DigitAll2.entity.PdfPagina;
import br.DigitAll2.repository.PdfPaginaRepository;

@Service

public class PdfPaginaService {

	@Autowired
	private PdfPaginaRepository pdfPaginaRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ArquivoPdfService arquivoPdfService;


	public PdfPagina salvarPagina(PdfPagina pdf) throws InvalidPasswordException, IOException {
		pdf.setNumeroDepaginasDODocumento(1);

		return pdfPaginaRepository.save(pdf);
	}

	public List<PdfPagina> getPaginas() {
		return pdfPaginaRepository.findAll();
	}

	public List<PdfPagina> invertSortList(List<PdfPagina> l) {
		List<PdfPagina> invertedSortList = new ArrayList<PdfPagina>();

		int maiorPagina = this.arquivoPdfService.getPdfById(l.get(0).getIdOriginalDocument())
				.getNumeroDepaginasDODocumento();
		int i = 0;
		while (maiorPagina > 0) {
			if (l.get(i).getNumeroDaPagina() == maiorPagina) {
				invertedSortList.add(l.get(i));
				maiorPagina -= 1;
				i = 0;
			} else {
				i += 1;
			}
		}

		return invertedSortList;

	}

	/**
	 * método seta a lista encadeada dos arquivos multipage quando divididos em
	 * páginas
	 * 
	 * @throws IOException
	 * @throws InvalidPasswordException
	 * 
	 */

	public void setAllId() throws InvalidPasswordException, IOException {
		List<PdfPagina> pdfList = new ArrayList<>();
		pdfList = this.getPaginas();
		List<List<PdfPagina>> grupoList = new ArrayList<>();
		// separando em uma lista de listas pelo id do documento original
		while (pdfList.size() > 0) {
			List<PdfPagina> inputPdfList = new ArrayList<PdfPagina>();
			int idOrig = pdfList.get(0).getIdOriginalDocument();
			for (PdfPagina p : pdfList) {
				if (p.getIdOriginalDocument().equals(idOrig)) {
					inputPdfList.add(p);
				}
			}
			grupoList.add(inputPdfList);
			pdfList.removeAll(inputPdfList);
		}
		// setando em todas as paginas o id da proxima pagina.
		for (List<PdfPagina> l : grupoList) {
			// invertendo a lista pelo numero da pagina
			l = this.invertSortList(l);
			int tamanhoL = l.size();
			for (int i = 0; i < tamanhoL; i++) {
				if (i < tamanhoL - 1) {
					l.get(i).setIdNextPage(l.get(i + 1).getId());
				} else {
					l.get(i).setIdNextPage(l.get(0).getId());
				}

				this.salvarPagina(l.get(i));

			}
		}

	}

	public void excluir(PdfPagina pdf) {
		pdfPaginaRepository.delete(pdf);
	}

	public PdfPaginaDTO convertePdfpaginaParaPdfPaginaDTO(PdfPagina pagina) {
		PdfPaginaDTO pdfPaginaDTO = new PdfPaginaDTO();
		pdfPaginaDTO = modelMapper.map(pagina, PdfPaginaDTO.class);
		return pdfPaginaDTO;
	}

	public PdfPagina converterPdfPaginaDTOParaPdfPagina(PdfPaginaDTO paginaDTO) {
		PdfPagina Pagina = new PdfPagina();
		Pagina = modelMapper.map(paginaDTO, PdfPagina.class);
		return Pagina;
	}

}
