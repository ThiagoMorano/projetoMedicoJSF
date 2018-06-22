/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.medico.views;

import br.ufscar.dc.medico.bean.Paciente;
import br.ufscar.dc.medico.bean.Privilegio;
import br.ufscar.dc.medico.dao.PacienteDAO;
import br.ufscar.dc.medico.dao.PrivilegioDAO;
import br.ufscar.dc.medico.dao.PrivilegioDAO.PrivilegioEnum;
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
    
    @Inject PacienteDAO pacienteDao;
    
    @Inject PrivilegioDAO privilegioDao;

    Paciente dadosPaciente;
    String mensagem;

    UIInput nomeInput;
    UIInput senhaInput;
    UIInput telefoneInput;
    UIInput cpfInput;
    UIInput sexoInput;
    UIInput dataDeNascimentoInput;
    UIInput loginInput;
    UIInput loginSenhaInput;

    LoginSM estado;

    public UIInput getLoginInput() {
        return loginInput;
    }

    public void setLoginInput(UIInput loginInput) {
        this.loginInput = loginInput;
    }

    public UIInput getLoginSenhaInput() {
        return loginSenhaInput;
    }

    public void setLoginSenhaInput(UIInput loginSenhaInput) {
        this.loginSenhaInput = loginSenhaInput;
    }

    public LoginSM getEstado() {
        return estado;
    }

    public void setEstado(LoginSM estado) {
        this.estado = estado;
    }
 
    public NovoPaciente() {
        dadosPaciente = new Paciente();
        estado = LoginSM.inicio();
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
    
    public void procurarUsuario() {
        try {            
            String login = (String) loginInput.getValue();
            String senha = (String) loginSenhaInput.getValue();
            if (senha != null && login != null) {
                Privilegio p = privilegioDao.buscarPrivilegio(login);
                
                if (p != null) {
                    if (p.getPrivilegio() == PrivilegioDAO.PrivilegioEnum.ADMIN.getValor()) {
                        if ("coutinho".equals(senha)) {
                            this.estado = LoginSM.logou();
                        } else {
                            mensagem = "Cadastro não encontrado! Contate um administrador.";
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (NamingException e1) {
            System.out.println(e1);
        }
    }

    public String recomecar() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }

    public String gravarPaciente() throws SQLException, NamingException {
        pacienteDao.gravarPaciente(dadosPaciente);
        
        Privilegio p = new Privilegio();
        p.setLogin(dadosPaciente.getCpf());
        p.setPrivilegio(PrivilegioEnum.PACIENTE.getValor());
        privilegioDao.gravarPrivilegio(p);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Paciente cadastrado com sucesso!"));

        return recomecar();
    }
}