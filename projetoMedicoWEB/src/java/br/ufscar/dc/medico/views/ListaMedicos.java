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
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;

/**
 *
 * @author Thiago
 */
@Named
@RequestScoped
public class ListaMedicos implements Serializable {
    List<Medico> listaMedicos;
    @Inject MedicoDAO medicoDao;


    public List<Medico> getListaMedicos() {
        return listaMedicos;
    }


    public void setListaMedicos(List<Medico> listaMedicos) {
        this.listaMedicos = listaMedicos;
    }
    
    public String verTodosMedicos() throws SQLException, NamingException {
        listaMedicos = medicoDao.listarTodosMedicos();
        return "listaMedicos";
    }
    
    public String verTodosMedicos(String especialidade) throws SQLException, NamingException {
        listaMedicos = medicoDao.listarTodosMedicosPorEspecialidade(especialidade);
        return "listaMedicos";
    }
}