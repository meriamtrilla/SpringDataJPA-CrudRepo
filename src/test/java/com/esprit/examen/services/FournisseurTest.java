package com.esprit.examen.services;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.esprit.examen.entities.CategorieFournisseur;
import com.esprit.examen.entities.Fournisseur;

import com.esprit.examen.entities.DetailFournisseur;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@TestMethodOrder(OrderAnnotation.class)
public class FournisseurTest {
	@Autowired
	IFournisseurService fournisseurService;
	
	@Test
	@Order(1)
	public void testAddFournisseur()
	{
		List<Fournisseur> fournisseurs = fournisseurService.retrieveAllFournisseurs();
		Fournisseur f = new Fournisseur();
		int expected = fournisseurs.size();
		f = new Fournisseur(null, "FO1", "fournisseur produits laitier", CategorieFournisseur.ORDINAIRE, 
				 null, null, null);
		Fournisseur savedFournisseur = fournisseurService.addFournisseur(f);
		assertEquals(expected+1, fournisseurService.retrieveAllFournisseurs().size());
		assertNotNull(savedFournisseur.getLibelle());
		fournisseurService.deleteFournisseur(savedFournisseur.getIdFournisseur());
	}
	
	@Test
	@Order(2)
	public void  retrieveAllFournisseurs()
	{
		Fournisseur fournisseur = new Fournisseur();
		fournisseur = new Fournisseur(null, "FO1", "fournisseur produits laitier", CategorieFournisseur.ORDINAIRE, 
				 null, null, null);
	    fournisseurService.addFournisseur(fournisseur);
		List<Fournisseur> fournisseurs = fournisseurService.retrieveAllFournisseurs();
		assertNotNull(fournisseurs);
		for (Fournisseur f : fournisseurs) {
			log.info(" fournisseur : " + f.getCode());
		}
	}
	
	@Test
	@Order(3)
	public void updateFournisseur()
	{
		Fournisseur fournisseur = fournisseurService.retrieveFournisseur((long) 6);
		assertNotNull(fournisseur);
		
		DetailFournisseur df = new DetailFournisseur() ;
		df.setFournisseur(fournisseur);
		df.setEmail("fournisseur@gmail.com");
		df.setAdresse("tunis");
		df.setMatricule("FFFk");
		
		fournisseur.setDetailFournisseur(df);
		
		Fournisseur savedFourniseur = fournisseurService.updateFournisseur(fournisseur);
		assertNotNull(savedFourniseur);
		assertNotNull(savedFourniseur.getDetailFournisseur());
		
	}
	
	@Test
	@Order(4)
	public void deleteFournisseur() 
	{
		Fournisseur f = new Fournisseur(null, "FO2", "fournisseur produits xxxx", 
				CategorieFournisseur.CONVENTIONNE,  null, null, null);
		Fournisseur savedFournisseur = fournisseurService.addFournisseur(f);
		assertNotNull(savedFournisseur);
		fournisseurService.deleteFournisseur(savedFournisseur.getIdFournisseur());
		assertNull(fournisseurService.retrieveFournisseur(savedFournisseur.getIdFournisseur()));
		
	}	

}
