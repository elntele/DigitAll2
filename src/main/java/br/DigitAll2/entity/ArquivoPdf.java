package br.DigitAll2.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


/**
 * 
 * @author jorge esta classe é uma especie de nó de uma lista encadeada.
 */
@Entity
@Table(name = "pdf")
//@Component
public class ArquivoPdf implements Serializable {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "arquivoPdf_sequence", sequenceName = "arquivoPdf_sequence")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "arquivoPdf_sequence")
	private Integer id;

	/**
	 * caso o id deste atributo seja o id do proprio objeto significa que ele
	 * não esta partido. caso não seja o id dele, então ele é parte de um outro
	 * documento
	 * 
	 * @return
	 */
	@Column(nullable = true)
	private Integer idOriginalDocument;

	/**
	 * caso o id deste atributo, seja ele mesmo, sigifica que ele não tem
	 * proxima pagina. pode ser a última, ou ele pode ser um documento não
	 * partido.
	 */
	@Column(nullable = true)
	private Integer idNextPage;
	/**
	 * terá vários documentos com o mesmo nome porem com id diferente
	 */
	@Column(nullable = false)
	private String nome;

	@Column(nullable = true)
	private Integer numeroDaPagina;

	@Column(nullable = true)
	private boolean isJaEncadeado;

	@Column(nullable = true)
	private boolean isMultiPage;

	// @Column(nullable=false)
	// @OneToMany(mappedBy="morePages", fetch=FetchType.LAZY,
	// orphanRemoval=true)
	// @Cascade(CascadeType.REMOVE)
	// private List<PDDocument> pages;
	/**
	 * é o pdf em si, sendo ele com uma ou n paginas
	 */

	// private Oid oid;
	@Column(nullable = false)
	private byte[] pdf;
	/**
	 * caso este seja a primeira pagina de um arquivo dividido em páginas
	 */
	@Column(nullable = false)
	private boolean isStartPage;

	public ArquivoPdf() {
		super();
	}

	public Integer getID() {
		return id;
	}

	public void setID(Integer iD) {
		id = iD;
	}

	public Integer getIdOriginalDocument() {
		return idOriginalDocument;
	}

	public void setIdOriginalDocument(Integer idOriginalDocument) {
		idOriginalDocument = idOriginalDocument;
	}

	public Integer getIdNextPage() {
		return idNextPage;
	}

	public void setIdNextPage(Integer idNextPage) {
		idNextPage = idNextPage;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	// public List<PDDocument> getPages() {
	// return pages;
	// }

	// public void setPages(List<PDDocument> pages) {
	// pages = pages;
	// }

	public PDDocument getPdf() throws InvalidPasswordException, IOException {
		PDDocument p = null;
		p.load(this.pdf);
		return p;
	}

	public void setPdf(PDDocument pdf) {
		PDDocument document = null;
		InputStream is = null;
		ByteArrayOutputStream out = null;
		try {
			document = pdf;
			out = new ByteArrayOutputStream();
			document.save(out);
			byte[] data = out.toByteArray();
			this.pdf = data;
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	public boolean isJaEncadeado() {
		return isJaEncadeado;
	}

	public void setJaEncadeado(boolean isJaEncadeado) {
		this.isJaEncadeado = isJaEncadeado;
	}

	public boolean isMultiPage() {
		return isMultiPage;
	}

	public void setMultiPage(boolean isMultiPage) {
		this.isMultiPage = isMultiPage;
	}

	public boolean isStartPage() {
		return isStartPage;
	}

	public void setStartPage(boolean isStartPage) {
		this.isStartPage = isStartPage;
	}

	public Integer getNumeroDaPagina() {
		return numeroDaPagina;
	}

	public void setNumeroDaPagina(Integer numeroDaPagina) {
		this.numeroDaPagina = numeroDaPagina;
	}

}
