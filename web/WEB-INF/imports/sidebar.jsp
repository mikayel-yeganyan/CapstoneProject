<link rel="stylesheet" href="././css/sidebar.css">

<%@ taglib prefix="fn"  uri="jakarta.tags.functions" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<script>
    function uncheckAllFilters() {
        const checkboxes = document.querySelectorAll('.filter-option input[type="checkbox"]');
        checkboxes.forEach(cb => {cb.checked = false; cb.dispatchEvent(new Event('change'));});
    }

    document.addEventListener('DOMContentLoaded', function () {
        const checkboxes = document.querySelectorAll('.filter-option input[type="checkbox"]');
        checkboxes.forEach(function (checkbox) {
            checkbox.addEventListener('change', function () {
                document.getElementById("pageInput").value = 1;
                document.getElementById('searchForm').submit();
            });
        });
    });

    document.addEventListener('DOMContentLoaded', () => {
        const sidebar = document.getElementById('sidebar');
        if (!sidebar) return;

        // Restore scroll position (from sessionStorage to keep it per‐tab)
        const saved = sessionStorage.getItem('sidebarScroll');
        if (saved !== null) {
            sidebar.scrollTop = Number(saved);
        }

        // Whenever the user scrolls the sidebar, update the stored value
        sidebar.addEventListener('scroll', () => {
            sessionStorage.setItem('sidebarScroll', sidebar.scrollTop.toString());
        });
    });
</script>

<div id="sidebar" class="sidebar">

    <c:choose>
        <c:when test="${empty sessionScope.types and empty sessionScope.domains and empty sessionScope.targets and empty sessionScope.languages}">
            <h3>No Filters Found!</h3>
        </c:when>

        <c:otherwise>

            <button class="uncheck-button" type="button" onclick="uncheckAllFilters()">Clear</button>

            <c:if test="${not empty sessionScope.types}">
                <h3>Filter by Type</h3>
                <c:set var="typeList" value="${fn:join(paramValues.types, ', ')}"/>
                <c:forEach var="type" items="${sessionScope.types}">
                    <div class="filter-option">
                        <input type="checkbox" name="types" value="${type}" id="${type}" form="searchForm"
                            <c:if test="${fn:contains(typeList, type)}">checked</c:if> >
                        <label for="${type}">${type}</label>
                    </div>
                </c:forEach>
            </c:if>

            <c:if test="${not empty sessionScope.targets}">
                <h3>Filter by Target Audience</h3>
                <c:set var="targetList" value="${fn:join(paramValues.targets, ', ')}"/>
                <c:forEach var="target" items="${sessionScope.targets}">
                    <div class="filter-option">
                        <input type="checkbox" name="targets" value="${target}" id="${target}" form="searchForm"
                                <c:if test="${fn:contains(targetList, target)}">checked</c:if> >
                        <label for="${target}">${target}</label>
                    </div>
                </c:forEach>
            </c:if>

            <c:if test="${not empty sessionScope.regions}">
                <h3>Filter by Region</h3>
                <c:set var="regionList" value="${fn:join(paramValues.regions, ', ')}"/>
                <c:forEach var="region" items="${sessionScope.regions}">
                    <div class="filter-option">
                        <input type="checkbox" name="regions" value="${region}" id="${region}" form="searchForm"
                                <c:if test="${fn:contains(regionList, region)}">checked</c:if> >
                        <label for="${region}">${region}</label>
                    </div>
                </c:forEach>
            </c:if>

            <c:if test="${not empty sessionScope.domains}">
                <h3>Filter by Domain</h3>
                <c:set var="domainList" value="${fn:join(paramValues.domains, ', ')}"/>
                <c:forEach var="domain" items="${sessionScope.domains}">
                    <div class="filter-option">
                        <input type="checkbox" name="domains" value="${domain}" id="${domain}" form="searchForm"
                                <c:if test="${fn:contains(domainList, domain)}">checked</c:if> >
                        <label for="${domain}">${domain}</label>
                    </div>
                </c:forEach>
            </c:if>

            <c:if test="${not empty sessionScope.languages}">
                <h3>Filter by Language</h3>
                <c:set var="langList" value="${fn:join(paramValues.languages, ', ')}"/>
                <c:forEach var="lang" items="${sessionScope.languages}">
                    <div class="filter-option">
                        <input type="checkbox" name="languages" value="${lang}" id="${lang}" form="searchForm"
                                <c:if test="${fn:contains(langList, lang)}">checked</c:if> >
                        <label for="${lang}">${lang}</label>
                    </div>
                </c:forEach>
            </c:if>
        </c:otherwise>
    </c:choose>
</div>
