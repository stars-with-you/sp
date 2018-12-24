<#-- 引入布局指令的命名空间 -->
<#import "../master/admin.ftl" as defaultLayout>
<#-- 调用布局指令 -->
<@defaultLayout.layout>
<div>
    <div class="layui-body">
        <div class="layui-card" style="margin-bottom: 0;">
            <div class="layui-card-header">
                <h2>基本信息</h2>
            </div>
            <div class="layui-card-body">
                <div class="layui-form" lay-filter="">
                    <div class="layui-form-item">
                        <label class="layui-form-label">头像</label>
                        <img src="${request.contextPath}${Session.user.logo!}"
                             style="width: 79px;height: 79px;margin-left: 15px;">
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">手机号</label>
                        <label class="layui-form-label" style="width: auto;" >
                            ${Session.user.loginname!}
                        </label>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">昵称</label>
                        <label class="layui-form-label" style="width: auto;" >
                            ${Session.user.displayname!}
                        </label>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">公司名称</label>
                        <label class="layui-form-label" style="width: auto;" >${Session.user.company!}</label>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">邮箱</label>
                        <label class="layui-form-label" style="width: auto;" >${Session.user.email!}</label>
                    </div>
                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label">身份证</label>
                        <label class="layui-form-label" style="width: auto;" >${Session.user.sfz!}</label>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <button class="layui-btn layui-btn-normal  layui-btn-sm" onclick="edit();">编辑资料</button>
                            <button class="layui-btn  layui-btn-normal  layui-btn-sm" onclick="editpwd();">修改密码</button>
                        </div>
                    </div>
                    <hr/>
                </div>
            </div>
        </div>
        <div class="layui-card" style="margin-top: 0;">
            <div class="layui-card-header">
                <h2>其他信息</h2>
            </div>
            <div class="layui-card-body">
                <div class="layui-form" lay-filter="">
                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label">审核状态</label>
                        <div class="layui-input-block" style="padding-left: 10px;">
                                <input type="radio" name="auth" value="1" title="通过" <#if Session.user.auth=='1'>checked</#if> disabled />
                                <input type="radio" name="auth" value="0" title="未通过"  <#if Session.user.auth=='0'>checked</#if>  disabled/>
                        </div>
                    </div>
                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label">用户类型</label>
                        <div class="layui-input-block" style="padding-left: 10px;">
                                <input type="radio" name="cata" value="0" title="管理员"  <#if Session.user.cata=='0'>checked</#if>  disabled />
                                <input type="radio" name="cata" value="1" title="普通用户"  <#if Session.user.cata=='1'>checked</#if>  disabled/>
                        </div>
                    </div>
                    <div class="layui-form-item layui-form-text">
                        <input type="hidden" id="hidpguid" value="${Session.user.pguid!}"/>
                        <label class="layui-form-label">微信openid</label>
                        <label class="layui-form-label" style="width: auto;">${Session.user.openid!}</label>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <table id="tablemm" class="layui-table layui-form" style="min-height:150px;display: none;">
        <tr>
            <td>新密码:</td>
            <td>

                <input id="master_txtNewpwd" class="layui-input" type="password"></input>
            </td>
        </tr>
        <tr>
            <td>确认密码:</td>
            <td><input id="master_txtSamepwd" class="layui-input" type="password"></input>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center;">
                <a href="javascript:void(0)" class="layui-btn layui-btn-normal" onclick="sbpwd()">保存密码</a>
                <button class="layui-btn  layui-btn-normal" onclick="closeW();">取消</button>
            </td>
        </tr>
    </table>
    <form id="myform" class="layui-form" lay-filter="form" enctype="multipart/form-data">
        <table id="tableax" class="layui-table " style="display: none;">
            <tr>
                <td>手机号：</td>
                <td>
                    <input type="hidden" id="master_hidpguid" name="pguid"/>
                    <input id="manager_txtLoginname" name="loginname" class="layui-input" type="text"
                           lay-verify="required|username|phone"
                           lay-verType="tips"></input></td>
            </tr>

            <tr>
                <td>昵称：</td>
                <td><input id="manager_txtdisplayname" name="displayname" class="layui-input" type="text"
                           lay-verify="required"
                           lay-verType="tips"></input></td>
            </tr>
            <tr>
                <td>身份证号：</td>
                <td><input id="manager_txtsfz" name="sfz" class="layui-input" type="text" lay-verify="identity"
                           lay-verType="tips"></input>
                </td>
            </tr>
            <tr>
                <td>邮箱：</td>
                <td><input id="manager_txtemail" name="email" class="layui-input" type="text" lay-verify="email"
                           lay-verType="tips"></input>
                </td>
            </tr>
            <tr>
                <td>公司名称：</td>
                <td><input id="manager_txtcompany" name="company" class="layui-input" type="text"
                           lay-verType="tips"></input>
                </td>
            </tr>
            <tr>
                <td>头像:</td>
                <td style="padding: 0;" colspan="3">
                    <input id="picfile" type="file" name="picfile">
                    <img id="imgpicfile" style="width:100px;height:100px; display: none;">
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: center;">
                    <button id="managerlist_btnupdate" class="layui-btn  layui-btn-normal" lay-submit
                            lay-filter="managerup">修改
                    </button>
                    <button type="button" class="layui-btn  layui-btn-normal" onclick="closeW();">取消</button>
                </td>
            </tr>
        </table>
    </form>
    <script src="${request.contextPath}/static/js/jquery.form.js"></script>
    <script>
        var layer = null, myindex = 0, form = null;
        layui.use(['layer', 'laydate', 'element', 'form'], function () {
            var table = layui.table, element = layui.element;
            layer = layui.layer;
            form = layui.form;
            form.on('submit(managerup)', function (data) {
                $.ajax({
                    async: false, // 使用同步的方法
                    type: "post",
                    url: '${request.contextPath}/user/validate2',
                    data: { //要提交到服务端验证的用户名
                        "loginname": $("#manager_txtLoginname").val(),
                        "pguid": $("#master_hidpguid").val()
                    },
                    dataType: 'text',
                    success: function (result) {
                        if (result == "false") {
                            layer.tips('用户名已存在，请重新录入', '#manager_txtLoginname', {
                                tips: 1
                            });
                            return;
                        }
                        managerup();
                    }
                });
                return false;
            });
            form.verify({
                username: function (value, item) { //value：表单的值、item：表单的DOM对象
                    if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                        return '用户名不能有特殊字符';
                    }
                }
                ,
                identity: function (value, item) {
                    if ($("#manager_txtsfz").val() != "" && !new RegExp("(^\\d{15}$)|(^\\d{17}(x|X|\\d)$)").test($("#manager_txtsfz").val())) {
                        return "请输入正确的身份证号";
                    }
                },
                email: function (value, item) {
                    if ($("#manager_txtemail").val() != "" && !new RegExp("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$").test($("#manager_txtemail").val())) {
                        return "邮箱格式不对";
                    }
                }
            });
        });

        function editpwd() {
            $("#master_txtNewpwd").val("");
            $("#master_txtSamepwd").val("");
            myindex = layer.open({
                type: 1,
                title: '密码修改',
                zIndex: 10000,
                area: ['400px', '250px'],
                content: $("#tablemm") //这里content是一个普通的String
            });
        }

        //密码修改
        function sbpwd() {
            if (!new RegExp("^[\\S]{6,12}$").test($("#master_txtNewpwd").val())) {
                layer.tips('密码必须6到12位，且不能出现空格', '#master_txtNewpwd', {
                    tips: 1
                });
                return;

            }
            if ($("#master_txtNewpwd").val() != $("#master_txtSamepwd").val()) {
                layer.tips('两次密码不一样', '#master_txtSamepwd', {
                    tips: 1
                });
                return;
            }
            var pguid = $("#hidpguid").val();
            $.post("${request.contextPath}/user/upwd", {
                "pguid": pguid,
                "loginpwd": $("#master_txtNewpwd").val()
            }, function (data, status) {
                if (status == "success") {
                    if (data == "1") {
                        layer.msg('密码修改成功', {icon: 1, time: 2000});
                        layer.close(myindex);
                    } else {
                        layer.msg('密码修改失败', {icon: 2, time: 2000});
                    }
                } else {
                    layer.msg('密码修改失败', {icon: 2, time: 2000});
                }
            });
        }

        function edit() {
            {
                $.post("${request.contextPath}/user/getsingle", {"pguid": $("#hidpguid").val()}, function (data, status) {
                    if (status == "success") {
                        if (data != "") {
                            var json = $.parseJSON(data);
                            $("#master_hidpguid").val(json.pguid);
                            $("#manager_txtLoginname").val(json.loginname);//用户名
                            $("#manager_txtdisplayname").val(json.displayname);//昵称
                            $("#manager_txtsfz").val(json.sfz);//
                            $("#manager_txtemail").val(json.email);//邮箱
                            $("#manager_txtcompany").val(json.company);//
                            $("#manager_slcata").val(json.cata);//
                            if (typeof(json.logo) != "undefined" && json.logo != null && json.logo != "") {
                                $("#imgpicfile").show();
                                $("#imgpicfile").prop("src", '${request.contextPath}' + json.logo);
                            }
                            $("#managerlist_btnupdate").show();
                        } else {
                            layer.msg('数据库中没有这条信息', {icon: 2, time: 2000});
                        }
                        myindex = layer.open({
                            type: 1,
                            title: '信息修改',
                            zIndex: 10000,
                            area: ['600px', '500px'],
                            content: $("#tableax")
                        });
                    }
                });
            }
        }

        //执行修改
        function managerup() {
            $("#myform").ajaxForm(function () {
            });
            $("#myform").ajaxSubmit({
                type: "POST",
                url: "${request.contextPath}/user/update",
                dataType: "text",
                error: function (jqXHR, textStatus, errorThrown) {
                    layer.msg('网络问题导致添加信息失败', {icon: 2, time: 2000});
                },
                success: function (data) {
                    if (data == "1") {
                        layer.msg('修改成功', {icon: 1, time: 2000});
                        location.reload();
                    } else {
                        if (data == "2") {
                            layer.msg('修改失败', {icon: 2, time: 2000});
                        } else {
                            layer.msg(data, {icon: 2, time: 20000});
                        }
                    }
                }
            });
            return false;
        }

        function closeW() {
            layer.close(myindex);
        }
    </script>
</div>
</@defaultLayout.layout>