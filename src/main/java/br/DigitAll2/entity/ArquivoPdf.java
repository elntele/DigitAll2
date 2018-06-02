package br.DigitAll2.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


/**
 * 
 * @author jorge esta classe é uma especie de nó de uma lista encadeada.
 */
@Entity
@Table(name = "pdf")
public class ArquivoPdf implements Serializable {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_DocumentOriginal")
	@SequenceGenerator(name = "arquivoPdf_sequence", sequenceName = "arquivoPdf_sequence")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "arquivoPdf_sequence")
	private Integer id;
	
	@Column(nullable = false)
	private Integer numeroDepaginasDODocumento;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private boolean isJaDivididEmPaginas;

	@Column(nullable = false)
	private boolean isMultiPage;

	@Column(nullable = false)
	private byte[] pdf;
	
//	@OneToMany(mappedBy="DocumentOriginal")
	@OneToMany(mappedBy="DocumentOriginal", fetch=FetchType.EAGER, orphanRemoval=true)
	@Cascade(CascadeType.REMOVE)
	private List <PdfPagina> listaDePaginas= new ArrayList<>();



	public ArquivoPdf() {
		super();
	}
	
	public void setJaDivididEmPaginas(boolean isJaDivididEmPaginas) {
		this.isJaDivididEmPaginas = isJaDivididEmPaginas;
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

	public boolean isJaDivididEmPaginas() {
		return isJaDivididEmPaginas;
	}

	public List<PdfPagina> getListaDePaginas() {
		return listaDePaginas;
	}

	public void setListaDePaginas(List<PdfPagina> listaDePaginas) {
		this.listaDePaginas = listaDePaginas;
	}
	
	
	
}
