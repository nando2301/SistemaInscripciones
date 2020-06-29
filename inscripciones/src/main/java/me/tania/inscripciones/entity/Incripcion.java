/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.tania.inscripciones.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 
 */
@Entity
@Table(name = "incripcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Incripcion.findAll", query = "SELECT i FROM Incripcion i"),
    @NamedQuery(name = "Incripcion.findByIdincripcion", query = "SELECT i FROM Incripcion i WHERE i.idincripcion = :idincripcion")})
public class Incripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idincripcion")
    private Integer idincripcion;
    @JoinColumn(name = "alumno_idalumno", referencedColumnName = "idalumno")
    @ManyToOne(optional = false)
    private Alumno alumnoIdalumno;
    @JoinColumn(name = "grupo_idgrupo", referencedColumnName = "idgrupo")
    @ManyToOne(optional = false)
    private Grupo grupoIdgrupo;

    public Incripcion() {
    }

    public Incripcion(Integer idincripcion) {
        this.idincripcion = idincripcion;
    }

    public Integer getIdincripcion() {
        return idincripcion;
    }

    public void setIdincripcion(Integer idincripcion) {
        this.idincripcion = idincripcion;
    }

    public Alumno getAlumnoIdalumno() {
        return alumnoIdalumno;
    }

    public void setAlumnoIdalumno(Alumno alumnoIdalumno) {
        this.alumnoIdalumno = alumnoIdalumno;
    }

    public Grupo getGrupoIdgrupo() {
        return grupoIdgrupo;
    }

    public void setGrupoIdgrupo(Grupo grupoIdgrupo) {
        this.grupoIdgrupo = grupoIdgrupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idincripcion != null ? idincripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Incripcion)) {
            return false;
        }
        Incripcion other = (Incripcion) object;
        if ((this.idincripcion == null && other.idincripcion != null) || (this.idincripcion != null && !this.idincripcion.equals(other.idincripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "me.tania.inscripciones.entity.Incripcion[ idincripcion=" + idincripcion + " ]";
    }

}
