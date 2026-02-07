<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Transport System - Main Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .container {
            background-color: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 500px;
        }
        h1 {
            color: #333;
            margin-bottom: 30px;
        }
        .links {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }
        a {
            display: block;
            padding: 15px 30px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-size: 18px;
            transition: background-color 0.3s;
        }
        a:hover {
            background-color: #45a049;
        }
        .emoji {
            font-size: 48px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="emoji">🚌</div>
    <h1>Transport Management System</h1>
    <div class="links">
        <a href="transport-service">🚍 Открыть систему управления транспортом</a>
        <a href="/hello-servlet">  Hello Servlet </a>

    </div>
</div>
</body>
</html>