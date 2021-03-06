package me.tania.inscripciones.bean;

import me.tania.inscripciones.entity.Alumno;
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

@Named("alumnoController")
@SessionScoped
public class AlumnoController implements Serializable {

    private static final Logger LOG = Logger.getLogger(AlumnoController.class.getName());

    @EJB
    private me.tania.inscripciones.bean.AlumnoFacade ejbFacade;
    private List<Alumno> items = null;
    private Alumno selected;

    public AlumnoController() {
    }

    public Alumno getSelected() {
        return selected;
    }

    public void setSelected(Alumno selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AlumnoFacade getFacade() {
        return ejbFacade;
    }

    public Alumno prepareCreate() {
        selected = new Alumno();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AlumnoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AlumnoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AlumnoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Alumno> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
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

    public Alumno getAlumno(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Alumno> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Alumno> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<Alumno> getMyRecord() {
        String username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
        Connection con = null;
        PreparedStatement ps;
        List<Alumno> alumnos = new ArrayList<>();

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select * \n"
                    + "from alumno \n"
                    + "where usuario = ? ;");
            ps.setString(1, username);
            //LOG.info(ps.toString());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                //result found, means valid inputs
                Alumno alumno = new Alumno();
                alumno.setIdalumno(rs.getInt("idalumno"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setUsuario(rs.getString("usuario"));
                alumno.setMatricula(rs.getString("matricula"));
                if (rs.getInt("estatus") == 1) {
                    alumno.setEstatus(Boolean.TRUE);
                } else {
                    alumno.setEstatus(Boolean.FALSE);
                }
                boolean add = alumnos.add(alumno);
            }
        } catch (SQLException ex) {
            LOG.log(Level.WARNING, "Error -->{0}", ex.getMessage());
            System.out.println("Error -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
        }
        return alumnos;
    }

    @FacesConverter(forClass = Alumno.class)
    public static class AlumnoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AlumnoController controller = (AlumnoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "alumnoController");
            return controller.getAlumno(getKey(value));
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
            if (object instanceof Alumno) {
                Alumno o = (Alumno) object;
                return getStringKey(o.getIdalumno());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Alumno.class.getName()});
                return null;
            }
        }

    }

}
