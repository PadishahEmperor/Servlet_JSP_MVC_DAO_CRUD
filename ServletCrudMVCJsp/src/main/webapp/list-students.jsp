<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="servletCRUDApp.*, java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student List</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
    }
    #wrapper {
        width: 80%;
        margin: auto;
    }
    #header {
        background-color: #0073e6;
        color: white;
		/*text-align: center;*/
        padding: 20px;
        border-radius: 10px;
        margin-top: 1%;
    }
    h2 {
        margin: 0;
    }
    
    .btn {
        display: inline-block;
        margin: 20px 0;
        padding: 10px 20px;
        background-color: #28a745;
        color: white;
        text-decoration: none;
        font-size: 16px;
        border-radius: 5px;
        transition: 0.3s;
    }
    
    table {
        width: 80%;
        margin: 20px auto;
        border-collapse: collapse;
        background: white;
        border-radius: 10px;
        overflow: hidden;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }
    th, td {
        padding: 12px;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }
    th {
        background-color: #0073e6;
        color: white;
    }
    
    .th{
    	padding-left: 40px;
    }
    
    tr:hover {
        background-color: #f1f1f1;
    }
</style>
</head>

<% 
    List<Student> theStudents = (List<Student>) request.getAttribute("MY_STUDENT_LIST");
%>

<body>
    <div id="wrapper">
        <div id="header">
            <h2>Chandigarh University</h2>
        </div>
        
        <!-- put a new button -->
		<input type="button" value="Add Student" 
       onclick="window.location.href='add-student-form.jsp';"
       class="btn">
		
        
        <table>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th class="th">Email</th>
                <th align="center" class="th">Action</th>
            </tr>
            <%
               /* for(Student s : theStudents) {
                    out.println("<tr>");
                    out.println("<td>" + s.getFirstName() + "</td>");
                    out.println("<td>" + s.getLastName() + "</td>");
                    out.println("<td>" + s.getEmail() + "</td>");
                    out.println("<td>" + "<a href='#'>Update</a>" + " | " + "<a href='#' onclick='return confirm('Are you sure?')' >Delete</a>"  + "</td>");
                    out.println("</tr>");
                }*/
            %>
            
            <c:forEach var="item" items="${MY_STUDENT_LIST}" >
            	
            	<c:url var="updateLink" value="StudentControllerServlet">
            		<c:param name="command" value="LOAD"/>
            		<c:param name="studentId" value="${item.id}"/>
            	</c:url>
            	
            	<!-- SET UP LINKS FOR DELETING -->
            	
            	<c:url var="deleteLink" value="StudentControllerServlet">
            		<c:param name="command" value="DELETE"/>
            		<c:param name="studentId" value="${item.id}"/>
            	</c:url>
            	
            	<tr>
            	
            		<td>${item.firstName}</td>
            		<td>${item.lastName}</td>
            		<td>${item.email}</td>
            		<td><a href="${updateLink}">Update</a> | <a href="${deleteLink}" onclick="return confirm('Are You Sure?')">Delete</a></td>
            	
            	</tr>
            		
            	
            </c:forEach>
            
        </table>
    </div>
</body>
</html>
