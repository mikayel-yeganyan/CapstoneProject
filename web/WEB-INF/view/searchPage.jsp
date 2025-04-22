<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Search Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/searchResults.css">


</head>
<body>

<!-- Include Navigation Bar -->
<%@ include file="../imports/navbar.html" %>

<!-- Search Bar -->
<div class="search-container">
    <form class="search-form" action="search-resources" method="GET">
        <input type="text" class="search-box" placeholder="Search..." name="search-box">
        <button type="submit" class="search-button">Search</button>
    </form>
</div>

<!-- Main Content -->
<div class="main-container">
    <!-- Sidebar (Filters) -->
    <!-- Include the sidebar -->
    <%@ include file="../imports/sidebar.jsp" %>

    <!-- Search Results -->
    <div class="results-container">
        <c:choose>
            <c:when test="${empty requestScope.searchResult}">
                <h2>No Resources Found</h2>
            </c:when>
            <c:otherwise>
                <h2>${requestScope.searchResult.size()} results</h2>
                <ul>
                    <c:forEach items="${requestScope.searchResult}" var="resource">
                        <li>
                            <div class="result-item">
                                <div class="result-title">
                                    <p>${resource.title}</p>
                                    <div class="result-meta">
                                        <p>
                                                ${resource.developer}
                                                ${(not empty resource.type) ? (' - '.concat(resource.type)) : ''}
                                                ${(not empty resource.description) ? (' - '.concat(resource.description)) : (not empty resource.keywords ? ' - '.concat(resource.keywords) : '')}

                                        </p>
                                    </div>
                                </div>
                                <div class="result-actions">
                                    <button  onclick="location.href='${resource.url}'">Link</button>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </c:otherwise>
        </c:choose>


    </div>
</div>

<%@ include file="../imports/footer.html" %>
</body>
</html>
