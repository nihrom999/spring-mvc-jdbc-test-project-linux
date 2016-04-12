<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
	<h1>Department List test</h1>

	<c:if test="${!departmentList.isEmpty()}">
		<table border="1">
			<tr>
				<td>Name</td>
			</tr>
			<c:forEach items="${departmentList}" var="department">
				<tr>
					<td>${department.name}</td>
					<td>
						<input type="button"  onclick="location.href='/department/${department.id}'" value="Select">
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

	<input type="button"  onclick="location.href='/department/create'" value="Add new department">
	<br>
	<br>
	<a href="/employeeList"> Go to employee list</a>

</body>
</html>