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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>


    <c:set var="descriptionPlaceholder" value="This is a description placeholder only for testing purposes, unfortunetely the discription for this item wasn't found in the database. This text isn't inteded for the real app and will not appear there, as the new clean data will have all the descriptions added."/>

    <c:set var="page" value="${requestScope.currentPage}"/>

    <script>
        function clearAndSubmit() {
            window.location.href = 'search-resources';
            sessionStorage.removeItem('sidebarScroll');
        }

        function tagClicked(e) {
            const form = document.getElementById("searchForm");

            // Clear all filter fields manually
            document.querySelectorAll("input").forEach(input => {
                if (input.type === "checkbox" || input.type === "radio") {
                    input.checked = false;
                } else if (input.type !== "hidden") {
                    input.value = "";
                }
            });

            // Set the search box to the clicked tag's text
            document.getElementById("searchBox").value = e.textContent;

            // Reset pagination explicitly
            const pageInput = document.getElementById("pageInput");
            if (pageInput) {
                pageInput.value = "1";
            }

            // Submit the form
            form.submit();
        }

        function pageChanged(delta) {
            const pageInput = document.getElementById("pageInput");

            document.getElementById("searchBox").value = null;

            let newPage = parseInt(pageInput.value || "1", 10) + delta;

            if (newPage < 1) newPage = 1; // Prevent going below page 1

            pageInput.value = newPage;
            document.getElementById("searchForm").submit();
        }
        document.addEventListener("DOMContentLoaded", function () {
            const form = document.getElementById("searchForm");
            const pageInput = document.getElementById("pageInput");

            if (form && pageInput) {
                form.addEventListener("submit", function () {
                    pageInput.value = "1";
                });
            }
        });
    </script>
</head>
<body>

<!-- Include Navigation Bar -->
<%@ include file="../imports/navbar.html" %>

<!-- Search Bar -->
<div class="search-container">
    <form id="searchForm" class="search-form" action="search-resources" method="GET">
        <input id="searchBox" type="text" class="search-box" value="${param.query}" placeholder="Search..." name="query">
        <button type="submit" class="search-button">Search</button>
        <button type="button" class="search-button" onclick="clearAndSubmit()" >Show All</button>

        <!-- Paging controls keep page -->
        <input type="hidden" id="pageInput" name="page" value="${param.page}"/>
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
            <c:when test="${requestScope.totalResources == 0}">
                <h2>No Resources Found</h2>
            </c:when>
            <c:otherwise>
                <div id="resultSizeWrapper">
                <h2>${requestScope.totalResources} results</h2>
                <!-- Pagination Controls -->
                <div class="pagination">
                    <c:if test="${page ne 1}">
                        <i class="fas fa-chevron-left" onclick="pageChanged(-1)"></i>
                    </c:if>
                    <span>Page ${page} of ${requestScope.noOfPages}</span>
                    <c:if test="${page lt requestScope.noOfPages}">
                        <i class="fas fa-chevron-right" onclick="pageChanged(1)"></i>
                    </c:if>
                </div>
                </div>
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
                                            <span class="tag" onclick="tagClicked(this)">${keyword}</span>
                                        </c:forEach>
                                    </div>
                                </div>
                                <div class="result-actions">
                                    <button  onclick="window.open('${resource.url}', '_blank')" >Link</button>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </c:otherwise>
        </c:choose>

        <!-- Pagination Controls -->
        <c:if test="${requestScope.totalResources != 0}">
            <div class="pagination">
                <c:if test="${page ne 1}">
                    <i class="fas fa-chevron-left" onclick="pageChanged(-1)"></i>
                </c:if>
                <span>Page ${page} of ${requestScope.noOfPages}</span>
                <c:if test="${page lt requestScope.noOfPages}">
                    <i class="fas fa-chevron-right" onclick="pageChanged(1)"></i>
                </c:if>
            </div>
        </c:if>

    </div>
</div>

<%@ include file="../imports/footer.html" %>
</body>
</html>
