<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
	<h1>Department: ${employee.firstName} ${employee.lastName}</h1>

	<table border="1">

		<tr>
			<td>First name</td> <td>Last name</td> <td>DoB</td> <td>Salary</td> <td>Department</td>
		</tr>
		<tr>
			<td>${employee.firstName}</td>
			<td>${employee.lastName}</td>
			<td>${employee.dateOfBirth}</td>
			<td>${employee.salary}</td>
			<td>${departmentName}</td>
		</tr>
	</table>

	<br>
	<br>

	<input type="button" onclick="location.href='/employee/edit/${employee.id}'" value="Edit employee">

	<form:form method="POST" action="/employee/delete">
		<input type="hidden" name = "employeeToDeleteId" value = ${employee.id}>
		<input type="submit" value="Delete employee" >
	</form:form>
</body>
</html>