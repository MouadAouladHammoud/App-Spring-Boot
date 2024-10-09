package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String email;
    Integer age;

    // "mappedBy" est utilisé uniquement dans les relations @OneToOne et @OneToMany, mais pas dans @ManyToOne.
    // "mappedBy" est utilisé une seule fois dans une association entre deux entités. Par exemple ici, il doit être utilisé soit dans "Compte", soit dans "User" dans l'association entre "User" et "Compte", même si les deux entités ont l'annotation @OneToOne.
    // Puisque nous avons utilisé "mappedBy" dans "User", "Compte" ne doit donc pas avoir "mappedBy" dans l'association.
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // mappedBy = "user" => fait référence à l'attribut "user" dans la classe "Compte"  / orphanRemoval = true => Si le "Compte" est dissocié du "User" (par exemple, user.setCompte(null)), il sera automatiquement supprimé de la base de données.
    @JoinColumn(name = "compte_id", referencedColumnName = "id", nullable = true) // "compte_id" dans la table users peut être null
    private Compte compte;

    @OneToMany(mappedBy = "user") // mappedBy = "user" => fait référence à l'attribut "user" dans la classe "Sale"
    private List<Sale> sales;
}
