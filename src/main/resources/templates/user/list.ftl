<#import "../master/admin.ftl" as defaultLayout>
<#-- 调用布局指令 -->
<@defaultLayout.layout>
<div>
<style>
    #tableax td:first-child {
        text-align: right;
        width: 150px;
    }

    .layui-input {
        height: 30px;
    }
</style>
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
            <button type="button" class="layui-btn  layui-btn-normal" onclick="closeW();">取消</button>
        </td>
    </tr>
</table>
<form id="myform" class="layui-form" lay-filter="form" enctype="multipart/form-data">
    <table id="tableax" class="layui-table " style="display: none;">
        <tr>
            <td>用户名/手机号：</td>
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
        <tr id="trmm">
            <td>密码：</td>
            <td><input id="manager_txtLoginpwd" class="layui-input" type="password" lay-verify="pass"
                       lay-verType="tips"></input></td>
        </tr>
        <tr id="trmmqr">
            <td>确认密码：</td>
            <td><input id="manager_txtConfirm" class="layui-input" type="password" lay-verify="cpass"
                       lay-verType="tips"></input>
            </td>
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
            <td>审核状态:</td>
            <td>
                <input type="radio" name="auth" value="1" title="通过">
                <input type="radio" name="auth" value="0" title="未通过">
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
                <button id="managerlist_btnadd" class="layui-btn layui-btn-normal" lay-submit lay-filter="manageradd">保存</button>
                <button id="managerlist_btnupdate" class="layui-btn layui-btn-normal" lay-submit lay-filter="managerup">修改</button>
                <button type="button" class="layui-btn  layui-btn-normal" onclick="closeW();">取消</button>
            </td>
        </tr>
    </table>
</form>
<table id="manager_tbrole" style="min-width: 800px;display: none;" class="layui-table">
    <thead>
    <tr>
        <th></th>
        <th width="80">角色代码</th>
        <th width="120">角色名称</th>
        <th>角色拥有的权限</th>
    </tr>
    </thead>
    <tbody id="manager_tby"></tbody>
    <tfoot>
    <tr>
        <td colspan="4" style="text-align: center;">
            <a id="manager_btnadd" href="javascript:void(0)" class="layui-btn layui-btn-normal" onclick="roleadd()">更新角色拥有角色</a>
            <button type="button" class="layui-btn  layui-btn-normal" onclick="closeW();">取消</button>
        </td>
    </tr>
    </tfoot>

</table>
<div class="layui-body">
    <!-- 内容主体区域 -->
    <div class="layui-card">
        <div class="layui-card-header">
            <h2>用户管理</h2>
        </div>
        <div class="layui-card-body">
                    <button class="layui-btn layui-btn-normal layui-btn-sm" id="btnAdd" onclick="addData();">+ 添加信息</button>
                <div  style="display: inline-block;">
                    <label>用户名：</label>
                    <div class="layui-input-inline">
                        <input id="manager_txtLoginnamesea" class="layui-input" type="text">
                    </div>
                </div>
                <div class="layui-inline">
                    <button class="layui-btn layui-btn-normal layui-btn-sm" id="btnSearch" onclick="reloadData();">查询信息</button>
                </div>
            </div>
            <table class="layui-hide" id="test" lay-filter="test"></table>
            <script type="text/html" id="barDemo">
                <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="xgmm">修改密码</a>
                <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
                <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="cho">选择角色</a>
            </script>
            <script type="text/html" id="indexTpl">

            </script>
        </div>
    </div>
    <script src="${request.contextPath}/static/js/jquery.form.js"></script>
<script>
    var tableIns = null, layer = null, myindex = 0, form = null;
    layui.use(['table', 'layer', 'laydate', 'element', 'form'], function () {
        var table = layui.table, laydate = layui.laydate, element = layui.element;
        layer = layui.layer;
        form = layui.form;
        //绑定添加角色按钮事件
        $.post("${request.contextPath}/role/getlist", {"pn": "1", "ps": "100"}, function (data, status) {
            if (status == "success") {
                if (data != "") {
                    var json = [];
                    if (data != "") {
                        //json=;
                        json = $.parseJSON(data).data;
                    }
                    var str = "";
                    for (var i = 0; i < json.length; i++) {
                        str += "<tr><td style='text-align:center;'><input id='" + json[i].rcode + "'  lay-skin='primary' type='checkbox'/></td><td  style='text-align:center;'>" + json[i].rcode + "</td><td  style='text-align:center;'>" + json[i].rname + "</td><td  style='text-align:center;'>" + json[i].yxbz + "</td></tr>";
                    }
                    $("#manager_tby").html(str);

                } else {
                    layer.msg('系统中没有角色相关信息', {icon: 2, time: 2000});
                }
            }
            else {
                layer.msg('网络问题导致角色信息获取失败', {icon: 2, time: 2000});
            }
        });
        //第一个实例
        tableIns = table.render({
            elem: '#test'
            , url: '${request.contextPath}/manager/data',
            request: {
                pageName: 'currentPage' //页码的参数名称，默认：page
                , limitName: 'pagesize' //每页数据量的参数名，默认：limit
            },
            method: 'post',
            page: true //开启分页
            ,
            cols: [
                [
                {
                    field: 'loginname',
                    title: '帐号名称/手机号'
                }, {
                    field: 'displayname',
                    title: '昵称'
                }, {
                    field: 'auth',
                    title: '审核状态',
                        templet: function (d) {
                            if (d.auth == "1") {
                                return "通过";
                            }
                            else {
                                return "<span style='color:#FF0000'>未通过</span>";
                            }
                        }
                    }
                //     , {
                //     field: 'email',
                //     title: '邮箱'
                // }
                ,
                {
                    field: 'company',
                    title: '公司名称'
                }
                , {
                    fixed: 'right',
                    align: 'center',
                    width: 280,
                    toolbar: '#barDemo',
                    title: '操作'
                }]
            ]
        });
        //监听工具条
        table.on('tool(test)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var trdata = obj.data //获得当前行数据
                , layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'xgmm') {
                $("#master_hidpguid").val(trdata.pguid);
                $("#master_txtNewpwd").val("");
                $("#master_txtSamepwd").val("");
                myindex=layer.open({
                    type: 1,
                    title: '密码修改',
                    zIndex: 10000,
                    area: ['400px', '250px'],
                    content: $("#tablemm") //这里content是一个普通的String
                });
            } else if (layEvent === 'del') {
                layer.confirm('真的删除行么', function (index) {
                    //obj.del(); //删除对应行（tr）的DOM结构
                    layer.close(index);
                    //向服务端发送删除指令
                    $.ajax({
                        url: "${request.contextPath}/manager/del",
                        type: "post",
                        data: {"pguid": trdata.pguid},
                        dataType: "text",
                        success: function (data) {
                            if (data == "1") {
                                reloadData();
                            }
                        }
                    });
                });
            } else if (layEvent === 'edit') {
                $.post("${request.contextPath}/user/getsingle", {"pguid": trdata.pguid}, function (data, status) {
                    if (status == "success") {
                        if (data != "") {
                            var json = $.parseJSON(data);
                            $("#master_hidpguid").val(json.pguid);
                            $("#manager_txtLoginname").val(json.loginname);//用户名
                            $("#manager_txtdisplayname").val(json.displayname);//昵称
                            $("#manager_txtsfz").val(json.sfz);//
                            $("#manager_txtemail").val(json.email);//邮箱
                            $("#manager_txtcompany").val(json.company);//
                            if (typeof(json.logo) != "undefined" && json.logo != null && json.logo != "") {
                                $("#imgpicfile").show();
                                $("#imgpicfile").prop("src", '${request.contextPath}' + json.logo);
                            }
                            if (json.auth == "1") {
                                $(":radio[name='auth'][value='1']").prop("checked", "true");
                            }
                            else {
                                $(":radio[name='auth'][value='0']").prop("checked", "true");
                            }
                            form.render("select");
                            form.render("radio");
                            $("#trmm").hide();
                            $("#trmmqr").hide();
                            $("#managerlist_btnadd").hide();
                            $("#managerlist_btnupdate").show();
                        } else {
                            layer.msg('数据库中没有这条信息', {icon: 2, time: 2000});
                        }
                        myindex = layer.open({
                            type: 1,
                            title: '信息修改',
                            zIndex: 10000,
                            area: ['600px', '600px'],
                            content: $("#tableax")
                        });
                    }
                });
            }else {
                if (layEvent === 'cho') {
                    myindex= layer.open({
                        type: 1,
                        title: '角色选择',
                        zIndex: 10000,
                        area: ['800px', '600px'],
                        content: $("#manager_tbrole"), //这里content是一个普通的String
                        cancel: function (index, layero) {

                        },
                        success: function (layero, index) {
                            managerRole(trdata.pguid);
                        }
                    });
                }
            }
        });
        form.on('submit(manageradd)', function (data) {
            $.ajax({
                async: false, // 使用同步的方法
                type: "post",
                url: '${request.contextPath}/manager/validate',
                data: { //要提交到服务端验证的用户名
                    "loginname": $("#manager_txtLoginname").val()
                },
                dataType: 'text',
                success: function (result) {
                    if (result == "false") {
                        layer.tips('用户名已存在，请重新录入', '#manager_txtLoginname', {
                            tips: 1
                        });
                        return;
                    }
                    manageradd();
                }
            });
            return false;
        });
        form.on('submit(managerup)', function (data) {
            $.ajax({
                async: false, // 使用同步的方法
                type: "post",
                url: '${request.contextPath}/manager/validate2',
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

    function reloadData() {
        var loginnamestr = document.getElementById("manager_txtLoginnamesea").value;
        //这里以搜索为例
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                loginname: loginnamestr
            }
        });
    }

    function addData() {
        $("#master_hidpguid").val("");
        $("#manager_txtLoginname").val("");//用户名
        $("#manager_txtLoginpwd").val("");//密码
        $("#manager_txtConfirm").val("");//确认密码
        $("#manager_txtdisplayname").val("");//昵称
        $("#manager_txtsfz").val("");//
        $("#manager_txtemail").val("");//邮箱
        $("#manager_txtcompany").val("");//
        $("#imgpicfile").hide();
        $("#imgpicfile").val("");
        $(":radio[name='auth'][value='1']").prop("checked", "true");
        form.render("select");
        form.render("radio");
        $("#trmm").show();
        $("#trmmqr").show();
        $("#managerlist_btnadd").show();
        $("#managerlist_btnupdate").hide();
        myindex = layer.open({
            type: 1,
            zIndex: 10000,
            title: '信息添加',
            area: ['600px', '650px'],
            content: $("#tableax"), //这里content是一个普通的String
        });
    }

    //执行添加
    function manageradd() {
        if (!new RegExp("^[\\S]{6,12}$").test($("#manager_txtLoginpwd").val())) {
            layer.tips('密码必须6到12位，且不能出现空格', '#manager_txtLoginpwd', {
                tips: 1
            });
            return;
        }
        if ($("#manager_txtLoginpwd").val() != $("#manager_txtConfirm").val()) {
            layer.tips('两次密码不一样', '#manager_txtConfirm', {
                tips: 1
            });
            return;
        }
        $("#myform").ajaxForm(function () {
        });
        $("#myform").ajaxSubmit({
            type: "POST",
            url: "${request.contextPath}/manager/add",
            dataType: "text",
            data: {"loginpwd": $("#manager_txtLoginpwd").val()},
            error: function (jqXHR, textStatus, errorThrown) {
                layer.msg('网络问题导致添加信息失败', {icon: 2, time: 2000});
            },
            success: function (data) {
                if (data == "1") {
                    layer.msg('添加成功', {icon: 1, time: 2000});
                    reloadData();
                    layer.close(myindex);
                } else {
                    if (data == "2") {
                        layer.msg('添加失败', {icon: 2, time: 2000});
                    } else {
                        layer.msg(data, {icon: 2, time: 20000});
                    }
                }
            }
        });
        return false;
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
                    reloadData();
                    closeW();
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
        $.post("${request.contextPath}/user/upwd", {
            "pguid": $("#master_hidpguid").val(),
            "loginpwd": $("#master_txtNewpwd").val()
        }, function (data, status) {
            if (status == "success") {
                if (data == "1") {
                    layer.msg('密码修改成功', {icon: 1, time: 2000});
                    closeW();
                } else {
                    layer.msg('密码修改失败', {icon: 2, time: 2000});
                }
            } else {
                layer.msg('密码修改失败', {icon: 2, time: 2000});
            }
        });
    }

    //获取帐号拥有的角色信息
    function managerRole(pguid) {
        $("#master_hidpguid").val(pguid);
        $("#manager_tbrole input[type=checkbox]").removeAttr("checked");
        $.post("${request.contextPath}/role/getlistbypguid", {"pguid": pguid}, function (data, status) {
            if (status == "success") {
                if (data != "") {
                    var json = $.parseJSON(data);
                    for (var i = 0; i < json.length; i++) {
                        document.getElementById(json[i].rcode).checked = true;
                    }
                }
            }
            else {
                layer.msg('网络问题导致角色信息获取失败', {icon: 2, time: 2000});
            }
        });
    }

    function roleadd() {
        var pid = "";
        $("#manager_tbrole input[type=checkbox]:checked").each(function () {
            pid += $(this).attr("id") + ";";
        });
        $.post("${request.contextPath}/role/adduserrole", {
            "pguid": $("#master_hidpguid").val(),
            "rcodestr": pid
        }, function (data, status) {
            if (status == "success") {
                if (data == "1") {
                    layer.msg('角色更新成功', {icon: 1, time: 2000});
                    reloadData();
                    closeW();
                }
                else {
                    layer.msg('角色更新失败', {icon: 2, time: 2000});
                }
            }
            else {
                layer.msg('角色更新失败', {icon: 2, time: 2000});
            }
        });
    }
    function closeW() {
        layer.close(myindex);
    }
</script>
</div>
</@defaultLayout.layout>