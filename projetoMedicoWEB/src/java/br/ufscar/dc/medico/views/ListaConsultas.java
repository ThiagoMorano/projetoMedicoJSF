/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.medico.views;

import br.ufscar.dc.medico.bean.Consulta;
import br.ufscar.dc.medico.dao.ConsultaDAO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
    @Inject ConsultaDAO consultaDao;

    @PostConstruct
    public void init() {
        try {
            listaConsultas = consultaDao.listarTodasAsConsultas();
            crms = consultaDao.listarTodosCrms();
        } catch (SQLException ex) {
            Logger.getLogger(ListaConsultas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
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
}
