package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "users")
public class User implements Serializable {
    // NB: La validation ici peut être effectuée sur des objets qui correspondent aux requêtes entrantes: entité User (comme celui-ci), UserRequest ou UserDto. Le même principe reste applicable aux branches v2 et v3.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Veuillez renseigner le nom d'utilisateur")
    String name;

    @NotBlank(message = "Veuillez renseigner l'email d'utilisateur")
    @Email(message = "L'email fourni n'est pas valide")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "L'email fourni n'est pas valide"
    )
    String email;

    @NotNull(message = "Veuillez renseigner l'âge de l'utilisateur")
    @Positive(message = "L'âge de l'utilisateur doit être un nombre positif")
    @Min(value = 18, message = "L'âge doit être d'au moins 18 ans")
    @Max(value = 40, message = "L'âge ne doit pas dépasser 40 ans")
    Integer age;

}
