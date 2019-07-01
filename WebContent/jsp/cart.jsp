<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Cart</title>
</head>
<script>
    function calcTotal() {
        let quantity = document.querySelectorAll(".quantity-number");
        let prices = document.querySelectorAll(".price-number");
        let total = 0;

        document.querySelector("#total-number").innerHTML = 'TOTAL: ';

        for (var i = 0; i < quantity.length; i++) {
            total += quantity[i].innerText * prices[i].innerText;
        }

        document.querySelector("#total-number").append(Math.round(total * 10) / 10.0);
    }
</script>
<style>
*+* {
	margin-top: 1.2em;
}

body {
	margin: 0;
}

#cart {
	min-height: 40em;
	display: flex;
	flex-direction: column;
}

/* temporary css */
[id*="item-details-"] {
	background-color: azure;
	display: flex;
	align-items: center;
	justify-content: space-around;
	border-radius: 1em;
	border: 1px solid;
}

/* temporary css*/
[id*="item-details-"]>* {
	margin: 0;
}

#items {
	min-width: 34em;
	margin: auto auto 0 auto;
}

.title {
	flex: 1 1 auto;
	padding-left: 0.5em;
}

.price {
	flex: 1 1 auto;
}

.item-details>* {
	margin: 0;
}

[class*="item-details-quantity-"] {
	flex: 0 1 auto;
}

.quantity {
	flex: 1 1 auto;
}

#header {
	display: inherit;
	margin: auto auto 0;
}

[id*="items-remove-button-"] {
	margin-right: 0.5em;
}

/* history reference */
.history-reference {
	position: relative;
	float: right;
}

/* history reference */
#history-button {
	float: inherit;
	width: 7em;
	height: 3em;
	border-radius: 1em;
	background-color: darkkhaki;
	font-size: 1em;
	margin: 1em 1em 0 0;
}

#checkout-button {
	align-self: center;
	margin: auto;
}

#checkout {
	width: 6em;
	height: 2em;
	background-color: cadetblue;
	font-size: 2em;
	border-radius: 0.6em;
	color: white;
}

#total {
	flex: 1 1 auto;
	max-height: 1em;
	margin: 0 auto 0 auto;
	display: inherit;
}

#total-number {
	font-size: 1.6em;
	font-weight: bold;
}
</style>

<body>
	<div id="cart">
		<header id="header">
			<h1 id="header-text">Products selected</h1>
		</header>
		<div id="items">
			<c:forEach items="${results}" var="result">
				<form id='item-details-<c:out value="${result.orderId}" />'>
					<div class="title">
						<h2 class="item-details-header">
							<c:out value="${result.title}" />
						</h2>
					</div>
					<div class="quantity">
						<p class="item-details-quantity">
							Quantity: <span class="quantity-number"> <c:out
									value="${result.quantity}" /></span>
						</p>
					</div>
					<div class="price">
						<p class="item-details-price">
							Price: $<span class="price-number"><c:out
									value="${result.price}" /></span>
						</p>
					</div>
					<input type="button"
						onclick='removeRequest<c:out value="${result.orderId}" />()'
						value="Remove"
						id='items-remove-button-<c:out value="${result.orderId}" />'
						itemid='<c:out value="${result.orderId}" />'>
				</form>
			</c:forEach>
		</div>
		<div id="total">
			<p id="total-number">TOTAL:</p>
		</div>
		<form id="checkout-button" method="GET" action="./checkout">
			<input type="hidden" class="data-fields" name="userid" value='<c:out value="${userId}" />'>
			<input type="hidden" class="data-fields" name="cartid" value='<c:out value="${cartId}" />'>
			<button type="submit" id="checkout">Checkout</button>
		</form>
		<script>calcTotal()</script>
	</div>
	<c:forEach items="${results}" var="result">
		<script>
			function removeRequest<c:out value="${result.orderId}" />() {
				let obj = document
						.querySelector('#items-remove-button-<c:out value="${result.orderId}" />');
				let orderid = obj.getAttribute("itemid");

				let xhr = new XMLHttpRequest();
				xhr.open("DELETE", './remove?orderid=' + orderid, true);

				xhr.send();
				
				obj.parentElement.remove();
	            calcTotal();
			}
		</script>
	</c:forEach>
	<!-- <script>
        function checkoutRequest() {
            let cartid = document.querySelector("#cart").getAttribute("cartid");

            let xhr = new XMLHttpRequest();
            xhr.open("PUT", './checkout?cartid=' + cartid, false);

            xhr.send();
            
            location.href="http://localhost:8080/webapp2/jsp/success.jsp";
        }
    </script> -->
</body>
</html>