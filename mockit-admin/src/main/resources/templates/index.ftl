<!DOCTYPE html>
<html>
<head>
    <title>Session List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        h1 {
            color: #333;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        .search-form {
            margin-bottom: 20px;
        }

        .search-input {
            width: 200px;
            padding: 6px;
        }

        .actions {
            display: flex;
            justify-content: flex-end;
            margin-top: 20px;
        }

        .action-button {
            margin-left: 10px;
            padding: 6px 12px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }

        .action-button.cancel {
            background-color: #f44336;
        }
    </style>
</head>
<body>
<h1>Session List</h1>

<form class="search-form">
    <label for="search-input">Search:</label>
    <input type="text" id="search-input" class="search-input" placeholder="Enter project name">
</form>

<table id="session-table">
    <thead>
    <tr>
        <th>Project Name</th>
        <th>IP</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <#list sessionMap?keys as projectName>
        <#list sessionMap[projectName] as session>
            <tr>
                <td>${projectName}</td>
                <td>${session.ip}</td>
                <td>
                    <button class="action-button">Mock</button>
                    <button class="action-button cancel">Cancel Mock</button>
                </td>
            </tr>
        </#list>
    </#list>
    </tbody>
</table>

<div class="actions">
    <button class="action-button">Mock All</button>
    <button class="action-button cancel">Cancel All</button>
</div>

<script>
    // Add JavaScript code here, if needed
</script>
</body>
</html>
