/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.medico.servlet;

import br.ufscar.dc.medico.bean.CadastrarNovoPaciente;
import br.ufscar.dc.medico.bean.Paciente;
import br.ufscar.dc.medico.bean.Privilegio;
import br.ufscar.dc.medico.dao.ConsultaDAO;
import br.ufscar.dc.medico.dao.PacienteDAO;
import br.ufscar.dc.medico.dao.PrivilegioDAO;
import java.io.IOException;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import javax.inject.Inject;

/**
 *
 * @author 619710
 */
@WebServlet(name = "GravarPacienteServlet", urlPatterns = {"/admin/GravarPacienteServlet"})
public class GravarPacienteServlet extends HttpServlet {
    
    @Resource(name="jdbc/MedicoDBLocal")
    DataSource dataSource;

    @Inject
    PacienteDAO pdao;
    @Inject
    PrivilegioDAO pridao;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        CadastrarNovoPaciente cnp = (CadastrarNovoPaciente) request.getSession().getAttribute("novoPaciente");
        request.getSession().removeAttribute("novoPaciente");
        
        //PacienteDAO pdao = new PacienteDAO(dataSource);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataNascimento = null;
        try {
            dataNascimento = sdf.parse(cnp.getDataDeNascimento());
        
            Paciente p = new Paciente();
            p.setNome(cnp.getNome());
            p.setSenha(cnp.getSenha());
            p.setTelefone(cnp.getTelefone());
            p.setCpf(cnp.getCpf());
            p.setSexo(cnp.getSexo());
            p.setDataDeNascimento(dataNascimento);
            p = pdao.gravarPaciente(p);
            
            Privilegio pri = new Privilegio();
            pri.setLogin(cnp.getCpf());
            pri.setPrivilegio(0);
            //PrivilegioDAO pridao = new PrivilegioDAO(dataSource);
            pridao.gravarPrivilegio(pri);
            
            request.setAttribute("mensagem", "Paciente cadastrado.");
            request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("mensagem", e.getLocalizedMessage());
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
