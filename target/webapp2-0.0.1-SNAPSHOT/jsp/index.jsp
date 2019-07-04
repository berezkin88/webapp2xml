<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Login Form</title>
</head>
<style>
body {
	background-color: aliceblue;
	position: inherit;
	display: flex;
	width: 100%;
	min-height: 38em;
	margin-top: 0;
}

*+* {
	margin-top: 1.2em;
}

#login-form {
	display: flex;
	flex-flow: column;
	margin: auto;
	width: 14em;
	align-items: center;
	background-color: dimgrey;
	border-radius: 18px;
}

h3 {
	margin: 0;
}
</style>

<body>
	<form action="./loginservlet" method="post" id="login-form">
		<label for="login-form-username">Username</label>
        <input type="text" name="username" id="login-form-username" required>
        <label for="login-form-password">Password</label>
        <input type="text" name="password" id="login-form-password" required>
        <input type="submit" id="login-form-submit" value="Submit">
	</form>
</body>

</html>