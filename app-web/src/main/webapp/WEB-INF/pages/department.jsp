<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
	<h1>Department: ${department.name}</h1>

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
						<input type="button"  onclick="location.href='/employee/${employee.id}'" value="Select">
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

	<br>
	<br>

	<input type="button" onclick="location.href='/department/edit/${department.id}'" value="Edit department">

	<form:form method="POST" action="/department/delete">
		<input type="hidden" name = "departmentToDeleteId" value = ${department.id}>
		<input type="submit" value="Delete department" >
	</form:form>
</body>
</html>