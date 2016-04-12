<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
	<h2>Creating new department...</h2>
	<form:form method="POST" action="/department/create">

		<table>
			<tr>Department name</tr>
			<tr>
				<input type="text" name = "departmentName" value = "${departmentName}">
			</tr>
		</table>

		<input type="submit" value="Submit">
		<input type="button" onclick="location.href='/'" value="Cancel">
	</form:form>
</body>
</html>