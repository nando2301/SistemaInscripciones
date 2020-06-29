package me.tania.inscripciones.bean.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.tania.inscripciones.entity.Usuario;

public class LoginDAO {

    private static final Logger LOG = Logger.getLogger(LoginDAO.class.getName());

    public static boolean validate(String user, String password) {
        Connection con = null;
        PreparedStatement ps;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select usuario, password from usuario where usuario = ? and password = ?");
            ps.setString(1, user);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //result found, means valid inputs
                return true;
            }
        } catch (SQLException ex) {
            LOG.log(Level.WARNING, "Login error -->{0}", ex.getMessage());
            System.out.println("Login error -->" + ex.getMessage());
            return false;
        } finally {
            DataConnect.close(con);
        }
        return false;
    }

    public static String getUserId(String username) {
        Connection con = null;
        PreparedStatement ps;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select * \n"
                    + "from usuario \n"
                    + "where usuario =  ?");
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            Usuario usuario = new Usuario();
            if (rs.next()) {
                usuario.setIdusuario(rs.getInt("idusuario"));
                LOG.info(usuario.getIdusuario().toString());
                return usuario.getIdusuario().toString();
            }
        } catch (SQLException ex) {
            LOG.log(Level.WARNING, "Error -->{0}", ex.getMessage());
            System.out.println("Error -->" + ex.getMessage());
        } finally {
            DataConnect.close(con);
        }
        return "";
    }

    public static boolean isAdmin(String username) {
        Connection con = null;
        PreparedStatement ps;

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select * \n"
                    + "from rol_usuario \n"
                    + "where usuario_idusuario = ? \n"
                    + "and rol_idrol = 1 ;");
            ps.setString(1, getUserId(username));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //result found, means valid inputs
                return true;
            }
        } catch (SQLException ex) {
            LOG.log(Level.WARNING, "Error -->{0}", ex.getMessage());
            System.out.println("Error -->" + ex.getMessage());
            return false;
        } finally {
            DataConnect.close(con);
        }
        return false;
    }
}
