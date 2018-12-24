<#-- 引入布局指令的命名空间 -->
<#import "../master/admin.ftl" as defaultLayout>
<#-- 调用布局指令 -->
<@defaultLayout.layout>
<div>
<table id="tableax" class="layui-table layui-form" style="display: none;">
    <tr>
        <td>角色名称:</td>
        <td>
            <input id="role_txtcode" type="hidden"></input>
            <input id="role_txtrname" class="layui-input"  type="text" ></input>
        </td>
    </tr>
    <tr>
        <td colspan="2" style="text-align: center;">
            <a id="role_btnadd" href="javascript:void(0)" class="layui-btn  layui-btn-normal " onclick="roleadd()">保存</a>
            <a id="role_btnupdate" href="javascript:void(0)" class="layui-btn  layui-btn-normal " onclick="roleup()">修改</a>
            <button type="button" class="layui-btn  layui-btn-normal" onclick="closeW();">取消</button>
        </td>
    </tr>
</table>
<table id="role_tbperm"   style="min-width:600px;display: none;"  class="layui-table">
    <thead>
    <tr>
        <th></th> <th>权限名称</th> <th>权限url</th>
    </tr>
    </thead>
    <tbody id="role_tby"></tbody>
    <tfoot>
    <tr>
        <td colspan="3" style="text-align: center;">
            <input type="hidden" id="role_hidrcode" />
            <a  href="javascript:void(0)" class="layui-btn  layui-btn-normal " onclick="permadd()">更新角色拥有权限</a>
            <button type="button" class="layui-btn  layui-btn-normal" onclick="closeW();">取消</button>
        </td>
    </tr>
    </tfoot>

</table>
<div class="layui-body">
    <!-- 内容主体区域 -->
    <div class="layui-card">
        <div class="layui-card-header">
            <h2>角色列表</h2>
        </div>
        <div class="layui-card-body">
                <button class="layui-btn layui-btn-normal  layui-btn-sm" id="btnAdd" onclick="addData();">+ 添加信息</button>

            <div style="display: inline-block;">
                <label>角色名称：</label>
                <div class="layui-input-inline">
                    <input id="role_txtSearch" class="layui-input" type="text">
                </div>
            </div>
            <div class="layui-inline">
                <button class="layui-btn layui-btn-normal  layui-btn-sm" id="btnSearch" onclick="reloadData();">查询信息</button>
            </div>
        </div>
        <table class="layui-hide" id="test" lay-filter="test"></table>
        <script type="text/html" id="barDemo">
            <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
            <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="cho">选择权限</a>
        </script>
    </div>
</div>
<script>
    var tableIns = null, layer = null;
    layui.use(['table', 'layer', 'laydate', 'element'], function () {
        var table = layui.table, laydate = layui.laydate, element = layui.element;
        layer = layui.layer;
        //第一个实例
        tableIns = table.render({
            elem: '#test'
            , url: '${request.contextPath}/role/getlist',
            request: {
                pageName: 'pn' //页码的参数名称，默认：page
                , limitName: 'ps' //每页数据量的参数名，默认：limit
            },
            method: 'post',
            page: true //开启分页
            ,
            cols: [
                [
                    {
                field: 'rname',
                title: '角色名称'
            }, {
                field: 'rcode',
                    width: 100,
                title: '角色代码'
            }, {
                field: 'yxbz',
                title: '拥有的权限'
            },{
                fixed: 'right',
                align: 'center',
                width: 300,
                toolbar: '#barDemo',
                title: '操作'
            }
            ]
            ]
        });
        //监听工具条
        table.on('tool(test)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var trdata = obj.data //获得当前行数据
                , layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'del') {
                layer.confirm('真的删除行么', function (index) {
                    //obj.del(); //删除对应行（tr）的DOM结构
                    layer.close(index);
                    //向服务端发送删除指令
                    $.ajax({
                        url: "${request.contextPath}/role/delbyrcode",
                        type: "post",
                        data: {"rcode": trdata.rcode},
                        dataType: "text",
                        success: function (data) {
                            if (data == "1") {
                                reloadData();
                            }
                        }
                    });
                });
            } else if (layEvent === 'edit') {
                $.post("${request.contextPath}/role/getsingle", {"rcode": trdata.rcode}, function (data, status) {
                    if (status == "success") {
                        if (data != "") {
                            var json = $.parseJSON(data);
                            $("#role_txtcode").val(json.rcode);//
                            $("#role_txtrname").val(json.rname);//
                            $("#role_btnadd").hide();
                            $("#role_btnupdate").show();
                        } else {
                            layer.msg('数据库中没有这条信息', {icon: 2});
                        }
                      myindex=  layer.open({
                            type: 1,
                            title: '信息修改',
                            zIndex: 10000,
                            area: ['400px', '200px'],
                            content: $("#tableax"), //这里content是一个普通的String
                        });
                    }
                });
            }
            else {
                if (layEvent === 'cho') {
                 myindex=   layer.open({
                        type: 1,
                        title: '角色选择',
                        zIndex: 10000,
                        area: ['800px', '600px'],
                        content: $("#role_tbperm"), //这里content是一个普通的String
                        cancel: function (index, layero) {

                        },
                        success: function (layero, index) {
                            roleperm(trdata.rcode);
                        }
                    });
                }
            }
        });
    });

    function reloadData() {
        var rnamestr = document.getElementById("role_txtSearch").value;
        //这里以搜索为例
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                rname: rnamestr
            }
        });
    }
    function addData() {
        $("#role_txtcode").val("");//
        $("#role_txtrname").val("");//
        $("#role_btnadd").show();
        $("#role_btnupdate").hide();
       myindex= layer.open({
            type: 1,
            zIndex: 10000,
            title: '信息添加',
            area: ['400px', '200px'],
            content: $("#tableax"), //这里content是一个普通的String
        });
    }
    //增加一条信息
    function roleadd()
    {
        if($("#role_txtrname").val()==""){
            layer.msg('请填写角色名称', {icon: 2});
            return;
        }
        $.post("${request.contextPath}/role/add", {"rname": $("#role_txtrname").val()}, function (data, status) {

            if (status=="success") {
                if (data=="1") {
                    layer.msg('信息添加成功', {icon: 1});
                    reloadData();
                    closeW();
                } else {
                    layer.msg('信息添加失败', {icon: 2});
                }
            }
            else {
                layer.msg('信息添加失败', {icon: 2});
            }
        });
    }
    //角色修改
    function roleup()
    {
        if($("#role_txtrname").val()==""){
            layer.msg('请填写角色名称', {icon: 2});
            return;
        }
        $.post("${request.contextPath}/role/updatebyrcode", {
            "rname": $("#role_txtrname").val(),
            "rcode": $("#role_txtcode").val()
        }, function (data, status) {
            if (status=="success") {
                if (data=="1") {
                    layer.msg('信息修改成功', {icon: 1});
                    reloadData();
                    closeW();
                } else {
                    layer.msg('信息修改失败', {icon: 2});
                }
            }
            else {
                layer.msg('信息修改失败', {icon: 2});
            }
        });
    }
    //获取角色拥有的权限信息
    function roleperm(rcode)
    {
        $("#role_hidrcode").val(rcode);
        $("#role_tbperm input[type=checkbox]").removeAttr("checked");
        $.post("${request.contextPath}/perm/getlistbyrcode", {"rcode": rcode}, function (data, status) {
            if (status=="success") {
                if (data!="") {
                    var json=$.parseJSON(data);
                    for (var i = 0; i < json.length; i++) {
                        document.getElementById(json[i].pid).checked=true;
                    }
                }
            }
            else {
                layer.msg("网络问题导致权限信息获取失败",{icon:2});
            }
        });
    }

    function permadd()
    {
        var pid="";
        $("#role_tbperm input[type=checkbox]:checked").each(function(){
            pid+=$(this).attr("id")+";";
        });
        $.post("${request.contextPath}/role/addroleperm", {
            "rcode": $("#role_hidrcode").val(),
            "pidstr": pid
        }, function (data, status) {
            if (status=="success") {
                if (data=="1") {
                    layer.msg('权限更新成功',{icon:1});
                    reloadData();
                    closeW();
                }
                else {
                    layer.msg('权限更新失败',{icon:2});
                }
            }
            else {
                layer.msg('网络问题导致权限更新失败',{icon:2});
            }
        });
    }
    $(function () {
        //绑定添加权限按钮事件
        $.post("${request.contextPath}/perm/getlistall", function (data, status) {
            if (status=="success") {
                if (data!="") {
                    var json=$.parseJSON(data);
                    var str="";
                    for (var i = 0; i < json.length; i++) {
                        str+="<tr><td><input id='"+json[i].pid+"' type='checkbox'/></td><td>"+json[i].pname+"</td><td>"+json[i].purl+"</td></tr>";
                    }
                    $("#role_tby").html(str);

                } else {
                    layer.msg("系统中没有权限相关信息",{icon:2});
                }
            }
            else {
                layer.msg("网络问题导致权限信息获取失败",{icon:2});
            }
        });
    });
    function closeW() {
        layer.close(myindex);
    }
</script>
</div>
    </@defaultLayout.layout>
