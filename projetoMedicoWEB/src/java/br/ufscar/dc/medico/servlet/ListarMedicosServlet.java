/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.medico.servlet;

import br.ufscar.dc.medico.bean.Medico;
import br.ufscar.dc.medico.dao.MedicoDAO;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author 619710
 */
@WebServlet(name = "ListarMedicosServlet", urlPatterns = {"/ListarMedicosServlet"})
public class ListarMedicosServlet extends HttpServlet {
    @Resource(name="jdbc/MedicoDBLocal")
    DataSource dataSource;

    
    @Inject
    MedicoDAO mdao;
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
        try {

//            MedicoDAO mdao = new MedicoDAO(dataSource);
            String especialidade = request.getParameter("especialidade");
            List<Medico> todosMedicos = null;

            if (especialidade == null){
                todosMedicos = mdao.listarTodosMedicos();
            }else{
                todosMedicos = mdao.listarTodosMedicosPorEspecialidade(especialidade);
            }
            
            request.setAttribute("listaMedicos", todosMedicos);
            request.getRequestDispatcher("listaMedicos.jsp").forward(request, response);
        }   catch(Exception e) {    
            e.printStackTrace();
            request.setAttribute("mensagem", e.getLocalizedMessage());
            request.getRequestDispatcher("500.html").forward(request, response);
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
