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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
    @NamedQuery(name = "Usuario.findByCasino", query = "SELECT u FROM Usuario u WHERE u.casinoList = :casino"),
    @NamedQuery(name = "Usuarios.findByTipoPerfil", query = "SELECT u FROM Usuario u WHERE u.idPerfil.nombre = :nombrePerfil")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUsuario")
    private Integer idUsuario;
    @Column(name = "tipo")
    private Integer tipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombreUsuario")
    private String nombreUsuario;
    @Basic(optional = false)
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "contrasena")
    private String contrasena;
    @Size(max = 45)
    @Column(name = "estado")
    private String estado;
    @ManyToMany(mappedBy = "usuarioList", fetch = FetchType.EAGER)
    private List<Tarea> tareaList;
    @JoinTable(name = "Usuarios_has_Casinos", joinColumns = {
        @JoinColumn(name = "Usuarios_idUsuario", referencedColumnName = "idUsuario")}, inverseJoinColumns = {
        @JoinColumn(name = "Casinos_idCasino", referencedColumnName = "idCasino")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Casino> casinoList;
    @OneToMany(mappedBy = "aprobador")
    private List<Solicitudentrega> solicitudentregaList;
    @OneToMany(mappedBy = "solicitante")
    private List<Solicitudentrega> solicitudentregaList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "remitente")
    private List<Solicitudentregalotesmaestro> solicitudentregalotesmaestroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuariosidUsuario", fetch = FetchType.EAGER)
    private List<CommputadorRegistrado> computadorregistradoList;
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
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Perfil idPerfil;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Usuario")
    private List<Permiso> permisoList;
    @OneToMany(mappedBy = "recibidor")
    private List<Ordencomprabaraja> ordencomprabarajaList;
    @OneToMany(mappedBy = "creador")
    private List<Ordencomprabaraja> ordencomprabarajaList1;
    @OneToMany(mappedBy = "aceptador")
    private List<Ordencomprabaraja> ordencomprabarajaList2;
    @OneToMany(mappedBy = "recibidor")
    private List<Solicitudbarajas> solicitudbarajasList;
    @OneToMany(mappedBy = "creador")
    private List<Solicitudbarajas> solicitudbarajasList1;
    @OneToMany(mappedBy = "aceptador")
    private List<Solicitudbarajas> solicitudbarajasList2;
    @OneToMany(mappedBy = "destructor")
    private List<Solicitudbarajas> solicitudbarajasList3;

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
    public List<Casino> getCasinoList() {
        return casinoList;
    }

    public void setCasinoList(List<Casino> casinoList) {
        this.casinoList = casinoList;
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
    public List<CommputadorRegistrado> getComputadorregistradoList() {
        return computadorregistradoList;
    }

    public void setComputadorregistradoList(List<CommputadorRegistrado> computadorregistradoList) {
        this.computadorregistradoList = computadorregistradoList;
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
        return idUsuario + " " + nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public List<Ordencomprabaraja> getOrdencomprabarajaList() {
        return ordencomprabarajaList;
    }

    public void setOrdencomprabarajaList(List<Ordencomprabaraja> ordencomprabarajaList) {
        this.ordencomprabarajaList = ordencomprabarajaList;
    }

    @XmlTransient
    public List<Ordencomprabaraja> getOrdencomprabarajaList1() {
        return ordencomprabarajaList1;
    }

    public void setOrdencomprabarajaList1(List<Ordencomprabaraja> ordencomprabarajaList1) {
        this.ordencomprabarajaList1 = ordencomprabarajaList1;
    }

    @XmlTransient
    public List<Ordencomprabaraja> getOrdencomprabarajaList2() {
        return ordencomprabarajaList2;
    }

    public void setOrdencomprabarajaList2(List<Ordencomprabaraja> ordencomprabarajaList2) {
        this.ordencomprabarajaList2 = ordencomprabarajaList2;
    }

    @XmlTransient
    public List<Solicitudbarajas> getSolicitudbarajasList() {
        return solicitudbarajasList;
    }

    public void setSolicitudbarajasList(List<Solicitudbarajas> solicitudbarajasList) {
        this.solicitudbarajasList = solicitudbarajasList;
    }

    @XmlTransient
    public List<Solicitudbarajas> getSolicitudbarajasList1() {
        return solicitudbarajasList1;
    }

    public void setSolicitudbarajasList1(List<Solicitudbarajas> solicitudbarajasList1) {
        this.solicitudbarajasList1 = solicitudbarajasList1;
    }

    @XmlTransient
    public List<Solicitudbarajas> getSolicitudbarajasList2() {
        return solicitudbarajasList2;
    }

    public void setSolicitudbarajasList2(List<Solicitudbarajas> solicitudbarajasList2) {
        this.solicitudbarajasList2 = solicitudbarajasList2;
    }

    @XmlTransient
    public List<Solicitudbarajas> getSolicitudbarajasList3() {
        return solicitudbarajasList3;
    }

    public void setSolicitudbarajasList3(List<Solicitudbarajas> solicitudbarajasList3) {
        this.solicitudbarajasList3 = solicitudbarajasList3;
    }
}
