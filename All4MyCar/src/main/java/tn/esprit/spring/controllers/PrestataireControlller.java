package tn.esprit.spring.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.models.Client;
import tn.esprit.spring.models.Prestataire;
import tn.esprit.spring.models.User;
import tn.esprit.spring.services.ClientService;
import tn.esprit.spring.services.PrestataireService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value="/api")
public class PrestataireControlller {
	@Autowired
	PrestataireService prestataireservice ;
	
	
	
	           
	@PostMapping("/addprestataire")
	public String addPrestataire(@RequestBody Prestataire pres) {
		
		return prestataireservice.add_prestataire(pres);
	}
	
	/*@PutMapping("/Updateclient")
	public String UpdateClient(@RequestBody Client client) {

		return clientservice.edit_client(client);
	}*/
	
	
   @GetMapping("/FinduserrById")
	
	public User FindpresById() {
	   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String userName = ((UserDetails) principal).getUsername();
			System.err.println(prestataireservice.Afficher_prestataire_by_name(userName).getPrestataire());
			return prestataireservice.Afficher_prestataire_by_name(userName);
			
		}
		return null;
		
	}
   @GetMapping("/FindpresById")
	
	public Prestataire FinduserById() {
	   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String userName = ((UserDetails) principal).getUsername();
			System.err.println(prestataireservice.Afficher_prestataire_by_name(userName).getPrestataire());
			return prestataireservice.Afficher_prestataire_by_name(userName).getPrestataire();
			
		}
		return null;
		
	}
   
  /* @PutMapping("/edit_client")
	
	public String edit_client(@RequestBody Client clt) {
	   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String userName = ((UserDetails) principal).getUsername();
			//System.err.println(clientservice.Afficher_client_by_name(userName).getClient());
			return clientservice.edit_client(clientservice.Afficher_client_by_name(userName).getClient(),clt);
			
		}
		return null;
		
	}*/
   
   private static String UPLOADED_FOLDER = System.getProperty("user.dir")+"/src/main/resources";	

   @PostMapping("/edit_prestataire1/{firstNamepres}/{lastNamepres}/{adressepres}/{adresseprof}/{emailpres}/{telpres}/{specialisations}/{cin}")
	
	public String edit_presss(@PathVariable(value = "firstNamepres") String firstNamepres ,@PathVariable(value = "lastNamepres") String lastNamepres ,@PathVariable(value = "adressepres") String adressepres ,
			@PathVariable(value = "adresseprof") String adresseprof , @PathVariable(value = "emailpres") String emailpres , @PathVariable(value = "telpres")  int telpres ,@PathVariable(value = "specialisations") String specialisation,
			@PathVariable(value ="cin")  int cin,@RequestParam("file") MultipartFile file
			 )
   {
	   Prestataire pres = new Prestataire();
	   
	      
	      File dir = new File(UPLOADED_FOLDER);
	      if (!dir.exists())
				dir.mkdirs();
	      System.out.println("c bnsssssssssssssssssssaaaaaas ");
	      File fileToImport = null;
	      if (dir.isDirectory()) {
	    	  System.out.println("c bnssssssssssssssssssssssssssssssssss ");
	    	  try {
		        	
		        	System.out.println("c bn ");
		        	
		            fileToImport = new File(dir + File.separator + file.getOriginalFilename());
		            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileToImport));
		            stream.write(file.getBytes());
		            stream.close();
		        } catch (Exception e) {
		            System.out.println("nnnnnnnnn");
		        }
	      }
		   pres.setFirstNamepres(firstNamepres);
		   pres.setLastNamepres(lastNamepres);
	       pres.setAdressepres(adressepres);
	       pres.setAdresseprof(adresseprof);
	       pres.setEmailpres(emailpres);

	       pres.setTelpres(telpres);
	       pres.setSpecialisations(specialisation);
	       pres.setCIN(cin);
	        pres.setPhotopres(file.getOriginalFilename());
	  // clt.setPhotoclt(file.getOriginalFilename());
	   
	   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String userName = ((UserDetails) principal).getUsername();
		//	System.err.println(clientservice.Afficher_client_by_name(userName).getClient());
			
			return prestataireservice.edit_prestataire(prestataireservice.Afficher_prestataire_by_name(userName).getPrestataire(), pres);
			
		}
		return null;
		
	}
   
   @GetMapping(value = "/api/image/logo/{image}")
   public ResponseEntity<InputStreamResource> getImage(@PathVariable(value = "image") String image ) throws IOException {

       ClassPathResource imgFile = new ClassPathResource(image);

       return ResponseEntity
               .ok()
               .contentType(MediaType.IMAGE_JPEG)
               .body(new InputStreamResource(imgFile.getInputStream()));
   }
   
  
}