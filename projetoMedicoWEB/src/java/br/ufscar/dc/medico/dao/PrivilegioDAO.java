/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.medico.dao;

import br.ufscar.dc.medico.bean.Privilegio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author 619680
 */
@RequestScoped
public class PrivilegioDAO {
    public enum PrivilegioEnum {
        PACIENTE(0), MEDICO(1), ADMIN(2);
        
        private final int valor;
        PrivilegioEnum(int valor) {
            this.valor = valor;
        }
        
        public int getValor() {
            return this.valor;
        }
    } 
    private final static String CRIAR_PRIVILEGIO_SQL = "insert into Privilegio"
            + " (login, privilegio)"
            + " values (?,?)";

    private final static String BUSCAR_PRIVILEGIO_SQL = "select"
            + " id, login, privilegio"
            + " from Privilegio"
            + " where login=?";
    
    @Resource (name = "jdbc/MedicoDBLocal")
    DataSource dataSource;
    
    public Privilegio gravarPrivilegio(Privilegio p) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(CRIAR_PRIVILEGIO_SQL, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, p.getLogin());
            ps.setInt(2, p.getPrivilegio());
            ps.execute();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                p.setId(rs.getInt(1));
            }
        }
        return p;
    }

    public Privilegio buscarPrivilegio(String login) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(BUSCAR_PRIVILEGIO_SQL)) {
            ps.setString(1, login);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Privilegio p = new Privilegio();
                    p.setId(rs.getInt("id"));
                    p.setLogin(rs.getString("login"));
                    p.setPrivilegio(rs.getInt("privilegio"));

                    return p;
                }
            }
        }
        
        return null;
    }
}
