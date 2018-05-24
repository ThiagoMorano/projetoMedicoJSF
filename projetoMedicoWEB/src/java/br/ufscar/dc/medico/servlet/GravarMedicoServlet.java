/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.medico.servlet;

import br.ufscar.dc.medico.bean.CadastroMedicoFormBean;
import br.ufscar.dc.medico.bean.Medico;
import br.ufscar.dc.medico.bean.Privilegio;
import br.ufscar.dc.medico.dao.ConsultaDAO;
import br.ufscar.dc.medico.dao.MedicoDAO;
import br.ufscar.dc.medico.dao.PrivilegioDAO;
import java.io.IOException;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author 496227
 */
@SessionScoped
@WebServlet(name = "GravarMedicoServlet", urlPatterns = {"/admin/gravarMedico"})
public class GravarMedicoServlet extends HttpServlet {
    
    @Resource(name =  "jdbc/MedicoDBLocal")
    DataSource dataSource;
    
    @Inject
    MedicoDAO mdao;
    @Inject
    PrivilegioDAO pdao;
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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
       CadastroMedicoFormBean cmfb = (CadastroMedicoFormBean) request.getSession().getAttribute("novoMedico");
       request.getSession().removeAttribute("novoMedico");
              
        try {
            Medico m = new Medico();
            m.setNome(cmfb.getNome());
            m.setSenha(cmfb.getSenha());
            m.setEspecialidade(cmfb.getEspecialidade());
            m.setCrm(cmfb.getCrm());
            
            mdao.gravarMedico(m);
            
            Privilegio p = new Privilegio();
            p.setLogin(cmfb.getCrm());
            p.setPrivilegio(1);
            //PrivilegioDAO pdao = new PrivilegioDAO(dataSource);
            pdao.gravarPrivilegio(p);
            request.setAttribute("mensagem", "Médico cadastrado com successo!");
            request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
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
