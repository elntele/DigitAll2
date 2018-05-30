package br.DigitAll2.dto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

/**
 * 
 * @author jorge
 *
 */
public class ArquivoPdfDTO {
	private Integer id;
	private Integer idOriginalDocument = null;
	private Integer idNextPage = null;
	private String nome;
	// private List<PDDocument> pages;
	private byte[] pdf;
	private boolean isJaEncadeado;

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

	private boolean isMultiPage;
	private boolean isStartPage;

	public ArquivoPdfDTO() {
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
	//
	// public void setPages(List<PDDocument> pages) {
	// pages = pages;
	// }

	public PDDocument getPdf() throws InvalidPasswordException, IOException {
		PDDocument p = new PDDocument();
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

}
