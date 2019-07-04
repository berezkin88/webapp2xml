<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
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

    #history {
        height: 100vh;
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    /* temporary css */
    [id*="item-details-"] {
        background-color: azure;
        display: flex;
        align-items: center;
        justify-content: space-around;
        border-radius: 1em;
        border: 1px solid;
        text-align: center;
    }

    /* temporary css*/
    [id*="item-details-"]>* {
        margin: 0;
    }

    #items {
        min-width: 34em;
        margin: 10% auto 0 auto;
    }

    .title {
        flex: 1 1 auto;
        padding-left: 0.5em;
    }

    .description {
        flex: 2 1 auto;
        padding: 0 1em 0 1em;
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
        margin: 10% auto 0;
    }

    #total {
        flex: 1 1 auto;
        max-height: 10vh;
        margin: 2% auto 0 auto;
        display: inherit;
    }

    #total-number {
        font-size: 1.6em;
        font-weight: bold;
    }

    #to-shop {
        width: 22em;
        height: 4em;
        border-radius: 1em;
    }

    a {
        text-decoration: none;
        color: black;
        font-size: 1.6em;
    }
</style>

<body>
    <div id="history">
        <header id="header">
            <h1 id="header-text">Products purchased</h1>
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
				</form>
			</c:forEach>
		</div>
        <div id="total">
            <p id="total-number"></p>
        </div>
        <div id="back-to-shop">
            <button id="to-shop">
                <a href='http://localhost:8080/webapp2/loginservlet?userid=<c:out value="${userId}" />'>Return to the shop</a>
            </button>
        </div>
        <script>calcTotal()</script>
    </div>
</body>

</html>