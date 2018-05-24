<%-- 
    Document   : erro
    Created on : 24/04/2018, 20:48:02
    Author     : Lucas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Erro - Projeto Medico WEB</title>
    </head>
    <body>
        <div id="httpcat" align="center">
            <div id="catimage">
                <img src="/ProjetoMedico/500.jpeg" alt="Error board from HTTPCAT"/>
                <c:if test="${!empty mensagem}">
                    <div align="center">
                        <p>${mensagem}</p>
                    </div>
                    <hr>
                </c:if>
            </div>
            <a href='/ProjetoMedico/'> VOLTAR </a>
        </div>
    </body>
</html>
