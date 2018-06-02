package br.DigitAll2.dto;

import br.DigitAll2.entity.ArquivoPdf;

public class PdfPaginaDTO {

	private Integer id;
	private Integer idOriginalDocument;
	private ArquivoPdf DocumentOriginal;
	private Integer numeroDepaginasDODocumento;
	private Integer idNextPage;
	private String nome;
	private Integer numeroDaPagina;
	private byte[] pdf;
	private boolean isStartPage;
	
	
	
	public PdfPaginaDTO() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
//	public Integer getIdOriginalDocument() {
//		return idOriginalDocument;
//	}
	public void setIdOriginalDocument(Integer idOriginalDocument) {
		this.idOriginalDocument = idOriginalDocument;
	}
	public ArquivoPdf getDocumentOriginal() {
		return DocumentOriginal;
	}
	public void setDocumentOriginal(ArquivoPdf documentOriginal) {
		DocumentOriginal = documentOriginal;
	}
	public Integer getNumeroDepaginasDODocumento() {
		return numeroDepaginasDODocumento;
	}
	public void setNumeroDepaginasDODocumento(Integer numeroDepaginasDODocumento) {
		this.numeroDepaginasDODocumento = numeroDepaginasDODocumento;
	}
	public Integer getIdNextPage() {
		return idNextPage;
	}
	public void setIdNextPage(Integer idNextPage) {
		this.idNextPage = idNextPage;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getNumeroDaPagina() {
		return numeroDaPagina;
	}
	public void setNumeroDaPagina(Integer numeroDaPagina) {
		this.numeroDaPagina = numeroDaPagina;
	}
	public byte[] getPdf() {
		return pdf;
	}
	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}
	public boolean isStartPage() {
		return isStartPage;
	}
	public void setStartPage(boolean isStartPage) {
		this.isStartPage = isStartPage;
	}
	
	
}
