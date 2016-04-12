<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>

<form:form method="POST" action="/department/edit">
	<input type="text" name = "departmentName" value = "${departmentName}">
	<input type="hidden" name = "departmentId" value = ${departmentId}>

	<c:if test="${!employeeList.isEmpty()}">
		<table border="1">

			<tr>
				<td>Name</td> <td>Salary</td>
			</tr>
			<c:forEach items="${employeeList}" var="employee">
				<tr>
					<td>${employee.firstName} ${employee.lastName}</td>
					<td>${employee.salary}</td>
					<td>
						<form:form method="POST" action="/employee/delete">
							<input type="hidden" name = "employeeToDeleteId" value = ${employee.id}>
							<input type="submit" value="Delete" >
						</form:form>
					</td>

				</tr>
			</c:forEach>
		</table>
	</c:if>



	<input type="submit" value="Submit">
	<input type="button" onclick="location.href='/'" value="Cancel">
</form:form>

</body>
</html>