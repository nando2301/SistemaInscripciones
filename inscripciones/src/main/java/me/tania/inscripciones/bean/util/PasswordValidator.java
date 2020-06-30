package me.tania.inscripciones.bean.util;

import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator {

    private static final Logger LOG = Logger.getLogger(PasswordValidator.class.getName());

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String cadena = value.toString();
        if (cadena.contains("\"")
                || cadena.contains("'")
                || cadena.contains("<")
                || cadena.contains(">")
                || cadena.contains("&")
                || cadena.contains("=")
                || cadena.contains("*")
                || !contieneMayusculaONumero(cadena)
                || excedeLimite(cadena)) {
            LOG.warning("En la contraseña los caracteres \",',<,>,&,=,* no son permitidos, debes incluir una Mayúscula, un número y no exceder el tamaño de 8");
            throw new ValidatorException(
                    new FacesMessage("En la contraseña los caracteres \",',<,>,&,=,* no son permitidos, debes incluir una Mayúscula, un número y no exceder el tamaño de 8"));
        }
    }

    public boolean contieneMayusculaONumero(String cadena) {
        boolean contieneMayuscula = false;
        for (int i = 0; i < cadena.length(); i++) {
            if (Character.isUpperCase(cadena.charAt(i))) {
                contieneMayuscula = true;
            } else if (Character.isDigit(cadena.charAt(i))) {
                contieneMayuscula = true;
            }
        }
        return contieneMayuscula;
    }

    public boolean excedeLimite(String cadena) {
        return cadena.length() > 8;
    }
}
