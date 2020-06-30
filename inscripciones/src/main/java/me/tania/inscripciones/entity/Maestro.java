/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.tania.inscripciones.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fernando
 */
@Entity
@Table(name = "maestro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Maestro.findAll", query = "SELECT m FROM Maestro m"),
    @NamedQuery(name = "Maestro.findByIdmaestro", query = "SELECT m FROM Maestro m WHERE m.idmaestro = :idmaestro"),
    @NamedQuery(name = "Maestro.findByNombre", query = "SELECT m FROM Maestro m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Maestro.findByUsuario", query = "SELECT m FROM Maestro m WHERE m.usuario = :usuario"),
    @NamedQuery(name = "Maestro.findByTipo", query = "SELECT m FROM Maestro m WHERE m.tipo = :tipo"),
    @NamedQuery(name = "Maestro.findByEstatus", query = "SELECT m FROM Maestro m WHERE m.estatus = :estatus")})
public class Maestro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmaestro")
    private Integer idmaestro;
    @Size(max = 32)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 32)
    @Column(name = "usuario")
    private String usuario;
    @Size(max = 32)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "estatus")
    private Boolean estatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maestroIdmaestro")
    private List<MaestroMateria> maestroMateriaList;

    public Maestro() {
    }

    public Maestro(Integer idmaestro) {
        this.idmaestro = idmaestro;
    }

    public Integer getIdmaestro() {
        return idmaestro;
    }

    public void setIdmaestro(Integer idmaestro) {
        this.idmaestro = idmaestro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }

    @XmlTransient
    public List<MaestroMateria> getMaestroMateriaList() {
        return maestroMateriaList;
    }

    public void setMaestroMateriaList(List<MaestroMateria> maestroMateriaList) {
        this.maestroMateriaList = maestroMateriaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmaestro != null ? idmaestro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Maestro)) {
            return false;
        }
        Maestro other = (Maestro) object;
        if ((this.idmaestro == null && other.idmaestro != null) || (this.idmaestro != null && !this.idmaestro.equals(other.idmaestro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "me.tania.inscripciones.entity.Maestro[ idmaestro=" + idmaestro + " ]";
    }

}
