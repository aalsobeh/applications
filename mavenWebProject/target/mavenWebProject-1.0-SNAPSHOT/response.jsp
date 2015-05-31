<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : response
    Created on : May 30, 2015, 5:52:09 PM
    Author     : AnasR
--%>
<sql:query var="studentRegistrationQuery" dataSource="MySqlDS">
    Select * from Student, Registration
    Where Registration.studentId = ?<sql:param value="${param.id}"/>
</sql:query>
    
<c:set var="registrationDetails" value="${studentRegistrationQuery.rows[0]}"/>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table border="0">
            <thead>
                <tr>
                    <th colspan="2">${studentRegistrationQuery.name}</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><strong>Description: </strong></td>
                    <td><span style="font-size:smaller; font-style:italic;">${studentRegistrationQuery.id}</span></td>
                </tr>
                <tr>
                    <td><strong>Student: </strong></td>
                    <td>${studentRegistrationQuery.name}
                        <br>
                        <span style="font-size:smaller; font-style:italic;">
                        Course Id: ${studentRegistrationQuery.courseId}</span>
                    </td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
