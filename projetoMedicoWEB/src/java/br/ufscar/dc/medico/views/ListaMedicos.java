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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
    List<Medico> medicosFiltrados;
    List<String> especialidades;
    @Inject MedicoDAO medicoDao;

    @PostConstruct
    public void init() {
        try {
            listaMedicos = medicoDao.listarTodosMedicos();
            especialidades = medicoDao.listarTodasEspecialidades();
        } catch (SQLException ex) {
            Logger.getLogger(ListaMedicos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ListaMedicos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<String> especialidades) {
        this.especialidades = especialidades;
    }
    public List<Medico> getMedicosFiltrados() {
        return medicosFiltrados;
    }

    public void setMedicosFiltrados(List<Medico> medicosFiltrados) {
        this.medicosFiltrados = medicosFiltrados;
    }

    public List<Medico> getListaMedicos() {
        return listaMedicos;
    }

    public void setListaMedicos(List<Medico> listaMedicos) {
        this.listaMedicos = listaMedicos;
    }
}