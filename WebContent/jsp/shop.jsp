<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Document</title>
</head>

<style>
*+* {
	margin-top: 1.2em;
}

body {
	margin: 0;
}

#shop {
	min-height: 40em;
	display: flex;
	flex-direction: column;
}

.item-details {
	background-color: azure;
	display: flex;
	align-items: center;
	justify-content: space-around;
	border-radius: 1em;
	border: 1px solid;
}

#items {
	min-width: 34em;
	margin: auto;
}

.title {
	flex: 0 1 auto;
	padding-left: 0.5em;
}

.description {
	flex: 2 1 auto;
	padding: 0 1em 0 1em;
}

.item-details>* {
	margin: 0;
}

.item-details-quantity {
	flex: 0 1 auto;
	max-width: 7%;
}

.check {
	flex: 1 1 auto;
}

#items-submit-button {
	float: right;
	width: 8em;
	height: inherit;
	border-radius: 1em;
}

#items-submit {
	width: 100%;
	height: 2em;
}

#header {
	display: inherit;
	margin: auto auto 0;
}
</style>

<body>
	<div id="shop">
		<header id="header">
			<h1 id="header-text">Available items</h1>
		</header>
		<form action="cartservlet" id="items">
			<div id="items-submit">
				<input type="submit" value="Submit" id="items-submit-button">
			</div>
			<c:forEach items="${products}" var="product">
				<div class="item-details">
					<div class="title">
						<h2 class="item-details-header">
							<c:out value="${product.title}" />
						</h2>
					</div>
					<div class="description">
						<p class="item-details-description">
							<c:out value="${product.description}" />
						</p>
					</div>
					<input type="number" name="quantiry" value="0" class="item-details-quantity"> 
					<input type="checkbox" name="productid" value=<c:out value="${product.id}" /> class="check">
				</div>
			</c:forEach>
		</form>
	</div>
</body>
</html>