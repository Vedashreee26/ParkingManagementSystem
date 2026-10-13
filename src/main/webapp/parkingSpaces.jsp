<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="project.ParkingSpaceBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Parking Space Management</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f7f6;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
            background: #fff;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }
        h2 { text-align: center; color: #333; margin-top: 0; }
        
        /* Stats Header */
        .stats-bar {
            display: flex;
            justify-content: space-around;
            margin: 20px 0;
            gap: 15px;
        }
        .stat-card {
            flex: 1;
            padding: 15px;
            border-radius: 8px;
            text-align: center;
            color: #fff;
        }
        .stat-total { background-color: #3498db; }
        .stat-available { background-color: #2ecc71; }
        .stat-occupied { background-color: #e74c3c; }
        .stat-card h3 { margin: 0; font-size: 28px; }
        .stat-card p { margin: 5px 0 0; font-size: 14px; text-transform: uppercase; }

        /* Add Slot Form */
        .add-box {
            background: #f8f9fa;
            border: 1px solid #e2e8f0;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 25px;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .add-box input[type="text"] {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
            flex: 1;
        }
        .add-box button {
            background-color: #2c3e50;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            font-weight: bold;
        }
        .add-box button:hover { background-color: #1a252f; }

        /* Slot Visual Grid */
        .grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(130px, 1fr));
            gap: 15px;
            margin-top: 20px;
        }
        .slot-box {
            border-radius: 8px;
            padding: 20px 10px;
            text-align: center;
            color: #fff;
            box-shadow: 0 2px 5px rgba(0,0,0,0.15);
            transition: transform 0.2s;
        }
        .slot-box:hover { transform: scale(1.05); }
        .slot-box.AVAILABLE { background-color: #2ecc71; }
        .slot-box.OCCUPIED { background-color: #e74c3c; }
        .slot-title { font-size: 20px; font-weight: bold; margin-bottom: 5px; }
        .slot-status { font-size: 12px; letter-spacing: 1px; text-transform: uppercase; }

        .nav-links { text-align: center; margin-top: 25px; }
        .nav-links a { text-decoration: none; color: #3498db; font-weight: bold; }
        .alert { padding: 10px; margin-bottom: 15px; border-radius: 5px; text-align: center; }
        .alert-success { background: #d4edda; color: #155724; }
        .alert-error { background: #f8d7da; color: #721c24; }
    </style>
</head>
<body>

<div class="container">
    <h2>🅿️ Parking Space Management</h2>

    <!-- Notifications -->
    <% if (request.getAttribute("msg") != null) { %>
        <div class="alert alert-success"><%= request.getAttribute("msg") %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-error"><%= request.getAttribute("error") %></div>
    <% } %>

    <!-- Summary Statistics -->
    <div class="stats-bar">
        <div class="stat-card stat-total">
            <h3><%= request.getAttribute("totalCapacity") != null ? request.getAttribute("totalCapacity") : 0 %></h3>
            <p>Total Capacity</p>
        </div>
        <div class="stat-card stat-available">
            <h3><%= request.getAttribute("availableSpaces") != null ? request.getAttribute("availableSpaces") : 0 %></h3>
            <p>Available Slots</p>
        </div>
        <div class="stat-card stat-occupied">
            <h3><%= request.getAttribute("occupiedSpaces") != null ? request.getAttribute("occupiedSpaces") : 0 %></h3>
            <p>Occupied Slots</p>
        </div>
    </div>

    <!-- Admin Feature: Add Slot -->
    <form class="add-box" action="ParkingServlet" method="post">
        <input type="hidden" name="action" value="add">
        <input type="text" name="spaceNo" placeholder="Enter Slot Number (e.g., S16, P-02)" required>
        <button type="submit">+ Add New Slot</button>
    </form>

    <!-- Visual Parking Grid -->
    <h3>Slot Layout (Live View)</h3>
    <div class="grid">
        <% 
            ArrayList<ParkingSpaceBean> spaceList = (ArrayList<ParkingSpaceBean>)request.getAttribute("spaceList");
            if (spaceList != null && !spaceList.isEmpty()) {
                for (ParkingSpaceBean space : spaceList) {
        %>
            <div class="slot-box <%= space.getStatus().toUpperCase() %>">
                <div class="slot-title"><%= space.getSpace_no() %></div>
                <div class="slot-status"><%= space.getStatus() %></div>
            </div>
        <% 
                }
            } else { 
        %>
            <p>No parking spaces configured yet. Add some slots above!</p>
        <% } %>
    </div>

    <div class="nav-links">
        <a href="Index.html">← Back to Home</a>
    </div>
</div>

</body>
</html>