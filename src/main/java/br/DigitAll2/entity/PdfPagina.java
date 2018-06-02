package br.DigitAll2.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 
 * @author jorge esta classe é uma especie de nó de uma lista encadeada.
 */
@Entity
@Table(name = "pdfPaginas")

public class PdfPagina {
	
	
	private static final long serialVersionUID = 1L;


	@Id
	@Column(name = "id_Pdf_Pagina")
	@SequenceGenerator(name = "PdfPagina_sequence", sequenceName = "PdfPagina_sequence")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PdfPagina_sequence")
	private Integer id;

	@Column(nullable = false)
	private Integer idOriginalDocument;
	
//	@ManyToOne
//	@JoinColumn(name="id_DocumentOriginal",nullable=false)
	
	@ManyToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name="id_DocumentOriginal", referencedColumnName="id_DocumentOriginal")
	@Fetch(FetchMode.JOIN)
	private ArquivoPdf DocumentOriginal;
	
	
	@Column(nullable = false)
	private Integer numeroDepaginasDODocumento;

	@Column(nullable = true)
	private Integer idNextPage;

	
	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private Integer numeroDaPagina;


	// @Column(nullable=false)
	// @OneToMany(mappedBy="morePages", fetch=FetchType.LAZY,
	// orphanRemoval=true)
	// @Cascade(CascadeType.REMOVE)
	// private List<PDDocument> pages;

	@Column(nullable = false)
	private byte[] pdf;
	
	@Column(nullable = false)
	private boolean isStartPage;
	
	
	public PdfPagina() {
		super();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdOriginalDocument() {
		return idOriginalDocument;
	}
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
