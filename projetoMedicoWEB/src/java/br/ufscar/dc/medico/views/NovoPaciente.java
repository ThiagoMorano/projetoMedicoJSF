/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.medico.views;

import br.ufscar.dc.medico.bean.Paciente;
import br.ufscar.dc.medico.dao.PacienteDAO;
import java.io.Serializable;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
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
public class NovoPaciente implements Serializable {

    @Resource(name = "jdbc/MedicoDBLocal")
    DataSource dataSource;
    
    @Inject
    PacienteDAO pacienteDao;

    Paciente dadosPaciente;
    String mensagem;

    UIInput nomeInput;
    UIInput senhaInput;
    UIInput telefoneInput;
    UIInput cpfInput;
    UIInput sexoInput;
    UIInput dataDeNascimentoInput;
    
    public NovoPaciente() {
        dadosPaciente = new Paciente();
    }
    
    public UIInput getNomeInput() {
        return nomeInput;
    }

    public void setNomeInput(UIInput nomeInput) {
        this.nomeInput = nomeInput;
    }
    
    public UIInput getTelefoneInput() {
        return telefoneInput;
    }

    public void setTelefoneInput(UIInput telefoneInput) {
        this.telefoneInput = telefoneInput;
    }

    public UIInput getSenhaInput() {
        return senhaInput;
    }

    public void setSenhaInput(UIInput senhaInput) {
        this.senhaInput = senhaInput;
    }

    public UIInput getCpfInput() {
        return cpfInput;
    }

    public void setCpfInput(UIInput cpfInput) {
        this.cpfInput = cpfInput;
    }

    public UIInput getSexoInput() {
        return sexoInput;
    }

    public void setSexoInput(UIInput sexoInput) {
        this.sexoInput = sexoInput;
    }

    public UIInput getDataDeNascimentoInput() {
        return dataDeNascimentoInput;
    }

    public void setDataDeNascimentoInput(UIInput dataDeNascimentoInput) {
        this.dataDeNascimentoInput = dataDeNascimentoInput;
    }
    
    public Paciente getDadosPaciente() {
        return dadosPaciente;
    }

    public void setDadosPaciente(Paciente dadosPaciente) {
        this.dadosPaciente = dadosPaciente;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    
    public void validarNome(FacesContext context, UIComponent toValidate, String value) {
        if (value.trim().length() == 0) {
            ((UIInput) toValidate).setValid(false);
            FacesMessage message = new FacesMessage("Nome não pode ser vazio");
            context.addMessage(toValidate.getClientId(context), message);
        }
    }

    public void validarCPF(FacesContext context, UIComponent toValidate, String value) {
        if (value.trim().length() == 0) {
            ((UIInput) toValidate).setValid(false);
            FacesMessage message = new FacesMessage("CPF não pode ser vazio");
            context.addMessage(toValidate.getClientId(context), message);
        }
    }

    public String recomecar() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }

    public String gravarPaciente() throws SQLException, NamingException {
        pacienteDao.gravarPaciente(dadosPaciente);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Paciente cadastrado com sucesso!"));

        return recomecar();
    }
}