/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.medico.views; 

import br.ufscar.dc.medico.bean.Consulta;
import br.ufscar.dc.medico.dao.ConsultaDAO;
import br.ufscar.dc.medico.dao.MedicoDAO;
import br.ufscar.dc.medico.dao.PacienteDAO;
import java.io.Serializable;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
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
   


    public NovaConsulta() {
        dadosConsulta = new Consulta();
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
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Achou que não ia ter consulta? Achou errado, otário"));

       return recomecar();
   }
}