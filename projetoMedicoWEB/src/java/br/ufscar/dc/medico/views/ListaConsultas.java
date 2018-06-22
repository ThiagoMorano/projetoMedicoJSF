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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;

/**
 *
 * @author Nicholas
 */
@Named
@RequestScoped
public class ListaConsultas implements Serializable {
    List<Consulta> listaConsultas;
    @Inject ConsultaDAO consultaDao;


    public List<Consulta> getListaConsultas() {
        return listaConsultas;
    }


    public void setListaConsultas(List<Consulta> listaConsultas) {
        this.listaConsultas = listaConsultas;
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
