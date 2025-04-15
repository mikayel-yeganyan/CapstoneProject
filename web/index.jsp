<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Search Page</title>
  <link rel="stylesheet" href="././css/searchResults.css">
</head>
<body>

<!-- Include Navigation Bar -->
<%@ include file="WEB-INF/imports/navbar.html" %>

<!-- Search Bar -->
<div class="search-container">
  <input type="text" class="search-box" placeholder="Search...">
  <button class="search-button">Search</button>
</div>

<!-- Main Content -->
<div class="main-container">
  <!-- Sidebar (Filters) -->
  <!-- Include the sidebar -->
  <%@ include file="WEB-INF/imports/sidebar.jsp" %>

  <!-- Search Results -->
  <div class="results-container">
    <h2>9,594,816 results</h2>
    <ul>
      <li>
        <div class="result-item">
          <div class="result-title">
            <p>India as a Leading Power</p>
            <div class="result-meta"><p>Ashley J. Tellis - Carnegie Endowment for International Peace</p></div>
          </div>
          <div class="result-actions">
            <button>Link</button>
          </div>
        </div>
      </li>
      <li>
        <div class="result-item">
          <div class="result-title">
            <p>Ideological Security as National Security</p>
            <div class="result-meta"><p>Jude Blanchette - Center for Strategic and International Studies</p></div>
          </div>
          <div class="result-actions">
            <button>Link</button>
          </div>
        </div>
      </li>
    </ul>

  </div>
</div>
</body>
</html>
