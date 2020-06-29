package me.tania.inscripciones.bean.util;

import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("inputValidator")
public class InputValidator implements Validator {

    private static final Logger LOG = Logger.getLogger(InputValidator.class.getName());

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String cadena = value.toString();
        if (cadena.contains("\"")
                || cadena.contains("'")
                || cadena.contains("<")
                || cadena.contains(">")
                || cadena.contains("&")
                || cadena.contains("=")
                || cadena.contains("*")) {
            LOG.warning("Los caracteres \",',<,>,&,=,* no son permitidos");
            throw new ValidatorException(new FacesMessage("Los caracteres \",',<,>,&,=,* no son permitidos"));
        }
    }

}
