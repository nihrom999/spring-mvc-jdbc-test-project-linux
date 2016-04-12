<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>

<form:form method="POST" action="/employee/edit">

	<input type="hidden" name = "id" value = ${employee.id}>

	<table>
		<tr>
			<td>First name:</td> <td><input type="text" name = "firstName" value = "${employee.firstName}"></td>
		</tr>
		<tr>
			<td>Last name:</td> <td><input type="text" name = "lastName" value = "${employee.lastName}"></td>
		</tr>
		<tr>
			<td>Date of birth:</td> <td><input type="date" name = "dateOfBirth" value = "${employee.dateOfBirth}"></td>
		</tr>
		<tr>
			<td>Salary:</td> <td><input type="number" name = "salary" value = "${employee.salary}"></td>
		</tr>
		<tr>
			<td>
				Department:
				<br>
				${depName}
			</td>
		</tr>
	</table>

	<input type="hidden" name = "departmentId" value=${employee.departmentId}>
	<input type="submit" value="Submit">
	<input type="button" onclick="location.href='/'" value="Cancel">
</form:form>

</body>
</html>