<#macro layout>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="金茂图文直播">
    <meta name="author" content="fl">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="金茂图文直播,江苏金茂图文,江苏金茂图文直播,江苏金茂图文直播系统">
    <link rel="shortcut icon" href="/static/images/index_PC_01.png">
    <title>金茂图文直播</title>
    <link rel="stylesheet" href="${request.contextPath}/static/js/layui/css/layui.css"/>
    <script src="${request.contextPath}/static/js/jquery-1.12.4.js"></script>
    <script src="${request.contextPath}/static/js/layui/layui.js"></script>
    <style>
        .layui-body {
            background-color: #eee;
        }

        .layui-card {
            margin: 15px 15px;
        }

        .layui-card-header {
            border-left: 5px solid #108cee;
        }

        .layui-card-header h2 {
            border-bottom: none;
            color: #333;
            font-size: 16px;
            background-color: transparent;

        }

        .layui-card-body {
            color: #666;
        }

        input, textarea, select, button {
            color: #666;
        }

        .layui-input {
            height: 30px;
        }

        .layui-side-scroll {
            background-color: #23262E;
        }

        .layui-layout-admin .layui-header {
            background-color: #fff;
        }

        /*菜单颜色设置*/
        .layui-nav-tree .layui-nav-bar {
            background-color: #1E9FFF;
        }

        .layui-nav-tree .layui-nav-child dd.layui-this, .layui-nav-tree .layui-nav-child dd.layui-this a, .layui-nav-tree .layui-this, .layui-nav-tree .layui-this > a, .layui-nav-tree .layui-this > a:hover {
            background-color: #1E9FFF;
        }

        .layui-laypage .layui-laypage-curr .layui-laypage-em {
            background-color: #1E9FFF;
        }

        .layui-layout-admin .layui-side {
            top: 0px;
        }

        .layui-layout-admin .layui-footer {
            text-align: center;
            background-color: #F0F2F5;
            color: #848587;
        }
    </style>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header" style="margin-left: 200px;">
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;" style="color: #393D49;">
                    欢迎您，
                    <#if Session.user??>
                        ${Session.user.displayname!}
                    </#if>
                </a>
            </li>
            <li class="layui-nav-item"><a href="/user/exit" style="color: #393D49;">退出</a></li>
        </ul>
    </div>
    <div class="layui-side">
        <div class="layui-side-scroll">
            <div style="background-color:#393D49;color: #fff;font-weight: 300;font-size: 16px;line-height: 60px;width:200px;text-align: center;">
                <img src="${request.contextPath}/static/images/index_PC_01.png" style="width:30px;height: 30px;">&nbsp;&nbsp;&nbsp;金茂图文直播
            </div>
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul id="ulcd" class="layui-nav layui-nav-tree">
            <#list Session.menu as item>
              <#if item.cata=='1'>
                  <#if item.permission!=''>
                      <@shiro.hasPermission name="${item.permission}">
                          <li class="layui-nav-item  layui-nav-itemed">
                              <a href="javascript:;">
                                  <i class="layui-icon ${item.iconname}"></i>&nbsp;&nbsp;${item.menuname}
                              </a>
                              <dl class="layui-nav-child">
                                   <#list Session.menu as itemc>
                                          <#if itemc.parentcode==item.menucode>
                                              <#if itemc.permission!=''>
                                                <@shiro.hasPermission name="${itemc.permission}">
                                                    <dd>
                                                        <a style="padding-left:40px;"
                                                           href="${request.contextPath}${itemc.url}">${itemc.menuname}</a>
                                                    </dd>
                                                </@shiro.hasPermission>
                                              <#else>
                                                <dd>
                                                    <a style="padding-left:40px;"
                                                       href="${request.contextPath}${itemc.url}">${itemc.menuname}</a>
                                                </dd>
                                              </#if>
                                          </#if>
                                   </#list>
                              </dl>
                          </li>
                      </@shiro.hasPermission>
                  <#else >
                      <li class="layui-nav-item  layui-nav-itemed">
                          <a href="javascript:;">
                              <i class="layui-icon ${item.iconname}"></i>&nbsp;&nbsp;${item.menuname}
                          </a>
                          <dl class="layui-nav-child">
                                   <#list Session.menu as itemc>
                                          <#if itemc.parentcode==item.menucode>
                                              <#if itemc.permission!=''>
                                                <@shiro.hasPermission name="${itemc.permission}">
                                                    <dd>
                                                        <a style="padding-left:40px;"
                                                           href="${request.contextPath}${itemc.url}">${itemc.menuname}</a>
                                                    </dd>
                                                </@shiro.hasPermission>
                                              <#else>
                                                <dd>
                                                    <a style="padding-left:40px;"
                                                       href="${request.contextPath}${itemc.url}">${itemc.menuname}</a>
                                                </dd>
                                              </#if>
                                          </#if>
                                   </#list>
                          </dl>
                      </li>
                  </#if>
              </#if>
            </#list>
            </ul>
        </div>
    </div>
    <#nested>
    <div>
        <div class="layui-footer">
            <!-- 底部固定区域 -->
            金茂图文直播管理系统
        </div>
    </div>
</div>
<script>
    $(function () {
        var href = window.location.href;
        if (href.indexOf("?") != -1 && href.indexOf("applive/list?cata") == -1) {
            href = href.substring(0, href.indexOf("?"));
        }
        href = GetUrlRelativePath();
        $("[href='" + href + "']").addClass("layui-this");
        $("[href='" + href + "']").parent().parent().parent().addClass("layui-nav-itemed").siblings().removeClass("layui-nav-itemed");
    });

    function GetUrlRelativePath() {
        var url = window.location.href
        var arrUrl = url.split("//");
        var start = arrUrl[1].indexOf("/");
        var relUrl = arrUrl[1].substring(start);//stop省略，截取从start开始到结尾的所有字符

        if (relUrl.indexOf("?") != -1) {
            relUrl = relUrl.split("?")[0];
        }
        return relUrl;
    }
</script>
</body>
</html>
</#macro>