<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns:h="http://java.sun.com/jsf/html" xmlns:f="http://java.sun.com/jsf/core">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Anas's JSP Page</title>
    </head>
    <body>
        <h1>Welcome to Registration System!</h1>
    <f:view>
        <h:form id="reg">
            <h2>Add Student </h2>
            <form action="response.jsp">
                <strong> Select a Student : </strong>
                <select name="id">
                    <c:forEach var="row" items="${Students.rows}">
                        <option value="${row.id}">${row.name}</option>
                    </c:forEach>
                </select>
                
                <input type="submit" value="Add" name="submit" />
                
                <sql:query var="Students" dataSource="MySqlDB">
                    SELECT id, name FROM student
                </sql:query>
            </form>
    </f:view>
    </body>
</html>
