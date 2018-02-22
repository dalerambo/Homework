<!DOCTYPE html>
<html>
<head lang="en">
<title>Spring Boot Demo - FreeMarker</title>
<link href="/css/test.css" rel="stylesheet">
<script type="text/javascript" src="/js/lib/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/js/test.js"></script>
</head>
<body>
    <h2>首页<h2>
    
    <font> 
        <#list userList as item> 
            ${item!}<br />
        </#list>
    </font>
    
    <button class="a"> click me</button>
</body>
</html>