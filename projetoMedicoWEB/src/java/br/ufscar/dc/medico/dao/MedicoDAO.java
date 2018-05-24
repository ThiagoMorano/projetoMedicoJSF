/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.medico.dao;

import br.ufscar.dc.medico.bean.Medico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author 619710
 */
public class MedicoDAO {
    private final static String CRIAR_MEDICO_SQL = "insert into Medico"
            + " (nome, crm, senha, especialidade)"
            + " values (?,?,?,?)";
    
    private final static String BUSCAR_MEDICO_SQL = "select "
            + "nome, crm, senha, especialidade "
            + "from Medico "
            + "where crm=?";
    
    private final static String LISTAR_MEDICOS_SQL = "select "
            + "nome, crm, senha, especialidade "
            + "from Medico ";
            
    private final static String LISTAR_MEDICOS_POR_ESPECIDADE_SQL = "select "
            + "nome, crm, senha, especialidade "
            + "from Medico "
            + "where especialidade=(?)";
    
    DataSource dataSource;
    
    public MedicoDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }
    
    public Medico gravarMedico(Medico m) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(CRIAR_MEDICO_SQL, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, m.getNome());
            ps.setString(2, m.getCrm());
            ps.setString(3, m.getSenha());
            ps.setString(4, m.getEspecialidade());
            ps.execute();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                m.setId(rs.getInt(1));
            }
        }
        return m;
    }

    public Medico buscarMedico(String crm) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(BUSCAR_MEDICO_SQL)) {
            ps.setString(1, crm);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                Medico m = new Medico();
                m.setId(rs.getInt("id"));
                m.setNome(rs.getString("nome"));
                m.setSenha(rs.getString("senha"));
                m.setEspecialidade(rs.getString("especialidade"));
                m.setCrm(rs.getString("crm"));
                return m;
            }
        }
    }
    
    public List<Medico> listarTodosMedicos() throws SQLException, NamingException {
        List<Medico> ret = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(LISTAR_MEDICOS_SQL)) {


            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Medico m = new Medico();
                    m.setNome(rs.getString("nome"));
                    m.setEspecialidade(rs.getString("especialidade"));
                    m.setCrm(rs.getString("crm"));
                    ret.add(m);
                }
            }
        }
        return ret;
    }
    
    public List<Medico> listarTodosMedicosPorEspecialidade(String especialidade) throws SQLException {
        List<Medico> ret = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(LISTAR_MEDICOS_POR_ESPECIDADE_SQL)) {


            ps.setString(1, especialidade);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Medico m = new Medico();
                    m.setNome(rs.getString("nome"));
                    m.setEspecialidade(rs.getString("especialidade"));
                    m.setCrm(rs.getString("crm"));
                    ret.add(m);
                }
            }
        }
        return ret;
    }
}