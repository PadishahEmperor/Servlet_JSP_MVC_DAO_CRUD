<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Student</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
    }
    #wrapper {
        width: 40%;
        margin: 50px auto;
        background: white;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        text-align: center;
    }
    h2 {
        color: #0073e6;
    }
    form {
        display: flex;
        flex-direction: column;
        gap: 15px;
        align-items: center;
    }
    input[type="text"] {
        width: 80%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        font-size: 16px;
    }
    input[type="submit"] {
        background-color: #28a745;
        color: white;
        padding: 10px 15px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 16px;
        transition: 0.3s;
    }
    input[type="submit"]:hover {
        background-color: #218838;
    }
    .back-link {
        display: inline-block;
        margin-top: 10px;
        color: #0073e6;
        text-decoration: none;
        font-size: 16px;
        transition: 0.3s;
    }
    .back-link:hover {
        text-decoration: underline;
    }
</style>
</head>
<body>

<div id="wrapper">
    <h2>Add New Student</h2>
    <form action="StudentControllerServlet" method="GET">
    	<input type="hidden" name="command" value="ADD">
        <input type="text" name="firstname" placeholder="First Name" required>
        <input type="text" name="lastname" placeholder="Last Name" required>
        <input type="text" name="email" placeholder="Email" required>
        <input type="submit" value="Save">
    </form>
    <a href="StudentControllerServlet" class="back-link"> Back to List</a>
</div>

</body>
</html>
