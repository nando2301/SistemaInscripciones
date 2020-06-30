package me.tania.inscripciones.bean;

import me.tania.inscripciones.entity.Inscripcion;
import me.tania.inscripciones.bean.util.JsfUtil;
import me.tania.inscripciones.bean.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import me.tania.inscripciones.bean.util.DataConnect;
import me.tania.inscripciones.entity.Alumno;
import me.tania.inscripciones.entity.Grupo;

@Named("inscripcionController")
@SessionScoped
public class InscripcionController implements Serializable {

    private static final Logger LOG = Logger.getLogger(InscripcionController.class.getName());

    @EJB
    private me.tania.inscripciones.bean.InscripcionFacade ejbFacade;
    private List<Inscripcion> items = null;
    private Inscripcion selected;

    public InscripcionController() {
    }

    public Inscripcion getSelected() {
        return selected;
    }

    public void setSelected(Inscripcion selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private InscripcionFacade getFacade() {
        return ejbFacade;
    }

    public Inscripcion prepareCreate() {
        selected = new Inscripcion();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("InscripcionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("InscripcionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("InscripcionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Inscripcion> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<Inscripcion> getMyItems() {
        String username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
        Connection con = null;
        PreparedStatement ps;
        items = new ArrayList<>();
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select * \n"
                    + "from inscripcion \n"
                    + "where alumno_idalumno =  ?");
            ps.setString(1, getAlumnoId(username));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Inscripcion inscripcion = getInscripcion(rs.getInt("idinscripcion"));
                items.add(inscripcion);
            }
        } catch (SQLException ex) {
            LOG.log(Level.WARNING, "Error -->{0}", ex.getMessage());
            System.out.println("Error -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
        }
        LOG.info(String.valueOf(items.size()));
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Inscripcion getInscripcion(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Inscripcion> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Inscripcion> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Inscripcion.class)
    public static class InscripcionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InscripcionController controller = (InscripcionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "inscripcionController");
            return controller.getInscripcion(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Inscripcion) {
                Inscripcion o = (Inscripcion) object;
                return getStringKey(o.getIdinscripcion());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Inscripcion.class.getName()});
                return null;
            }
        }

    }

    public String getAlumnoId(String username) {
        Connection con = null;
        PreparedStatement ps;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select * \n"
                    + "from alumno \n"
                    + "where usuario =  ?");
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            Alumno alumno = new Alumno();
            if (rs.next()) {
                alumno.setIdalumno(rs.getInt("idalumno"));
                return alumno.getIdalumno().toString();
            }
        } catch (SQLException ex) {
            LOG.log(Level.WARNING, "Error -->{0}", ex.getMessage());
            System.out.println("Error -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
        }
        return "";
    }
}
