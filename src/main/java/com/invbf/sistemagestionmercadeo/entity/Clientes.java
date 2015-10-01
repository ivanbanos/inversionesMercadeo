/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionmercadeo.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c"),
    @NamedQuery(name = "Clientes.findByIdCliente", query = "SELECT c FROM Clientes c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "Clientes.findByNombres", query = "SELECT c FROM Clientes c WHERE c.nombres = :nombres"),
    @NamedQuery(name = "Clientes.findByApellidos", query = "SELECT c FROM Clientes c WHERE c.apellidos = :apellidos"),
    @NamedQuery(name = "Clientes.findByTelefono1", query = "SELECT c FROM Clientes c WHERE c.telefono1 = :telefono1"),
    @NamedQuery(name = "Clientes.findByTelefono2", query = "SELECT c FROM Clientes c WHERE c.telefono2 = :telefono2"),
    @NamedQuery(name = "Clientes.findByIdentificacion", query = "SELECT c FROM Clientes c WHERE c.identificacion = :identificacion"),
    @NamedQuery(name = "Clientes.findByCorreo", query = "SELECT c FROM Clientes c WHERE c.correo = :correo"),
    @NamedQuery(name = "Clientes.findByCumpleanos", query = "SELECT c FROM Clientes c WHERE c.cumpleanos = :cumpleanos"),
    @NamedQuery(name = "Clientes.findByPais", query = "SELECT c FROM Clientes c WHERE c.pais = :pais"),
    @NamedQuery(name = "Clientes.findByDireccion", query = "SELECT c FROM Clientes c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Clientes.findByCiudad", query = "SELECT c FROM Clientes c WHERE c.ciudad = :ciudad"),
    @NamedQuery(name = "Clientes.findByBonoFidelizacion", query = "SELECT c FROM Clientes c WHERE c.bonoFidelizacion = :bonoFidelizacion"),
    @NamedQuery(name = "Clientes.findByGenero", query = "SELECT c FROM Clientes c WHERE c.genero = :genero"),
    @NamedQuery(name = "Clientes.findByOcupacion", query = "SELECT c FROM Clientes c WHERE c.ocupacion = :ocupacion"),
    @NamedQuery(name = "Clientes.findByMontodejuegovisita", query = "SELECT c FROM Clientes c WHERE c.montodejuegovisita = :montodejuegovisita"),
    @NamedQuery(name = "Clientes.findByMaquinapreferida", query = "SELECT c FROM Clientes c WHERE c.maquinapreferida = :maquinapreferida"),
    @NamedQuery(name = "Clientes.findByHoraHabitualVisita", query = "SELECT c FROM Clientes c WHERE c.horaHabitualVisita = :horaHabitualVisita"),
    @NamedQuery(name = "Clientes.findByDiaSemanaVisita", query = "SELECT c FROM Clientes c WHERE c.diaSemanaVisita = :diaSemanaVisita"),
    @NamedQuery(name = "Clientes.findByAcompananteHabitual", query = "SELECT c FROM Clientes c WHERE c.acompananteHabitual = :acompananteHabitual"),
    @NamedQuery(name = "Clientes.findByDescripcionPersonalidad", query = "SELECT c FROM Clientes c WHERE c.descripcionPersonalidad = :descripcionPersonalidad"),
    @NamedQuery(name = "Clientes.findByGustosPreferencias", query = "SELECT c FROM Clientes c WHERE c.gustosPreferencias = :gustosPreferencias"),
    @NamedQuery(name = "Clientes.findByBebida", query = "SELECT c FROM Clientes c WHERE c.bebida = :bebida"),
    @NamedQuery(name = "Clientes.findByComida", query = "SELECT c FROM Clientes c WHERE c.comida = :comida"),
    @NamedQuery(name = "Clientes.findByFuma", query = "SELECT c FROM Clientes c WHERE c.fuma = :fuma"),
    @NamedQuery(name = "Clientes.findBySendEmail", query = "SELECT c FROM Clientes c WHERE c.sendEmail = :sendEmail"),
    @NamedQuery(name = "Clientes.findByPorActualizar", query = "SELECT c FROM Clientes c WHERE c.porActualizar = :porActualizar"),
    @NamedQuery(name = "Clientes.findByObservacionesAct", query = "SELECT c FROM Clientes c WHERE c.observacionesAct = :observacionesAct")})
public class Clientes implements Serializable {
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
    @Size(max = 45)
    @Column(name = "ocupacion")
    private String ocupacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montodejuegovisita")
    private Float montodejuegovisita;
    @Size(max = 45)
    @Column(name = "maquinapreferida")
    private String maquinapreferida;
    @Size(max = 45)
    @Column(name = "horaHabitualVisita")
    private String horaHabitualVisita;
    @Size(max = 45)
    @Column(name = "diaSemanaVisita")
    private String diaSemanaVisita;
    @Size(max = 45)
    @Column(name = "acompananteHabitual")
    private String acompananteHabitual;
    @Size(max = 45)
    @Column(name = "descripcionPersonalidad")
    private String descripcionPersonalidad;
    @Size(max = 1000)
    @Column(name = "gustosPreferencias")
    private String gustosPreferencias;
    @Size(max = 45)
    @Column(name = "bebida")
    private String bebida;
    @Size(max = 45)
    @Column(name = "comida")
    private String comida;
    @Size(max = 45)
    @Column(name = "fuma")
    private String fuma;
    @Column(name = "sendEmail")
    private Integer sendEmail;
    @Column(name = "porActualizar")
    private Integer porActualizar;
    @Size(max = 500)
    @Column(name = "observacionesAct")
    private String observacionesAct;
    @JoinColumn(name = "idCasinoPreferencial", referencedColumnName = "idCasino")
    @ManyToOne
    private Casinos idCasinoPreferencial;
    @JoinColumn(name = "idCategorias", referencedColumnName = "idCategorias")
    @ManyToOne(optional = false)
    private Categorias idCategorias;
    @JoinColumn(name = "idTipoDocumento", referencedColumnName = "idTipoDocumento")
    @ManyToOne
    private Tiposdocumento idTipoDocumento;

    public Clientes() {
    }

    public Clientes(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Clientes(Integer idCliente, String nombres, String apellidos) {
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

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public Float getMontodejuegovisita() {
        return montodejuegovisita;
    }

    public void setMontodejuegovisita(Float montodejuegovisita) {
        this.montodejuegovisita = montodejuegovisita;
    }

    public String getMaquinapreferida() {
        return maquinapreferida;
    }

    public void setMaquinapreferida(String maquinapreferida) {
        this.maquinapreferida = maquinapreferida;
    }

    public String getHoraHabitualVisita() {
        return horaHabitualVisita;
    }

    public void setHoraHabitualVisita(String horaHabitualVisita) {
        this.horaHabitualVisita = horaHabitualVisita;
    }

    public String getDiaSemanaVisita() {
        return diaSemanaVisita;
    }

    public void setDiaSemanaVisita(String diaSemanaVisita) {
        this.diaSemanaVisita = diaSemanaVisita;
    }

    public String getAcompananteHabitual() {
        return acompananteHabitual;
    }

    public void setAcompananteHabitual(String acompananteHabitual) {
        this.acompananteHabitual = acompananteHabitual;
    }

    public String getDescripcionPersonalidad() {
        return descripcionPersonalidad;
    }

    public void setDescripcionPersonalidad(String descripcionPersonalidad) {
        this.descripcionPersonalidad = descripcionPersonalidad;
    }

    public String getGustosPreferencias() {
        return gustosPreferencias;
    }

    public void setGustosPreferencias(String gustosPreferencias) {
        this.gustosPreferencias = gustosPreferencias;
    }

    public String getBebida() {
        return bebida;
    }

    public void setBebida(String bebida) {
        this.bebida = bebida;
    }

    public String getComida() {
        return comida;
    }

    public void setComida(String comida) {
        this.comida = comida;
    }

    public String getFuma() {
        return fuma;
    }

    public void setFuma(String fuma) {
        this.fuma = fuma;
    }

    public Integer getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(Integer sendEmail) {
        this.sendEmail = sendEmail;
    }

    public Integer getPorActualizar() {
        return porActualizar;
    }

    public void setPorActualizar(Integer porActualizar) {
        this.porActualizar = porActualizar;
    }

    public String getObservacionesAct() {
        return observacionesAct;
    }

    public void setObservacionesAct(String observacionesAct) {
        this.observacionesAct = observacionesAct;
    }

    public Casinos getIdCasinoPreferencial() {
        return idCasinoPreferencial;
    }

    public void setIdCasinoPreferencial(Casinos idCasinoPreferencial) {
        this.idCasinoPreferencial = idCasinoPreferencial;
    }

    public Categorias getIdCategorias() {
        return idCategorias;
    }

    public void setIdCategorias(Categorias idCategorias) {
        this.idCategorias = idCategorias;
    }

    public Tiposdocumento getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Tiposdocumento idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
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
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionmercadeo.entity.Clientes[ idCliente=" + idCliente + " ]";
    }
    
}
