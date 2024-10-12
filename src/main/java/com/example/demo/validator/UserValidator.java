package com.example.demo.validator;

import com.example.demo.entity.User;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserValidator {

    // NB: La validation ici peut être effectuée sur des objets entité User (comme celui-ci), UserRequest ou UserDto. Le même principe reste applicable sur les branches v2 et v3.
    public static List<String> validate(User user) {
        List<String> errors = new ArrayList<>();

        if (user == null) {
            errors.add("Veuillez renseigner le nom d'utilisateur");
            errors.add("Veuillez renseigner l'email de l'utilisateur");
            errors.add("Veuillez renseigner l'âge de l'utilisateur");
            return errors;
        }

        if (user.getName() == null || !StringUtils.hasLength(user.getName())) {
            errors.add("Veuillez renseigner le nom d'utilisateur");
        }

        if (user.getEmail() == null || !StringUtils.hasLength(user.getEmail())) {
            errors.add("Veuillez renseigner l'email de l'utilisateur");
        }

        if (user.getEmail() != null && !org.springframework.util.PatternMatchUtils.simpleMatch("*@*.*", user.getEmail())) {
            errors.add("L'email fourni n'est pas valide");
        }

        if (user.getAge() == null) {
            errors.add("Veuillez renseigner l'âge de l'utilisateur");
        }

        // On peut ajouter d'autres validateurs si nécessaire aux objets associés à l'objet User (par exemple, l'adresse)
        // errors.addAll(AddressValidator.validate(userDto.getAddress()));
        return errors;
    }
}
