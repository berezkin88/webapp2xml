<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Shop</title>
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

.price {
	flex: 1 1 auto;
}

[id*="item-details-"] {
	background-color: azure;
	display: flex;
	align-items: center;
	justify-content: space-around;
	border-radius: 1em;
	border: 1px solid;
}

[id*="item-details-"]>* {
	margin: 0;
}

.item-details-quantity {
	flex: 0 1 auto;
	max-width: 7%;
}

.check {
	flex: 1 1 auto;
}

#header {
	display: inherit;
	margin: auto auto 0;
}

[id*="items-add-button-"] {
	margin-right: 0.5em;
}

/* cart reference */
.cart-reference {
	position: relative;
	float: right;
}

/* cart reference */
#cart-button {
	float: inherit;
	width: 7em;
	height: 3em;
	border-radius: 1em;
	background-color: darkkhaki;
	font-size: 1em;
	margin: 1em 1em 0 0;
}
</style>

<body>
	<div id="shop">
		<form action="./cart" class="cart-reference">
			<input type="text" class="data-fields" name="userid"
				value='<c:out value="${userId}"/>' style="display: none"> 
			<input type="text" class="data-fields" name="cartid"
				value='<c:out value="${cartId}"/>' style="display: none">
			<button id="cart-button" type="submit">Cart</button>
		</form>
		<header id="header">
			<h1 id="header-text">Available items</h1>
		</header>
		<div id="items">
			<c:forEach items="${products}" var="product">
				<form id='item-details-<c:out value="${product.id}"/>'>
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
					<div class="price">
						<p class="item-details-price">
							$
							<c:out value="${product.price}" />
						</p>
					</div>
					<input type="number" name="quantiry" value="1"
						class="item-details-quantity"> <input type="checkbox"
						name="productid" value=<c:out value="${product.id}"/>
						class="check"
						onchange="document.querySelector('#items-add-button-<c:out value="${product.id}"/>').disabled = !this.checked;">
					<input type="button"
						onclick='sendRequest<c:out value="${product.id}"/>()' value="Add"
						disabled id='items-add-button-<c:out value="${product.id}"/>'>
				</form>
			</c:forEach>
		</div>
	</div>
	<c:forEach items="${products}" var="product">
		<script>
			function sendRequest<c:out value="${product.id}"/>() {
				let obj = document.querySelector('#item-details-<c:out value="${product.id}"/>');
				let quantity = obj[0].valueAsNumber;
				let productid = obj[1].getAttribute("value");
				let cartId = document.querySelector("[name=\"cartid\"]").getAttribute("value");
		
				let xhr = new XMLHttpRequest();
				xhr.open("POST", "./orderservlet?productid=" + productid + "&quantity=" + quantity + "&cartid=" + cartId, true);

				xhr.send();
			}
		</script>
	</c:forEach>
</body>
</html>