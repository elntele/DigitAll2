package br.DigitAll2.test;
import static org.junit.Assert.assertFalse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
		    		"C:/multipage-txt.pdf"));
		    PDDocument document = null;
	        document = PDDocument.load(file);
			ArquivoPdf a = new ArquivoPdf();
			a.setNome("multipage-txt");
			a.setPdf(document);
			a.setStartPage(false);
	        arquivoPdfService.salvar(a);
	        assertFalse(arquivoPdfService.getPdfs().contains(a));
		}
		
//		@Test
//		public void testSalvarArquivoPdfArray()throws Exception {
//		    byte[] file = FileUtils.readFileToByteArray(new File(
//		    		"C:/multipage-txt.pdf"));
//		    PDDocument document = null;
//	        document = PDDocument.load(file);
//	        InputStream is = null;
//		    ByteArrayOutputStream out = null;
//		    document.save(out);
//	        byte[] data = out.toByteArray();
//	        arquivoPdfService.salvar(data,"multipageArray-txt");
//	        
//	        assertFalse(arquivoPdfService.getPdfs().contains(a));
//		}

		
//		
//		@Test
//		public void testExcluirAluno()throws Exception{
//			AlunoSisa alu = new AlunoSisa();
//			alu = alus.getAlunoPorId(1);
//			//alus.excluir(alu);
//			assertFalse(alus.getAlunos().contains(alu));
//		}
//		
//		@Test
//		public void testGetAlunos()throws Exception{
//			Collection<AlunoSisa> colecaoAlu = alus.getAlunos();
//			assertFalse(colecaoAlu.isEmpty());
//		}
//		
//		@Test
//		public void testGetAlunosPorId()throws Exception{
//			int id = 1;
//			Collection<AlunoSisa> colecaoAlu = alus.getAlunos();
//			alus.getAlunoPorId(id);
//			assertTrue(id!=0 && id<=colecaoAlu.size());
//		}
//		
//		@Test
//		public void testConverterAlunoParaDTO()throws Exception{
//					
//			AlunoSisa aluno = new AlunoSisa();
//			aluno.setNome("Tatiana Melo");
//			aluno.setCpf(1234567825);
//			aluno.setEmail("tati@gmail.com");
//			aluno.setAreaDePreferencia("Ensiso");
//			aluno.setSenha("tati90");
//			aluno.setAnoIngresso(2015);
//			aluno.setSemestreIngresso(1);
//			aluno.setQtdPeriodosTrancados(0);
//			
//			AlunoDTO alunoDTO = modelMapper.map(aluno, AlunoDTO.class);
//			
//			assertEquals(aluno.getNome(), alunoDTO.getNome());
//			assertEquals(aluno.getEmail(), alunoDTO.getEmail());
//			assertEquals(aluno.getSenha(), alunoDTO.getSenha());
//			assertEquals(aluno.getAreaDePreferencia(), alunoDTO.getAreaDePreferencia());
//			
//		}
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
