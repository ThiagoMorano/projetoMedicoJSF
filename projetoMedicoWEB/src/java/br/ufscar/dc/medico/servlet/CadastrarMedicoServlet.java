/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.medico.servlet;

import br.ufscar.dc.medico.bean.CadastroMedicoFormBean;
import br.ufscar.dc.medico.bean.Privilegio;
import br.ufscar.dc.medico.dao.PrivilegioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author 619680
 */
@WebServlet(name = "CadastrarMedicoServlet", urlPatterns = {"/admin/CadastrarMedico"})
public class CadastrarMedicoServlet extends HttpServlet {

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
        Privilegio p = (Privilegio) request.getSession().getAttribute("login");
        if ((p != null) && (p.getPrivilegio() == PrivilegioDAO.PrivilegioEnum.ADMIN.getValor())) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                CadastroMedicoFormBean cmfb = new CadastroMedicoFormBean();
                try {
                    // Obs: BeanUtils é uma classe da biblioteca
                    // Apache Commons BeanUtils
                    // http://commons.apache.org/beanutils/
                    BeanUtils.populate(cmfb, request.getParameterMap());
                    List<String> mensagens = cmfb.validar();
                    request.getSession().setAttribute("novoMedico", cmfb);
                    if (mensagens == null) {
                        request.getRequestDispatcher("/admin/confirmarMedico.jsp").forward(request, response);
                    } else {
                        request.setAttribute("mensagens", mensagens);
                        request.getRequestDispatcher("/admin/cadastroMedicoForm.jsp").forward(request, response);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("mensagem" + e.getLocalizedMessage());
                    request.setAttribute("mensagem", e.getLocalizedMessage());
                    request.getRequestDispatcher("/erro.jsp").forward(request, response);
                }
            } else {
                request.getRequestDispatcher("/admin/cadastroMedicoForm.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("/ProjetoMedico/login?next=/ProjetoMedico/admin/CadastrarMedico");
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
