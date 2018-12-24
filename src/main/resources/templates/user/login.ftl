<!--
  User: fanglei
  Date: 2018-10-12
  Time: 13:45
-->
<!DOCTYPE html>
<html lang="en"  >
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
    <link rel="shortcut icon" href="${request.contextPath}${request.contextPath}/static/images/index_PC_01.png">
    <title>金茂图文直播</title>
    <link rel="stylesheet" href=" ${request.contextPath}/static/js/layui/css/layui.css"/>
    <link rel="stylesheet" type="text/css"
          href="${request.contextPath}${request.contextPath}/static/js/alert/dialog.css"/>
    <style>
        .login {
            width: 400px;
            height: auto;
            background: #fff;
            padding: 40px 50px;
            border-radius: 5px;
            position: absolute;
            top: 18%;
            left: 50%;
            margin: 0px 0px 0px -250px;
        }

        .login-logo {
            width: 400px;
            height: 40px;
            margin: 0px auto 20px auto;
            font-size: 30px;
            line-height: 40px;
            text-align: center;
            color: #1e9fff;
        }

        .login-box input {
            height: 40px;
            margin: 10px 0;
        }
    </style>
    <script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.12.4.min.js"></script>
    <script src="${request.contextPath}/static/js/layui/layui.js"></script>
    <script >
        //刷新验证码
        function changeImg(createTypeFlag) {
            document.getElementById("imgCode").src = "${request.contextPath}/yzm/create?createTypeFlag=" + createTypeFlag + "&" + Math.random();
        }
        $(function () {
            $("#btnLogin").click(
                function ()
                {
                if($("#txtloginname").val()==""){
                    alert("请输入用户名");
                    return ;
                }
                if($("#txtloginpwd").val()==""){
                    alert("请输入密码");
                    return ;
                }

                    if($("#txtcode").val()==""){
                        alert("请输入验证码");
                        return ;
                    }
                    $.post('${request.contextPath}/user/startlogin', {
                    "loginname": $("#txtloginname").val(), "loginpwd": $("#txtloginpwd").val(), "code": $("#txtcode").val()
                }, function (data, status){
                    if (status == "success") {
                        if (data == "2") {
                            alert("验证码错误");
                            return;
                        }
                        else {
                            if (data == "1") {
                                location.href = "${request.contextPath}/user/detail";
                            } else {
                                if (data == "0") {
                                    alert("登录失败，帐号或密码错误");
                                } else {
                                    alert("登录失败，登录出现异常");
                                }
                            }
                        }
                    } else {
                        alert("网络异常，登录失败");
                    }
                }
                );
            });
        });
        document.onkeydown = function (event_e) {
            if (window.event)
                event_e = window.event;
            var int_keycode = event_e.charCode || event_e.keyCode;
            if (int_keycode == 13) {
                $('#btnLogin').click();
            }
        };
        function reg() {
            var popUp = document.getElementById("popupcontent");
            popUp.style.visibility = "visible";
        }
        function closeReg() {
            document.getElementById('popupcontent').style.visibility='hidden';
        }

        layui.use([ 'layer','element'], function () {
            var  element = layui.element;
            layer = layui.layer;
            try{
                var _href = window.location.href+"";
                if(_href && _href.indexOf('?kickout')!=-1){
                    layer.alert('您已经被踢出，请重新登录！');
                }
                else{
                    if(_href && _href.indexOf('?nosession')!=-1){
                        layer.alert('登录失效，请重新登录！');
                    }
                }
            }catch(e){
            }
        });
    </script>
</head>

<body style="background:url(/static/images/400095057.jpg) no-repeat; background-size:cover;">
<div data-layout-fragment="content">
<div id="popupcontent" class="c_alert_dialog" >
    <div class="c_alert_mask" onclick="closeReg();"></div>
    <div class="c_alert_width">
        <div class="c_alert_con"><img src="${request.contextPath}/static/images/reg.png" width="100%">
        </div>
        <div class="c_alert_btn"><a href="javascript:;" data-name="关闭" onclick="closeReg();">关闭</a></div>
    </div>
</div>
<div class="login">
    <div class="login-logo"><img src="${request.contextPath}/static/images/index_PC_01.png"
                                 style="vertical-align:middle;border: none;margin: 0;"/>&nbsp;&nbsp;金茂图文直播会员登录
    </div>
    <div class="login-box">
        <input id="txtloginname" type="text" name="title" lay-verify="title" autocomplete="off" placeholder="请输入手机号"
               class="layui-input"
               style="float:left; background:url(/static/images/icon1.png) 10px 10px no-repeat #fff; text-indent:30px;">
        <input id="txtloginpwd" type="password" name="title" lay-verify="title" autocomplete="off" placeholder="请输入密码"
               class="layui-input"
               style="float:left; background:url(/static/images/icon2.png) 10px 10px no-repeat #fff; text-indent:30px;">
        <input id="txtcode" type="text" name="title" lay-verify="title" autocomplete="off" placeholder="请输入验证码"
               class="layui-input"
               style="width:238px; float:left; margin-right:20px; background:url(/static/images/icon3.png) 10px 10px no-repeat #fff; text-indent:30px;">
        <img id="imgCode" style="vertical-align: text-top;width:100px;height:50px;line-height:50px;cursor:pointer;border: none;"
             src="${request.contextPath}/yzm/create?createTypeFlag=n" onclick="changeImg('n')" title="点击刷新验证码"></img>
        <button id="btnLogin" class="layui-btn  layui-btn-normal"
                style="width:100%; height:40px;  margin-top:10px; font-size:16px; ">登 录
        </button>
        <a style="float: right;cursor: pointer;text-decoration: underline;" href="javascript:;"  onclick="reg();">会员注册</a>
    </div>
</div>
</div>
</body>
</html>

