<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
	<h1>Employee List</h1>

	<c:if test="${!employeeList.isEmpty()}">
		<table border="1">
			<tr>
				<td>Name</td>
				<td>Salary</td>
				<td>Department</td>
			</tr>
			<c:forEach items="${employeeMap}" var="map">
				<tr>
					<td>${map.key.firstName} ${map.key.lastName}</td>
					<td>${map.key.salary}</td>
					<td><a href="/department/${map.value.id}"> ${map.value.name} </a> </td>
					<td>
						<input type="button"  onclick="location.href='/employee/${map.key.id}'" value="Select">
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

	<input type="button"  onclick="location.href='/department/create'" value="Add new department">
	<br>
	<br>
	<a href="/departmentList"> Go to department list </a>

</body>
</html>