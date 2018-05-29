package br.DigitAll2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.DigitAll2.entity.ArquivoPdf;
/**
 * 
 * @author jorge
 *
 */
@Repository
public interface ArquivoPdfRepository extends JpaRepository<ArquivoPdf, Integer> {


	
}
