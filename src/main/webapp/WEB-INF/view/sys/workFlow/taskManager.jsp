<%--
  Created by IntelliJ IDEA.
  User: wangjianyuan
  Date: 2019/8/30
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>待办管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="${simonBasePath }/resources/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${simonBasePath }/resources/css/public.css" media="all"/>
</head>
<body class="childrenBody">
<div class="layui-form-item" style="text-align: right;">
    <a class="layui-btn search_btn">刷新</a>
    <%--    <button type="reset" class="layui-btn layui-btn-warm">清空</button>--%>
</div>
<table id="workFlowList" lay-filter="workFlowList"></table>
<!--操作-->
<script type="text/html" id="workFlowListBar">
    <a class="layui-btn layui-btn-xs" lay-event="completeTask">办理任务</a>

    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="viewProcessImage">查看流程图</a>
</script>
<script type="text/javascript" src="${simonBasePath }/resources/layui/layui.js"></script>
</body>
<script type="text/javascript">
    var tableIns;
    layui.use(['form', 'layer', 'table', 'jquery'], function () {
        var form = layui.form,
            layer = parent.layer === undefined ? layui.layer : top.layer,
            $ = layui.jquery,
            table = layui.table;
        //待办列表
        tableIns = table.render({
            elem: '#workFlowList',
            url: '${simonBasePath }/workFlow/loadCurrentUserTask.action',
            cellMinWidth: 95,
            page: true,
            height: "full-220",
            limits: [10, 15, 20, 25],
            defaultToolbar: ['filter'],
            limit: 10,
            id: "workFlowListTable",
            cols: [[
                {type: "checkbox", fixed: "left", width: 50},
                {field: 'id', title: '待办任务ID', minWidth: 100, align: "center"},
                {field: 'name', title: '待办任务名称', minWidth: 100, align: "center"},
                {field: 'createTime', title: '创建时间', minWidth: 100, align: "center"},
                {field: 'assignee', title: '代理人', align: 'center'},
                {title: '操作', minWidth: 175, templet: '#workFlowListBar', fixed: "right", align: "center"}
            ]]
        });

        //查询
        $(".search_btn").on("click", function () {
            //刷新table
            tableIns.reload();
        });

        //修改待办任务
        function openDoTask(data) {
            var index = layui.layer.open({
                title: "修改待办任务",
                type: 2,
                area: ["800px", "500px"],
                content: "${simonBasePath }/workFlow/toDoTask.action?taskId=" + data.id,
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回待办任务列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                }
            })
            //layui.layer.full(index);
            //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
            $(window).on("resize", function () {
                layui.layer.full(index);
            })
        }

        //列表操作
        table.on('tool(workFlowList)', function (obj) {
            var layEvent = obj.event,
                data = obj.data;
            if (layEvent === 'viewProcessImage') { //编辑
                toviewProcessImage(data);//data主当前点击的行
            } else if (layEvent === 'completeTask') { //删除
                openDoTask(data);

            } else if (layEvent === "view") {
                show(data.id);
            }
        });

        function show(id) {
            var index = layui.layer.open({
                title: "查看待办任务",
                type: 2,
                area: ["800px", "500px"],
                content: "${simonBasePath }/workFlow/showworkFlow.action?id=" + id,
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回待办任务列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                }
            })
            //layui.layer.full(index);
            //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
            $(window).on("resize", function () {
                layui.layer.full(index);
            })
        }
        function toviewProcessImage(data) {
            var index = layui.layer.open({
                title: "查看流程【"+data.name+"】图片",
                type: 2,//ifream层
                area: ["800px", "500px"],
                content: "${simonBasePath }/workFlow/toViewProcessImage.action?taskId="+data.id,
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回流程列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                }
            })
            //layui.layer.full(index);
            //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
            $(window).on("resize", function () {
                layui.layer.full(index);
            })
        }
    })

</script>
</html>