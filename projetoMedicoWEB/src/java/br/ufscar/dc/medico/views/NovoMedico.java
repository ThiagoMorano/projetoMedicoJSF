/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.medico.views;

import br.ufscar.dc.medico.bean.Medico;
import br.ufscar.dc.medico.dao.MedicoDAO;
import java.io.Serializable;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Thiago
 */
@Named
@SessionScoped
public class NovoMedico implements Serializable {

    @Resource(name = "jdbc/MedicoDBLocal")
    DataSource dataSource;

    Medico dadosMedico;
    //UsuarioDAO usuarioDao;
    @Inject
    MedicoDAO medicoDao;

    UIInput crmInput;
    UIInput senhaInput;
    UIInput nomeInput;
    UIInput especialidadeInput;

    public UIInput getCrmInput() {
        return crmInput;
    }

    public void setCrmInput(UIInput crmInput) {
        this.crmInput = crmInput;
    }

    public UIInput getSenhaInput() {
        return senhaInput;
    }

    public void setSenhaInput(UIInput senhaInput) {
        this.senhaInput = senhaInput;
    }

    public UIInput getNomeInput() {
        return nomeInput;
    }

    public void setNomeInput(UIInput nomeInput) {
        this.nomeInput = nomeInput;
    }

    public UIInput getEspecialidadeInput() {
        return especialidadeInput;
    }

    public void setEspecialidadeInput(UIInput especialidadeInput) {
        this.especialidadeInput = especialidadeInput;
    }
    
    
    public NovoMedico() {
        dadosMedico = new Medico();
    }

    public Medico getDadosMedico() {
        return dadosMedico;
    }

    public void setDadosPalpite(Medico dadosMedico) {
        this.dadosMedico = dadosMedico;
    }
    
    public void validarNome(FacesContext context, UIComponent toValidate, String value) {
        if (value.trim().length() == 0) {
            ((UIInput) toValidate).setValid(false);
            FacesMessage message = new FacesMessage("Nome não pode ser vazio");
            context.addMessage(toValidate.getClientId(context), message);
        }
    }
    
    public void validarCrm(FacesContext context, UIComponent toValidate, String value) {
        if (value.trim().length() == 0) {
            ((UIInput) toValidate).setValid(false);
            FacesMessage message = new FacesMessage("Crm não pode ser vazio");
            context.addMessage(toValidate.getClientId(context), message);
        }
    }
    
    
    public String recomecar() {
       FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
       return "index?faces-redirect=true";
   }

   public String gravarMedico() throws SQLException, NamingException {
       medicoDao.gravarMedico(dadosMedico);
       FacesContext facesContext = FacesContext.getCurrentInstance();
       Flash flash = facesContext.getExternalContext().getFlash();
       flash.setKeepMessages(true);
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Achou que não ia ter novo médico? Achou errado, otário"));

       return recomecar();
   }
}