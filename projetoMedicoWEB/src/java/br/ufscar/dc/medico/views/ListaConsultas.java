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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.component.UIInput;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;

/**
 *
 * @author Nicholas
 */
@Named
@ViewScoped
public class ListaConsultas implements Serializable {
    List<Consulta> listaConsultas;
    List<Consulta> consultasFiltradas;
    List<String> crms;
    
    UIInput loginInput;
    UIInput senhaInput;
    
    @Inject ConsultaDAO consultaDao;
    @Inject PacienteDAO pacienteDao;
    @Inject MedicoDAO medicoDao;
    @Inject PrivilegioDAO priDao;
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

    @PostConstruct
    public void init() {
        try {
            crms = consultaDao.listarTodosCrms();
            estado = LoginSM.inicio();
        } catch (SQLException ex) {
            Logger.getLogger(ListaConsultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public List<Consulta> getListaConsultas() {
        return listaConsultas;
    }

    public void setListaConsultas(List<Consulta> listaConsultas) {
        this.listaConsultas = listaConsultas;
    }
    
     public List<Consulta> getConsultasFiltradas() {
        return consultasFiltradas;
    }

    public void setConsultasFiltradas(List<Consulta> consultasFiltradas) {
        this.consultasFiltradas = consultasFiltradas;
    }

    public List<String> getCrms() {
        return crms;
    }

    public void setCrms(List<String> crms) {
        this.crms = crms;
    }
    
    
    public String verTodasConsultas() throws SQLException, NamingException {
        listaConsultas = consultaDao.listarTodasAsConsultas();
        return "listaConsultas";
    }
    
    public String verTodasConsultas(String crm) throws SQLException, NamingException {
        listaConsultas = consultaDao.listarConsultasMedico(crm);
        return "listaConsultas";
    }
    
    public void procurarUsuario() throws NamingException {
        try {
            this.listaConsultas = null;
            
            String login = (String) loginInput.getValue();
            String senha = (String) senhaInput.getValue();
            if (senha != null && login != null) {
                Privilegio p = priDao.buscarPrivilegio(login);
                
                if (p != null) {
                    if (p.getPrivilegio() == PrivilegioDAO.PrivilegioEnum.PACIENTE.getValor()) {
                        Paciente pa = pacienteDao.buscarPaciente(login);                        
                        if (pa.getSenha().equals(senha)) {
                            this.listaConsultas = consultaDao.listarConsultasPaciente(p.getLogin());
                        }
                    } else if (p.getPrivilegio() == PrivilegioDAO.PrivilegioEnum.MEDICO.getValor()) {
                        Medico m = medicoDao.buscarMedico(login);
                        if (m.getSenha().equals(senha)) {
                            this.listaConsultas = consultaDao.listarConsultasMedico(p.getLogin());
                        }
                    } else if (p.getPrivilegio() == PrivilegioDAO.PrivilegioEnum.ADMIN.getValor()) {
                        if ("coutinho".equals(senha)) {
                            this.listaConsultas = consultaDao.listarTodasAsConsultas();
                        }
                    }

                    if (this.listaConsultas != null) {
                        this.estado = LoginSM.logou();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
