/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.medico.dao;

import br.ufscar.dc.medico.bean.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author 619680
 */
@RequestScoped
public class PacienteDAO {
    private final static String CRIAR_PACIENTE_SQL = "insert into Paciente"
            + " (nome, cpf, senha, telefone, sexo, dataDeNascimento)"
            + " values (?,?,?,?,?,?)";

    private final static String BUSCAR_PACIENTE_SQL = "select"
            + " id, nome, cpf, senha, telefone, sexo, dataDeNascimento"
            + " from Paciente"
            + " where cpf=?";
    
    @Resource (name = "jdbc/MedicoDBLocal")
    DataSource dataSource;
    
    public Paciente gravarPaciente(Paciente p) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(CRIAR_PACIENTE_SQL, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCpf());
            ps.setString(3, p.getSenha());
            ps.setString(4, p.getSexo());
            ps.setString(5, p.getTelefone());
            ps.setDate(6, new java.sql.Date(p.getDataDeNascimento().getTime()));
            ps.execute();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                p.setId(rs.getInt(1));
            }
        }
        return p;
    }

    public Paciente buscarPaciente(String cpf) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(BUSCAR_PACIENTE_SQL)) {
            ps.setString(1, cpf);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Paciente p = new Paciente();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setSenha(rs.getString("senha"));
                    p.setSexo(rs.getString("sexo"));
                    p.setCpf(rs.getString("cpf"));
                    p.setTelefone(rs.getString("telefone"));
                    p.setDataDeNascimento(new Date(rs.getDate("dataDeNascimento").getTime()));
                    return p;
                }
            }
        }
        
        return null;
    }
}
