<link rel="stylesheet" href="././css/sidebar.css">

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
    function uncheckAllFilters() {
        const checkboxes = document.querySelectorAll('.filter-option input[type="checkbox"]');
        checkboxes.forEach(cb => cb.checked = false);
    }
</script>

<div class="sidebar">

    <c:choose>
        <c:when test="${empty sessionScope.types} && ${empty sessionScope.domains} && ${empty sessionScope.targets} && ${empty sessionScope.languages}">
            <h3>No Filters Found!</h3>
        </c:when>

        <c:otherwise>

            <button class="uncheck-button" type="button" onclick="uncheckAllFilters()">Clear</button>

            <c:if test="${not empty sessionScope.types}">
                <h3>Filter by Type</h3>
                <c:forEach var="type" items="${sessionScope.types}">
                    <div class="filter-option">
                        <input type="checkbox" name="types" value="${type}" id="${type}" form="searchForm"
                            <c:if test="${fn:contains(param.types, type)}">checked</c:if> >
                        <label for="${type}">${type}</label>
                    </div>
                </c:forEach>
            </c:if>

            <c:if test="${not empty sessionScope.domains}">
                <h3>Filter by Domain</h3>
                <c:forEach var="domain" items="${sessionScope.domains}">
                    <div class="filter-option">
                        <input type="checkbox" name="domains" value="${domain}" id="${domain}" form="searchForm"
                                <c:if test="${fn:contains(param.domains, domain)}">checked</c:if> >
                        <label for="${domain}">${domain}</label>
                    </div>
                </c:forEach>
            </c:if>

            <c:if test="${not empty sessionScope.targets}">
                <h3>Filter by Target Audience</h3>
                <c:forEach var="target" items="${sessionScope.targets}">
                    <div class="filter-option">
                        <input type="checkbox" name="targets" value="${target}" id="${target}" form="searchForm"
                                <c:if test="${fn:contains(param.targets, target)}">checked</c:if> >
                        <label for="${target}">${target}</label>
                    </div>
                </c:forEach>
            </c:if>

            <c:if test="${not empty sessionScope.languages}">
                <h3>Filter by Language</h3>
                <c:forEach var="lang" items="${sessionScope.languages}">
                    <div class="filter-option">
                        <input type="checkbox" name="languages" value="${lang}" id="${lang}" form="searchForm"
                                <c:if test="${fn:contains(param.languages, lang)}">checked</c:if> >
                        <label for="${lang}">${lang}</label>
                    </div>
                </c:forEach>
            </c:if>
        </c:otherwise>
    </c:choose>
</div>
