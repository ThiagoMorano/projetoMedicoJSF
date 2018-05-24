<%-- 
    Document   : novaConsulta
    Created on : 24/04/2018, 01:54:15
    Author     : Lucas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Projeto Médico</title>
    </head>
    <body>
        <h1>Marcar Consulta</h1>
        <c:if test="${!empty requestScope.mensagens}">
            <ul class="erro">
            <c:forEach items="${requestScope.mensagens}" var="mensagem">
                <li>${mensagem}</li>
            </c:forEach>
            </ul>
            <hr>
        </c:if>
        <form action="/ProjetoMedico/novaConsulta" method="POST">
            <input type="hidden" value="${sessionScope.login.login}" name="cpfPaciente"/>
            <select id="medico" name="crmMedico">
                <option value="">Selecione um médico</option>
                <c:forEach items="${medicos}" var="medico">
                    <c:choose>
                        <c:when test="${sessionScope.novaConsulta.crmMedico == medico.crm}">
                            <option value="${medico.crm}" selected>${medico.nome}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${medico.crm}">${medico.nome}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
            <input type="date" name="dataConsulta" value="${sessionScope.novaConsulta.dataConsulta}"/>
            <input type="submit" />
        </form>
        
    </body>
</html>
