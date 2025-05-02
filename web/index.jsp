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
        <div class="result-card">
          <!-- Title (full width) -->
          <p class="result-title">Water</p>

          <!-- Description + Button on the same row -->
          <div class="description-row">
            <p class="result-description">
              Water is an inorganic compound with the chemical formula H₂O. It is a transparent,
              tasteless, odorless, and nearly colorless chemical substance.
            </p>
            <div class="result-actions">
              <a href="#">
                <button type="button">Link</button>
              </a>
            </div>
          </div>

          <!-- Tags -->
          <div class="keyword-tags">
            <span class="tag">Drinking water</span>
            <span class="tag">Properties</span>
            <span class="tag">Fresh water</span>
            <span class="tag">Water supply</span>
          </div>
        </div>
      </li>
    </ul>

  </div>
</div>
</body>
</html>
