<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加请假单</title>
    <link rel="stylesheet" href="${simonBasePath }/resources/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${simonBasePath }/resources/css/public.css" media="all"/>
</head>
<body class="childrenBody">
<form class="layui-form" method="post" id="frm">
    <div class="layui-form-item">
        <label class="layui-form-label">请假标题</label>
        <div class="layui-input-block">
            <input type="text" name="title" value="${leaveBill.title}" disabled="disabled" autocomplete="off"
                   class="layui-input">
            <input type="hidden" value="0" name="state">
            <input type="hidden" value="${user.id }" name="userid">

        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">请假天数</label>
            <div class="layui-input-inline">
                <input type="tel" name="days" disabled="disabled" value="${leaveBill.days}" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">请假时间</label>
            <div class="layui-input-inline">
                <input type="text" name="leavetime" id="leavetime"
                       value="<fmt:formatDate value="${leaveBill.leavetime}" pattern="yyyy-MM-dd"></fmt:formatDate>"
                       readonly="readonly" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">请假原因</label>
        <div class="layui-input-block">
            <textarea disabled="disabled" name="content" id="content" class="layui-textarea">${leaveBill.content}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">批注</label>
        <div class="layui-input-block">
            <textarea placeholder="请输入批注内容"  name="comment" id="comment" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-form-item" style="text-align: center;">
        <c:forEach items="${outcomes}" var="sn">
            <input type="button" value="${sn}" class="layui-btn dotask" >
        </c:forEach>
        <%--<a href="javascript:void(0)" class="layui-btn" lay-submit="" lay-filter="addLeaveBill">提交</a>
        <button type="reset" class="layui-btn layui-btn-warm">重置</button>--%>
    </div>
</form>
<script type="text/javascript" src="${simonBasePath }/resources/layui/layui.js"></script>
</body>
<script type="text/javascript">
    layui.use(['form', 'jquery', 'layer'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        //如果父页面有layer就使用父页的  没有就自己导入
        var layer = parent.layer === undefined ? layui.layer : top.layer;
    });
</script>
</html>