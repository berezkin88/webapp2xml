<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Success</title>
</head>
<style>
*+* {
	margin-top: 1.2em;
}

body {
	margin: 0;
}

#container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: auto 0 auto 0;
  height: 100vh;
  justify-content: center;
}

#search-carts {
  display: grid;
  grid-template-columns: 0.75fr 1fr 0.75fr 1fr;
  grid-template-rows: 1fr 1fr 1fr;
  width: 435px;
  border: 1px solid aquamarine;
  border-radius: 1em;
  background-color: azure;
}

#header-for-search-pane {
  grid-column: 1/5;
  text-align: center;
}

#set-data-range-from {
  grid-column: 1/2;
  grid-row: 2/3;
}

#from {
  grid-column: 2/3;
  grid-row: 2/3;
  place-self: center;
  width: 100%;
  padding: 0;
}

#submit-search {
  grid-column: 1/5;
  grid-row: 3/4;
  border-radius: 1em;
}

#data-range-till {
  grid-column: 3/4;
  grid-row: 2/3;
  place-self: center;
}

#data-range-from {
  place-self: center;
}

#till {
  place-self: center;
  width: 100%;
  padding: 0;
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
    <div id="container">
        <div class="header">
            <h1>Checkout Successful!</h1>
        </div>
        <form id="search-carts" action="./history">
        	<input type="hidden" class="data-fields" name="userid" value='<c:out value="${userId}" />'>
        	<input type="hidden" class="data-fields" name="cartid" value='<c:out value="${cartId}" />'>
            <h2 id="header-for-search-pane">Search for old bills</h2>
            <label for="from" id="data-range-from">From</label>
            <input type="date" id="from" name="from" required>
            <label for="till" id="data-range-till">Till</label>
            <input type="date" id="till" name="till" required>
            <input type="submit" value="Get orders" id="submit-search">
        </form>
        <div id="back-to-shop">
        	<button id="to-shop">
        		<a href='http://localhost:8080/webapp2/loginservlet?userid=<c:out value="${userId}" />'>Return to the shop</a>
        	</button>
        </div>
    </div>
</body>

</html>