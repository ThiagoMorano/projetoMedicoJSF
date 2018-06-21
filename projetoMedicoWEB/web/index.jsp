<%-- 
    Document   : index
    Created on : 12/04/2018, 10:23:45
    Author     : 619680
--%>

<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:remove scope="session" var="novoPaciente" />
<html>
    <head>
	<title>Home - ProjetoMedico</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="/ProjetoMedico/images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/vendor/animate/animate.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/vendor/select2/select2.min.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/css/util.css">
	<link rel="stylesheet" type="text/css" href="/ProjetoMedico/css/main.css">
<!--===============================================================================================-->
</head>
    <body>
        <div class="limiter">
            <div class="container-login100">
                <div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-50">
                    <c:if test="${!empty mensagem}">
                        <div align="center">
                            <p>${mensagem}</p>
                        </div>
                        <hr>
                    </c:if>
                    <span class="login100-form-title p-b-15">
                        ProjetoMedicoWEB
                    </span>
                        <div align="center">
                            <c:choose>
                                <c:when test="${empty sessionScope.login}">
                                    <a href="/ProjetoMedico/login">Login</a> |
                                </c:when>
                                <c:otherwise>
                                    <a href="/ProjetoMedico/logout">Logout</a> |
                                </c:otherwise>
                            </c:choose>
                            <a href="/ProjetoMedico/ListarMedicosServlet">Lista de Médicos</a> |
                            <a href='/ProjetoMedico/admin'> Painel de Controle</a>
                        </div>
                    <span class="login100-form-title p-t-15 p-b-15">
                        Paciente
                    </span>
                        <div align="center">
                            <a href="/ProjetoMedico/novaConsulta">Marcar consulta</a> |
                            <a href="/ProjetoMedico/listarConsultas">Listar minhas consultas</a>
                        </div>
                    </ul>

                    <span class="login100-form-title p-t-15 p-b-15">
                        Médico
                    </span>
                    <div align="center">
                        <a href="/ProjetoMedico/listarConsultas">Listar minhas consultas</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
