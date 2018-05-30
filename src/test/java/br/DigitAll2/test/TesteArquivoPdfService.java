package br.DigitAll2.test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.DigitAll2.dto.ArquivoPdfDTO;
import br.DigitAll2.entity.ArquivoPdf;
import br.DigitAll2.service.ArquivoPdfService;


@RunWith(SpringRunner.class)
@SpringBootTest		
public class TesteArquivoPdfService {
		@Autowired
		public ArquivoPdfService arquivoPdfService;// alus;
			
		//@Autowired
		private ModelMapper modelMapper;
		
		@Test
		public void testSalvarArquivoPdf()throws Exception {
		    byte[] file = FileUtils.readFileToByteArray(new File(
		    		"C:/sqlteste/multipage-txt.pdf"));
		    PDDocument document = null;
	        document = PDDocument.load(file);
			ArquivoPdf a = new ArquivoPdf();
			a.setNome("multipage-txt");
			a.setPdf(document);
			a.setStartPage(false);
	        arquivoPdfService.salvar(a);
	        assertFalse(arquivoPdfService.getPdfs().contains(a));
		}
		
		@Test
		public void testSalvarArquivoPdfArray()throws Exception {
			// como incialmente não sabemos o id de um elemento autoincremente,
			// uma maneira prática de realizar este teste é garantir no banco
			// so haja o elemento que você esta tratando
		    byte[] file = FileUtils.readFileToByteArray(new File(
		    		"C:/multipage-txt.pdf"));
		    //removendo todos os elementos do banco
		    for (ArquivoPdf a:arquivoPdfService.getPdfs()){
		    	arquivoPdfService.excluir(a);
		    }
			//tranformando o PDDdocumente em array de bytes
		    PDDocument document = null;
	        document = PDDocument.load(file);
		    ByteArrayOutputStream out = null;
		    out=new ByteArrayOutputStream();
		    document.save(out);
	        byte[] data = out.toByteArray();
	        //inserindo um único elemento no banco.
	        arquivoPdfService.salvar(data,"multipageArray2-txt");
	        //recupenrando o elemento que foi inserido para comparar os array de bytes
	        ByteArrayOutputStream out2 = null;
	        out2 = new ByteArrayOutputStream();
	        PDDocument pdd=new PDDocument();	        
	        pdd=arquivoPdfService.getPdfs().get(0).getPdf();
	        pdd.save(out2);
	        byte[] data2 = out2.toByteArray();
	        //Salvando o elmento que foi recuperando do banco em disco
		    InputStream is = null;
	        is = new ByteArrayInputStream(data2);
	        FileUtils.writeByteArrayToFile(new File(
	        		"C:/sqlteste/multipage-txt2.pdf"), IOUtils.toByteArray(is));
	        //recuperando o elemento que foi salvo em disco e tranformando ele outra vez em array de bytes
		    byte[] file3 = FileUtils.readFileToByteArray(new File(
		    		"C:/sqlteste/multipage-txt2.pdf"));
		    PDDocument document3 = null;
	        document3 = PDDocument.load(file3);
		    ByteArrayOutputStream out3 = null;
		    out3=new ByteArrayOutputStream();
		    document.save(out3);
	        byte[] data3 = out.toByteArray();
	        // comparando o array de bytes original, com o recuperando do elemento salvo em disco
	        // apos ser recuperado do banco
	        assertArrayEquals(data, data3);
		}

		
//		
		@Test
		public void testExcluirArquivoPdf()throws Exception{
			ArquivoPdf arq=new ArquivoPdf();
			arq= arquivoPdfService.getPdfs().get(0);
			arquivoPdfService.excluir(arq);
			assertFalse(arquivoPdfService.getPdfs().contains(arq));
		}
		
		@Test
		public void testGetAlunos()throws Exception{
			Collection<ArquivoPdf> colecaoAq = arquivoPdfService.getPdfs();
			assertFalse(colecaoAq.isEmpty());
		}
		
		@Test
		public void testGetAlunosPorId()throws Exception{
			int id ;
			List<ArquivoPdf> colecaoAq =new ArrayList<ArquivoPdf>();
			ArquivoPdf aQ=new ArquivoPdf();
			ArquivoPdf aQ1=new ArquivoPdf();
			colecaoAq= arquivoPdfService.getPdfs();
			aQ=colecaoAq.get(0);
			id=aQ.getID();
			aQ1=arquivoPdfService.getPdfById(id);
			assertTrue(aQ.getID().equals(aQ1.getID()));
		}
		
		@Test
		public void testConverterAlunoParaDTO()throws Exception{
					
			   byte[] file = FileUtils.readFileToByteArray(new File(
			    		"C:/sqlteste/multipage-txt.pdf"));
			    PDDocument document = null;
		        document = PDDocument.load(file);
				ArquivoPdf arquivo = new ArquivoPdf();
				arquivo.setNome("multipage-txt");
				arquivo.setPdf(document);
				arquivo.setStartPage(false);
				ArquivoPdfDTO arquivoDTO =	arquivoPdfService.converteArquivoPdfParaArquivoPdfDTO(arquivo);
			assertEquals(arquivo.getNome(), arquivoDTO.getNome());
			assertEquals(arquivo.getPdf(), arquivoDTO.getPdf());
			assertEquals(arquivo.isStartPage(), arquivoDTO.isStartPage());
			
		}
//		
//		@Test
//		public void testConverterAlunoDTOParaEntidade()throws Exception{
//			
//			AlunoDTO alunoDTO = new AlunoDTO();
//			alunoDTO.setNome("Tatiana Melo");
//			alunoDTO.setCpf(1234567825);
//			alunoDTO.setEmail("tati@gmail.com");
//			alunoDTO.setAreaDePreferencia("ARQ");
//			alunoDTO.setSenha("tati90");
//			alunoDTO.setAnoIngresso(2015);
//			alunoDTO.setSemestreIngresso(1);
//			alunoDTO.setQtdPeriodosTrancados(0);
//			
//			AlunoSisa aluno = modelMapper.map(alunoDTO, AlunoSisa.class);
//			
//			assertEquals(alunoDTO.getNome(), aluno.getNome());
//			assertEquals(alunoDTO.getEmail(), aluno.getEmail());
//			assertEquals(alunoDTO.getSenha(), aluno.getSenha());
//			assertEquals(alunoDTO.getAreaDePreferencia(), aluno.getAreaDePreferencia());
//		}
	

}
