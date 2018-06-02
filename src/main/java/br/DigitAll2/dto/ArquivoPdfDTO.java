package br.DigitAll2.dto;

//import br.DigitAll2.entity.PdfPagina;

/**
 * 
 * @author jorge
 *
 */
public class ArquivoPdfDTO {

	private Integer id;

	private Integer numeroDepaginasDODocumento;
	private String nome;
	private boolean isJaDivididEmPaginas;
	private boolean isMultiPage;
	private byte[] pdf;
//	private List <PdfPagina> listaDePaginas= new ArrayList<>();


	public boolean isJaDivididEmPaginas() {
		return isJaDivididEmPaginas;
	}

	public void setJaDivididEmPaginas(boolean isJaDivididEmPaginas) {
		this.isJaDivididEmPaginas = isJaDivididEmPaginas;
	}

	
	public ArquivoPdfDTO() {
		super();
	}
	
	public void setNumeroDepaginasDODocumento(Integer numeroDepaginasDODocumento) {
		this.numeroDepaginasDODocumento = numeroDepaginasDODocumento;
	}
	
	public Integer getNumeroDepaginasDODocumento() {
		return numeroDepaginasDODocumento;
	}

	public Integer getID() {
		return id;
	}

	public void setID(Integer iD) {
		id = iD;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public byte [] getPdf() {
		return this.pdf;
	}


	public void setPdf(byte[] data) {
			this.pdf = data;
	}

	public boolean isMultiPage() {
		return isMultiPage;
	}

	public void setMultiPage(boolean isMultiPage) {
		this.isMultiPage = isMultiPage;
	}

}
