package br.DigitAll2.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

public class Teste {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
		
		
		    byte[] file = FileUtils.readFileToByteArray(new File(
		    		"C:/multipage-txt.pdf"));

		    PDDocument document = null;

		    InputStream is = null;
		    ByteArrayOutputStream out = null;
		    List<PDDocument> Pages=new ArrayList<PDDocument>();// dela
		    try {
		        document = PDDocument.load(file);
		        Splitter splitter = new Splitter(); //de la
			    Pages = splitter.split(document);// de la
			    
			    int i=1; // de la 
			    for (PDDocument P: Pages){ // de la 
			        out = new ByteArrayOutputStream();
			        P.save(out);
			        byte[] data = out.toByteArray();
			        is = new ByteArrayInputStream(data);
			        FileUtils.writeByteArrayToFile(new File(
			        		"C:/Users/jorge/Desktop/rural 2/Currículo/digitalworks/teste_pagina"+i+".pdf"), IOUtils.toByteArray(is));
			        i+=1;
			    } // de la

		        document.save(out);
		        byte[] data = out.toByteArray();
		        is = new ByteArrayInputStream(data);

		        FileUtils.writeByteArrayToFile(new File(
		        		"C:/Users/jorge/Desktop/rural 2/Currículo/digitalworks/teste.pdf"), IOUtils.toByteArray(is));
		    } finally {
		        IOUtils.closeQuietly(out);
		        IOUtils.closeQuietly(is);
//		        IOUtils.closeQuietly(document);
		    }
		}
		
		
		

	

}
