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
<table id="commentList" lay-filter="commentList"></table>
<script type="text/javascript" src="${simonBasePath }/resources/layui/layui.js"></script>
</body>
<script type="text/javascript">
    layui.use(['form', 'jquery', 'layer','table'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var table=layui.table;
        //如果父页面有layer就使用父页的  没有就自己导入
        var layer = parent.layer === undefined ? layui.layer : top.layer;
        var tableIns = table.render({
            elem: '#commentList',
            url: '${simonBasePath }/workFlow/loadAllCommentByTaskId.action?taskId='+${workFlowVo.taskId},
            cellMinWidth: 95,
            height: "full-220",
            id: "commentListTable",
            cols: [[
                {field: 'time', title: '批注时间', minWidth: 100, align: "center"},
                {field: 'userId', title: '批注人', minWidth: 100, align: "center"},
                {field: 'message', title: '批注内容', minWidth: 100, align: "center"}
            ]]
        });

        //监控按钮事件
        $(".dotask").click(function (obj) {
            var outcome=this.value;
            $.post("${simonBasePath}/workFlow/doTask.action"
                ,{
                    taskId:${workFlowVo.taskId},
                    outcome:outcome,
                    id:${leaveBill.id},
                    comment:$("#comment").val()
                },function (data) {
                    layer.msg(data.msg);

                    //关闭部署的弹出层
                    var index=parent.layer.getFrameIndex(window.name);
                    window.parent.layer.close(index);

                    //刷新父页面的table
                    window.parent.tableIns.reload();
                }
            )

        })
    });
</script>
</html>