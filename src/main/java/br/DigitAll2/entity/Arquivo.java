package br.DigitAll2.entity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfReader;

public class Arquivo {

	private PDDocument pdfDocument = null;

	
	
//	public static  String arquivorecebido(File archive) {
//		PDDocument pdfDocument = null;
//	
//		try {
//			
//			pdfDocument = PDDocument.load(archive);
//			
//			PDFTextStripper stripper = new PDFTextStripper();
//			String texto = stripper.getText(pdfDocument);
//			
//			return texto;
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		} finally {
//			if (pdfDocument != null)
//				try {
//					pdfDocument.close();
//				} catch (IOException e) {
//					throw new RuntimeException(e);
//				}
//		}
//
//	}
	

	public static  String arquivorecebido(PDDocument pdfDocument) {
	
		try {
			
			PDFTextStripper stripper = new PDFTextStripper();
			String texto = stripper.getText(pdfDocument);
			
//			stripper.
			
			return texto;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (pdfDocument != null)
				try {
					pdfDocument.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}

	}

	
	
	@Autowired
	public static void outroteste(File file) throws InvalidPasswordException, IOException{
		List<PDDocument> Pages=new ArrayList<PDDocument>();
//		PDDocument.load(filePath);
		PDDocument document = new PDDocument().load(file);
	
		try {
		    Splitter splitter = new Splitter();
		    Pages = splitter.split(document);
		    int i=1;
//		    ObjectInputStream obj;
		    InputStream inputstream;
		    for (PDDocument P: Pages){
		    	
		    	inputstream=P.getDocument().createCOSStream().createInputStream();
		    	int data = inputstream.read();
		    	System.out.println("data"+ data);
                while (data != -1) {
                       System.out.println("char data: "+(char) data);

                              data = inputstream.read();

                }
                inputstream.close();
		    	String texto=arquivorecebido(P);
//		    	System.out.println("pagina "+i);
//		    	System.out.println(texto);
		    	i+=1;
		    }
		    
		}
		catch(Exception e) {
		    e.printStackTrace(); // print reason and line number where error exist
		}
	
		
	}
	
	

	

	/**
	 *
	 * Extrai o conteudo do arquivo indicado
	 * @throws IOException 
	 * @throws InvalidPasswordException 
	 *
	 */
	public static void main(String[] args) throws InvalidPasswordException, IOException {
		String caminho = "C:/multipage-txt.pdf" ;
		PRStream obj=null;
		//PRStream pr = new PRStream();
		
		File file =new File (caminho);
		
		//String texto=arquivorecebido(file);
		outroteste(file);
		PdfReader pdfReader;
		
		try {
			pdfReader = new PdfReader(caminho);
//			PRStream pr = new PRStream(pdfReader);
			List<PdfReader> Pages=new ArrayList<PdfReader>();
			pdfReader.getStreamBytesRaw(obj);
			System.out.println("numero de paginas: " + pdfReader.getNumberOfPages() );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
