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

    <c:set var="descriptionPlaceholder" value="This is a description placeholder only for testing purposes, unfortunetely the discription for this item wasn't found in the database. This text isn't inteded for the real app and will not appear there, as the new clean data will have all the descriptions added."/>

    <script>
        function clearAndSubmit() {
            window.location.href = 'search-resources';
        }
    </script>
</head>
<body>

<!-- Include Navigation Bar -->
<%@ include file="../imports/navbar.html" %>

<!-- Search Bar -->
<div class="search-container">
    <form id="searchForm" class="search-form" action="search-resources" method="GET">
        <input type="text" class="search-box" value="${param.query}" placeholder="Search..." name="query">
        <button type="submit" class="search-button">Search</button>
        <button type="button" class="search-button" onclick="clearAndSubmit()" >Show All</button>
    </form>
</div>

<!-- Main Content -->
<div class="main-container">
    <!-- Sidebar (Filters) -->
    <!-- Include the sidebar -->
    <jsp:include page="../imports/sidebar.jsp" />

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
                                                ${(not empty resource.description) ? resource.description : descriptionPlaceholder}
                                        </p>
                                    </div>
                                    <!-- Tags -->
                                    <div class="keyword-tags">
                                        <c:forEach items="${resource.keywords}" var="keyword">
                                            <span class="tag">${keyword}</span>
                                        </c:forEach>
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
