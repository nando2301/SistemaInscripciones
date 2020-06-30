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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fernando
 */
@Entity
@Table(name = "maestro_materia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MaestroMateria.findAll", query = "SELECT m FROM MaestroMateria m"),
    @NamedQuery(name = "MaestroMateria.findByIdmaestroMateria", query = "SELECT m FROM MaestroMateria m WHERE m.idmaestroMateria = :idmaestroMateria")})
public class MaestroMateria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmaestro_materia")
    private Integer idmaestroMateria;
    @JoinColumn(name = "maestro_idmaestro", referencedColumnName = "idmaestro")
    @ManyToOne(optional = false)
    private Maestro maestroIdmaestro;
    @JoinColumn(name = "materia_idmateria", referencedColumnName = "idmateria")
    @ManyToOne(optional = false)
    private Materia materiaIdmateria;

    public MaestroMateria() {
    }

    public MaestroMateria(Integer idmaestroMateria) {
        this.idmaestroMateria = idmaestroMateria;
    }

    public Integer getIdmaestroMateria() {
        return idmaestroMateria;
    }

    public void setIdmaestroMateria(Integer idmaestroMateria) {
        this.idmaestroMateria = idmaestroMateria;
    }

    public Maestro getMaestroIdmaestro() {
        return maestroIdmaestro;
    }

    public void setMaestroIdmaestro(Maestro maestroIdmaestro) {
        this.maestroIdmaestro = maestroIdmaestro;
    }

    public Materia getMateriaIdmateria() {
        return materiaIdmateria;
    }

    public void setMateriaIdmateria(Materia materiaIdmateria) {
        this.materiaIdmateria = materiaIdmateria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmaestroMateria != null ? idmaestroMateria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaestroMateria)) {
            return false;
        }
        MaestroMateria other = (MaestroMateria) object;
        if ((this.idmaestroMateria == null && other.idmaestroMateria != null) || (this.idmaestroMateria != null && !this.idmaestroMateria.equals(other.idmaestroMateria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "me.tania.inscripciones.entity.MaestroMateria[ idmaestroMateria=" + idmaestroMateria + " ]";
    }

}
