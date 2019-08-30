<%--
  Created by IntelliJ IDEA.
  User: wangjianyuan
  Date: 2019/8/30
  Time: 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>查看流程图</title>
</head>
<body style="text-align: center">
<c:if test="${not empty workFlowVo.deploymentId}">
    <img src="${simonBasePath}/workFlow/viewProcessImage.action?deploymentId=${workFlowVo.deploymentId}" alt="流程图">
</c:if>
<c:if test="${not empty workFlowVo.taskId}">
    <img src="${simonBasePath}/workFlow/viewProcessImageByTaskId.action?taskId=${workFlowVo.taskId}" alt="流程图">
</c:if>

</body>
</html>
