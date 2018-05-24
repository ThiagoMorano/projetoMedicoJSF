/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.medico.servlet;

import br.ufscar.dc.medico.bean.Consulta;
import br.ufscar.dc.medico.bean.Medico;
import br.ufscar.dc.medico.bean.Paciente;
import br.ufscar.dc.medico.bean.Privilegio;
import br.ufscar.dc.medico.dao.ConsultaDAO;
import br.ufscar.dc.medico.dao.MedicoDAO;
import br.ufscar.dc.medico.dao.PrivilegioDAO;
import br.ufscar.dc.medico.dao.PacienteDAO;
import br.ufscar.dc.medico.dao.PrivilegioDAO.PrivilegioEnum;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author 619680
 */
@WebServlet(name = "ListarConsultasServlet", urlPatterns = {"/listarConsultas"})
public class ListarConsultasServlet extends HttpServlet {
        
    @Resource(name="jdbc/MedicoDBLocal")
    DataSource dataSource;
    
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

        try {
            
            Privilegio p = (Privilegio) request.getSession().getAttribute("login");
            if (p != null) {
                List<Consulta> consultas = null;
                
                ConsultaDAO cdao = new ConsultaDAO(dataSource);
                // Usuário é Paciente
                if (p.getPrivilegio() == PrivilegioEnum.PACIENTE.getValor()) {
                    consultas = cdao.listarConsultasPaciente(p.getLogin());
                } else if (p.getPrivilegio() == PrivilegioEnum.MEDICO.getValor()) {
                    consultas = cdao.listarConsultasMedico(p.getLogin());
                } else if (p.getPrivilegio() == PrivilegioEnum.ADMIN.getValor()) {
                    consultas = cdao.listarTodasAsConsultas();
                }

                request.setAttribute("consultas", consultas);
                request.getRequestDispatcher("/listaConsultas.jsp").forward(request, response);               
            } else {
                response.sendRedirect("/ProjetoMedico/login?next=/ProjetoMedico/listarConsultas");
            }
            
        }   catch(Exception e) {    
            e.printStackTrace();
            request.setAttribute("mensagem", e.getLocalizedMessage());
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
        };
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
