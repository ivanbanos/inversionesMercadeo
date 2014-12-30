/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByIdCliente", query = "SELECT c FROM Cliente c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "Cliente.findByNombres", query = "SELECT c FROM Cliente c WHERE c.nombres = :nombres"),
    @NamedQuery(name = "Cliente.findByApellidos", query = "SELECT c FROM Cliente c WHERE c.apellidos = :apellidos"),
    @NamedQuery(name = "Cliente.findByTelefono1", query = "SELECT c FROM Cliente c WHERE c.telefono1 = :telefono1"),
    @NamedQuery(name = "Cliente.findByTelefono2", query = "SELECT c FROM Cliente c WHERE c.telefono2 = :telefono2"),
    @NamedQuery(name = "Cliente.findByIdentificacion", query = "SELECT c FROM Cliente c WHERE c.identificacion = :identificacion"),
    @NamedQuery(name = "Cliente.findByCorreo", query = "SELECT c FROM Cliente c WHERE c.correo = :correo"),
    @NamedQuery(name = "Cliente.findByCumpleanos", query = "SELECT c FROM Cliente c WHERE c.cumpleanos = :cumpleanos"),
    @NamedQuery(name = "Cliente.findByPais", query = "SELECT c FROM Cliente c WHERE c.pais = :pais"),
    @NamedQuery(name = "Cliente.findByDireccion", query = "SELECT c FROM Cliente c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Cliente.findByCiudad", query = "SELECT c FROM Cliente c WHERE c.ciudad = :ciudad"),
    @NamedQuery(name = "Cliente.findByBonoFidelizacion", query = "SELECT c FROM Cliente c WHERE c.bonoFidelizacion = :bonoFidelizacion"),
    @NamedQuery(name = "Cliente.findByGenero", query = "SELECT c FROM Cliente c WHERE c.genero = :genero"),
    @NamedQuery(name = "Cliente.findByCasino", query = "SELECT c FROM Cliente c WHERE c.idCasinoPreferencial.idCasino = :casino")})
public class Cliente implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<ControlsalidabonosHasLotesbonosHasClientes> controlsalidabonosHasLotesbonosHasClientesList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCliente")
    private Integer idCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombres")
    private String nombres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "apellidos")
    private String apellidos;
    @Size(max = 45)
    @Column(name = "telefono1")
    private String telefono1;
    @Size(max = 45)
    @Column(name = "telefono2")
    private String telefono2;
    @Size(max = 45)
    @Column(name = "identificacion")
    private String identificacion;
    @Size(max = 45)
    @Column(name = "correo")
    private String correo;
    @Column(name = "cumpleanos")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cumpleanos;
    @Size(max = 45)
    @Column(name = "pais")
    private String pais;
    @Size(max = 100)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 45)
    @Column(name = "ciudad")
    private String ciudad;
    @Size(max = 45)
    @Column(name = "bonoFidelizacion")
    private String bonoFidelizacion;
    @Size(max = 45)
    @Column(name = "genero")
    private String genero;
    @JoinTable(name = "clientestiposjuegos", joinColumns = {
        @JoinColumn(name = "idCliente", referencedColumnName = "idCliente")}, inverseJoinColumns = {
        @JoinColumn(name = "idTipoJuego", referencedColumnName = "idTipoJuego")})
    @ManyToMany
    private List<Tipojuego> tipojuegoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Solicitudentregacliente> solicitudentregaclienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Bono> bonoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Listasclientestareas> listasclientestareasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Clienteatributo> clienteatributoList;
    @JoinColumn(name = "idTipoDocumento", referencedColumnName = "idTipoDocumento")
    @ManyToOne
    private Tipodocumento idTipoDocumento;
    @JoinColumn(name = "idCategorias", referencedColumnName = "idCategorias")
    @ManyToOne(optional = false)
    private Categoria idCategorias;
    @JoinColumn(name = "idCasinoPreferencial", referencedColumnName = "idCasino")
    @ManyToOne
    private Casino idCasinoPreferencial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCliente")
    private List<Solicitudcambiocupofidelizacion> solicitudcambiocupofidelizacionList;

    public Cliente() {
    }

    public Cliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente(Integer idCliente, String nombres, String apellidos) {
        this.idCliente = idCliente;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getCumpleanos() {
        return cumpleanos;
    }

    public void setCumpleanos(Date cumpleanos) {
        this.cumpleanos = cumpleanos;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getBonoFidelizacion() {
        return bonoFidelizacion;
    }

    public void setBonoFidelizacion(String bonoFidelizacion) {
        this.bonoFidelizacion = bonoFidelizacion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @XmlTransient
    public List<Tipojuego> getTipojuegoList() {
        return tipojuegoList;
    }

    public void setTipojuegoList(List<Tipojuego> tipojuegoList) {
        this.tipojuegoList = tipojuegoList;
    }

    @XmlTransient
    public List<Solicitudentregacliente> getSolicitudentregaclienteList() {
        return solicitudentregaclienteList;
    }

    public void setSolicitudentregaclienteList(List<Solicitudentregacliente> solicitudentregaclienteList) {
        this.solicitudentregaclienteList = solicitudentregaclienteList;
    }

    @XmlTransient
    public List<Bono> getBonoList() {
        return bonoList;
    }

    public void setBonoList(List<Bono> bonoList) {
        this.bonoList = bonoList;
    }

    @XmlTransient
    public List<Listasclientestareas> getListasclientestareasList() {
        return listasclientestareasList;
    }

    public void setListasclientestareasList(List<Listasclientestareas> listasclientestareasList) {
        this.listasclientestareasList = listasclientestareasList;
    }

    @XmlTransient
    public List<Clienteatributo> getClienteatributoList() {
        return clienteatributoList;
    }

    public void setClienteatributoList(List<Clienteatributo> clienteatributoList) {
        this.clienteatributoList = clienteatributoList;
    }

    public Tipodocumento getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Tipodocumento idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public Categoria getIdCategorias() {
        return idCategorias;
    }

    public void setIdCategorias(Categoria idCategorias) {
        this.idCategorias = idCategorias;
    }

    public Casino getIdCasinoPreferencial() {
        return idCasinoPreferencial;
    }

    public void setIdCasinoPreferencial(Casino idCasinoPreferencial) {
        this.idCasinoPreferencial = idCasinoPreferencial;
    }

    @XmlTransient
    public List<Solicitudcambiocupofidelizacion> getSolicitudcambiocupofidelizacionList() {
        return solicitudcambiocupofidelizacionList;
    }

    public void setSolicitudcambiocupofidelizacionList(List<Solicitudcambiocupofidelizacion> solicitudcambiocupofidelizacionList) {
        this.solicitudcambiocupofidelizacionList = solicitudcambiocupofidelizacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Cliente[ idCliente=" + idCliente + " ]";
    }

    @XmlTransient
    public List<ControlsalidabonosHasLotesbonosHasClientes> getControlsalidabonosHasLotesbonosHasClientesList() {
        return controlsalidabonosHasLotesbonosHasClientesList;
    }

    public void setControlsalidabonosHasLotesbonosHasClientesList(List<ControlsalidabonosHasLotesbonosHasClientes> controlsalidabonosHasLotesbonosHasClientesList) {
        this.controlsalidabonosHasLotesbonosHasClientesList = controlsalidabonosHasLotesbonosHasClientesList;
    }
    
}
