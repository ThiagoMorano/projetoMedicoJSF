/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.medico.views; 

import br.ufscar.dc.medico.bean.Consulta;
import br.ufscar.dc.medico.bean.Medico;
import br.ufscar.dc.medico.bean.Paciente;
import br.ufscar.dc.medico.bean.Privilegio;
import br.ufscar.dc.medico.dao.ConsultaDAO;
import br.ufscar.dc.medico.dao.MedicoDAO;
import br.ufscar.dc.medico.dao.PacienteDAO;
import br.ufscar.dc.medico.dao.PrivilegioDAO;
import java.io.Serializable;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.sql.DataSource;



/**
 *
 * @author 619710
 */

@Named
@SessionScoped
public class NovaConsulta implements Serializable {
    Consulta dadosConsulta;
    @Inject ConsultaDAO consultaDao;
    @Inject PacienteDAO pacienteDao;
    @Inject PrivilegioDAO privilegioDao;
    UIInput crmMedicoInput;
    UIInput cpfPacienteInput;
    UIInput dataConsultaInput;
    UIInput loginInput;
    UIInput senhaInput;

    String mensagem;
    LoginSM estado;

    public UIInput getLoginInput() {
        return loginInput;
    }

    public void setLoginInput(UIInput loginInput) {
        this.loginInput = loginInput;
    }

    public UIInput getSenhaInput() {
        return senhaInput;
    }

    public void setSenhaInput(UIInput senhaInput) {
        this.senhaInput = senhaInput;
    }

    public LoginSM getEstado() {
        return estado;
    }

    public void setEstado(LoginSM estado) {
        this.estado = estado;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public UIInput getCrmMedicoInput() {
        return crmMedicoInput;
    }

    public void setCrmMedicoInput(UIInput crmMedicoInput) {
        this.crmMedicoInput = crmMedicoInput;
    }

    public UIInput getCpfPacienteInput() {
        return cpfPacienteInput;
    }

    public void setCpfPacienteInput(UIInput cpfPacienteInput) {
        this.cpfPacienteInput = cpfPacienteInput;
    }

    public UIInput getDataConsultaInput() {
        return dataConsultaInput;
    }

    public void setDataConsultaInput(UIInput dataConsultaInput) {
        this.dataConsultaInput = dataConsultaInput;
    }
    
    
    
    
    public NovaConsulta() {
        dadosConsulta = new Consulta();
        estado = LoginSM.inicio();
    }


   public Consulta getDadosConsulta() {
        return dadosConsulta;
    }


    public void setDadosConsulta(Consulta dadosConsulta) {
        this.dadosConsulta = dadosConsulta;
    }
    
    public String recomecar() {
       FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
       return "index?faces-redirect=true";
   }

   public String gravarConsulta() throws SQLException, NamingException {
       consultaDao.gravarConsulta(dadosConsulta);
       FacesContext facesContext = FacesContext.getCurrentInstance();
       Flash flash = facesContext.getExternalContext().getFlash();
       flash.setKeepMessages(true);
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Consulta marcada com sucesso!"));

       return recomecar();
   }
   
   public void procurarUsuario() {
        try {            
            String login = (String) loginInput.getValue();
            String senha = (String) senhaInput.getValue();
            if (senha != null && login != null) {
                Privilegio p = privilegioDao.buscarPrivilegio(login);
                
                if (p != null) {
                    if (p.getPrivilegio() == PrivilegioDAO.PrivilegioEnum.PACIENTE.getValor()) {
                        Paciente pa = pacienteDao.buscarPaciente(login);                        
                        if (pa.getSenha().equals(senha)) {
                            cpfPacienteInput.setValue(login);
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

}