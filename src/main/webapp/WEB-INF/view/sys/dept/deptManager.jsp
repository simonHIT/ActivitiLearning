<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>部门管理</title>
</head>
<frameset cols="200,*" border="2" frameborder="yes">
    <frame src="${simonBasePath }/dept/toDeptLeft.action" name="left">
    <frame src="${simonBasePath }/dept/toDeptRight.action" name="right">
</frameset>
</html>