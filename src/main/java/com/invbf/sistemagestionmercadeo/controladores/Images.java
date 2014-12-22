package com.invbf.sistemagestionmercadeo.controladores;

import java.io.ByteArrayInputStream;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@RequestScoped
public class Images {

    private StreamedContent image;

    @ManagedProperty("#{param.id}")
    private Long id;
    
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    @PostConstruct
    public void init() {
        if (FacesContext.getCurrentInstance().getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            image = new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            image = new DefaultStreamedContent(new ByteArrayInputStream((byte[])sessionBean.getAttributes().get("imagen")));
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StreamedContent getImage() {
        return image;
    }

}