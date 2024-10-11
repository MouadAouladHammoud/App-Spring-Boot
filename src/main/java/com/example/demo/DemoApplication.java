package com.example.demo;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CompteRepository compteRepository;

	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SaleLineRepository saleLineRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner initData() {
		return args -> {
			// Insérer des utilisateurs initiaux
			User user1 = new User(null, "Mouad", "mouad@example.com", 25, null, null);
			User user2 = new User(null, "Ahmed", "ahmed@example.com", 40, null, null);
			userRepository.saveAll(List.of(user1, user2));
			// Créer et associer les comptes aux utilisateurs
			Compte compte1 = new Compte(user1);
			compte1.setReference("REF123");
			compteRepository.save(compte1);
			Compte compte2 = new Compte(user2);
			compte2.setReference("REF124");
			compteRepository.save(compte2);
			user1.setCompte(compte1);
			user2.setCompte(compte2);
			userRepository.save(user1);
			userRepository.save(user2);

			Sale sale1 = new Sale(null, "SALE001", new Date(), user1, null);
			Sale sale2 = new Sale(null, "SALE002", new Date(), user1, null);
			Sale sale3 = new Sale(null, "SALE003", new Date(), user2, null);
			saleRepository.saveAll(List.of(sale1, sale2, sale3));

			Product product1 = new Product(null, "Product_1", "PRODUCT001");
			Product product2 = new Product(null, "Product_2", "PRODUCT002");
			Product product3 = new Product(null, "Product_3", "PRODUCT003");
			productRepository.saveAll(List.of(product1, product2, product3));

			SaleLine saleLine1 = new SaleLine(null, 2, 10.99f, sale1, product1);
			SaleLine saleLine2 = new SaleLine(null, 3, 10.99f, sale1, product2);
			SaleLine saleLine3 = new SaleLine(null, 7, 10.99f, sale2, product1);
			SaleLine saleLine4 = new SaleLine(null, 1, 10.99f, sale2, product2);
			SaleLine saleLine5 = new SaleLine(null, 3, 10.99f, sale2, product3);
			SaleLine saleLine6 = new SaleLine(null, 3, 10.99f, sale3, product2);

			// En supposant que nous avons déjà un identifiant (id) de "Sale" et de "Product" qui est égal à 1L.
			//   Ici, nous pouvons même créer un "SaleLine" dans la base de données avec le "Sale" associé en indiquant simplement l'id de "Sale" dans saleLineRepository().
			//   Pas besoin d'initialiser l'objet "Sale" complet pour initialiser "SaleLine" afin de l'utiliser dans saleLineRepository() pour le créer dans la base de données.
			//   Car saleLineRepository().save() dans ce scénario a seulement besoin de l'id de "Sale". (voir le fichier: SaleLine.java => @JoinColumn(name = "sale_id") private Sale sale;)
			//   c'est le meme principe avec "Product".
			SaleLine saleLine7 = SaleLine.builder()
					.quantity(4)
					.unitPrice(12.33f)
					.sale(new Sale(1L, null, null, null, null))  // SaleLine.java => @JoinColumn(name = "sale_id") private Sale sale;
					.product(new Product(1L, null, null)) // SaleLine.java => @JoinColumn(name = "product_id") private Product product;
					.build();

			SaleLine saleLine8 = SaleLine.builder()
					.quantity(4)
					.unitPrice(12.33f)
					.sale(
							Sale.builder()
									.id(1L)
									.build()
					)
					.product(new Product(1L, null, null))
					.build();

			saleLineRepository.saveAll(List.of(saleLine1, saleLine2, saleLine3, saleLine4, saleLine5, saleLine6, saleLine7, saleLine8));
		};
	}

}
