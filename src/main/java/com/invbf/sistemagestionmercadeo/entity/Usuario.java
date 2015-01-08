/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "Usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuario.findByNombreUsuario", query = "SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "Usuario.findByContrasena", query = "SELECT u FROM Usuario u WHERE u.contrasena = :contrasena"),
    @NamedQuery(name = "Usuario.findByEstado", query = "SELECT u FROM Usuario u WHERE u.estado = :estado"),
    @NamedQuery(name = "Usuarios.findByTipoPerfil", query = "SELECT u FROM Usuario u WHERE u.idPerfil.nombre = :nombrePerfil")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUsuario")
    private Integer idUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombreUsuario")
    private String nombreUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "contrasena")
    private String contrasena;
    @Size(max = 45)
    @Column(name = "estado")
    private String estado;
    @ManyToMany(mappedBy = "usuarioList")
    private List<Tarea> tareaList;
    @OneToMany(mappedBy = "aprobador")
    private List<Solicitudentrega> solicitudentregaList;
    @OneToMany(mappedBy = "solicitante")
    private List<Solicitudentrega> solicitudentregaList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "remitente")
    private List<Solicitudentregalotesmaestro> solicitudentregalotesmaestroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuariosidUsuario")
    private List<ComputadorRegistrado> computadorRegistradoList;
    @OneToMany(mappedBy = "autorizador")
    private List<Bono> bonoList;
    @OneToMany(mappedBy = "validador")
    private List<Bono> bonoList1;
    @OneToMany(mappedBy = "usuario")
    private List<Listasclientestareas> listasclientestareasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Log> logList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitante")
    private List<Solicitudcambiocupofidelizacion> solicitudcambiocupofidelizacionList;
    @OneToMany(mappedBy = "solicitante")
    private List<Controlsalidabono> controlsalidabonoList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Usuariodetalle usuariodetalle;
    @JoinColumn(name = "idPerfil", referencedColumnName = "idPerfil")
    @ManyToOne(optional = false)
    private Perfil idPerfil;
    @JoinColumn(name = "idCasino", referencedColumnName = "idCasino")
    @ManyToOne
    private Casino idCasino;

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(Integer idUsuario, String nombreUsuario, String contrasena) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Tarea> getTareaList() {
        return tareaList;
    }

    public void setTareaList(List<Tarea> tareaList) {
        this.tareaList = tareaList;
    }

    @XmlTransient
    public List<Solicitudentrega> getSolicitudentregaList() {
        return solicitudentregaList;
    }

    public void setSolicitudentregaList(List<Solicitudentrega> solicitudentregaList) {
        this.solicitudentregaList = solicitudentregaList;
    }

    @XmlTransient
    public List<Solicitudentrega> getSolicitudentregaList1() {
        return solicitudentregaList1;
    }

    public void setSolicitudentregaList1(List<Solicitudentrega> solicitudentregaList1) {
        this.solicitudentregaList1 = solicitudentregaList1;
    }

    @XmlTransient
    public List<Solicitudentregalotesmaestro> getSolicitudentregalotesmaestroList() {
        return solicitudentregalotesmaestroList;
    }

    public void setSolicitudentregalotesmaestroList(List<Solicitudentregalotesmaestro> solicitudentregalotesmaestroList) {
        this.solicitudentregalotesmaestroList = solicitudentregalotesmaestroList;
    }

    @XmlTransient
    public List<ComputadorRegistrado> getComputadorRegistradoList() {
        return computadorRegistradoList;
    }

    public void setComputadorRegistradoList(List<ComputadorRegistrado> computadorRegistradoList) {
        this.computadorRegistradoList = computadorRegistradoList;
    }

    @XmlTransient
    public List<Bono> getBonoList() {
        return bonoList;
    }

    public void setBonoList(List<Bono> bonoList) {
        this.bonoList = bonoList;
    }

    @XmlTransient
    public List<Bono> getBonoList1() {
        return bonoList1;
    }

    public void setBonoList1(List<Bono> bonoList1) {
        this.bonoList1 = bonoList1;
    }

    @XmlTransient
    public List<Listasclientestareas> getListasclientestareasList() {
        return listasclientestareasList;
    }

    public void setListasclientestareasList(List<Listasclientestareas> listasclientestareasList) {
        this.listasclientestareasList = listasclientestareasList;
    }

    @XmlTransient
    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }

    @XmlTransient
    public List<Solicitudcambiocupofidelizacion> getSolicitudcambiocupofidelizacionList() {
        return solicitudcambiocupofidelizacionList;
    }

    public void setSolicitudcambiocupofidelizacionList(List<Solicitudcambiocupofidelizacion> solicitudcambiocupofidelizacionList) {
        this.solicitudcambiocupofidelizacionList = solicitudcambiocupofidelizacionList;
    }

    @XmlTransient
    public List<Controlsalidabono> getControlsalidabonoList() {
        return controlsalidabonoList;
    }

    public void setControlsalidabonoList(List<Controlsalidabono> controlsalidabonoList) {
        this.controlsalidabonoList = controlsalidabonoList;
    }

    public Usuariodetalle getUsuariodetalle() {
        return usuariodetalle;
    }

    public void setUsuariodetalle(Usuariodetalle usuariodetalle) {
        this.usuariodetalle = usuariodetalle;
    }

    public Perfil getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Perfil idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Casino getIdCasino() {
        return idCasino;
    }

    public void setIdCasino(Casino idCasino) {
        this.idCasino = idCasino;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Usuario[ idUsuario=" + idUsuario + " ]";
    }
    
}
