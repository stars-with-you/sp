<#-- 引入布局指令的命名空间 -->
<#import "../master/admin.ftl" as defaultLayout>
<#-- 调用布局指令 -->
<@defaultLayout.layout>
<div>
<form class="layui-form">
<table id="tableax" class="layui-table" style="display: none;">
    <tr>
        <td>所属菜单:</td>
        <td><input id="menulist_hidpid" type="hidden"
                   style="width: 100px; height: 25px; border-radius: 5px; border: 1px solid #A4D3EE;" />
            <select id="menulist_slcata">
            </select></td>
    </tr>
    <tr id="trmenucode">
        <td>菜单代码:</td>
        <td><input id="menulist_txtmenucode" class="layui-input" type="text"></input></td>
    </tr>
    <tr>
        <td>菜单名称:</td>
        <td><input id="menulist_txtmenuname" class="layui-input" type="text"
                   name="menulist_txtmenuname" ></input></td>
    </tr>
    <tr style="display: none;">
        <td>所属菜单代码:</td>
        <td><input id="txtparmenucode" class="layui-input" type="text"></input></td>
    </tr>
    <tr>
        <td>菜单路径:</td>
        <td><input id="menulist_txturl"  class="layui-input" type="text" ></input></td>
    </tr>
    <tr>
        <td>菜单图标名称:</td>
        <td><input id="menulist_txticonname"  class="layui-input" type="text" ></input></td>
    </tr>
    <tr>
        <td>菜单显示顺序:</td>
        <td>
            <input id="menulist_txtsort"  class="layui-input" type="text" ></input>
        </td>
    </tr>
    <tr>
        <td>菜单访问权限:</td>
        <td>
            <input id="menulist_txtpermission"  class="layui-input" type="text" ></input>
        </td>
    </tr>
    <tr>
        <td colspan="2" style="text-align: center;">
            <a id="menulist_btnadd" href="javascript:void(0)" class="layui-btn layui-btn-normal" onclick="menuadd()">保存</a>
            <a id="menulist_btnupdate"  href="javascript:void(0)" class="layui-btn layui-btn-normal" onclick="menuup()">修改</a>
            <button type="button" class="layui-btn  layui-btn-normal" onclick="closeW();">取消</button>
        </td>
    </tr>
</table>
</form>
<div class="layui-body">
    <!-- 内容主体区域 -->
    <div class="layui-card">
        <div class="layui-card-header">
            <h2>菜单管理</h2>
        </div>
        <div class="layui-card-body">
                <button class="layui-btn  layui-btn-normal  layui-btn-sm" id="btnAdd" onclick="addData();">+ 添加信息</button>

            <div style="display: inline-block;">
                <label >菜单名称：</label>
                <div class="layui-input-inline">
                    <input id="perm_txtSearch" class="layui-input" type="text">
                </div>
            </div>
            <div class="layui-inline">
                <button class="layui-btn layui-btn-normal  layui-btn-sm" id="btnSearch" onclick="reloadData();">查询信息</button>
            </div>
        </div>
        <table class="layui-hide" id="test" lay-filter="test"></table>
        <script type="text/html" id="barDemo">
            <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
        </script>
    </div>
</div>
<script>
    var tableIns = null, layer = null,myindex=0,form=null;

    layui.use(['table', 'layer', 'laydate', 'element','form'], function () {
        var table = layui.table, laydate = layui.laydate, element = layui.element;
        layer = layui.layer;form = layui.form;
        //第一个实例
        tableIns = table.render({
            elem: '#test'
            , url: '/menu/data',
            request: {
                pageName: 'currentPage' //页码的参数名称，默认：page
                , limitName: 'pagesize' //每页数据量的参数名，默认：limit
            },
            method: 'post',
            page: true //开启分页
            ,
            cols:
                [
                [
                    {
                field: 'menucode',
                title: '菜单代码'
            },{
                field: 'cata',
                title: '菜单级别',
            }, {
                field: 'menuname',
                title: '菜单名称'
            },  {
                field: 'parentcode',
                title: '所属菜单'
            }, {
                field: 'url',
                title: '菜单路径'
            }, {
                field: 'sort',
                title: '显示顺序',
            }, {
                    field: 'permission',
                    title: '访问权限',
                }, {
                fixed: 'right',
                align: 'center',
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
                layer.confirm('确定删除？', function (index) {
                    //obj.del(); //删除对应行（tr）的DOM结构
                    layer.close(index);
                    //向服务端发送删除指令
                    $.ajax({
                        url: "/menu/delbymid",
                        type: "post",
                        data: {"mid": trdata.mid},
                        dataType: "text",
                        success: function (data) {
                            if (data == "1") {
                                reloadData();
                            }
                        }
                    });
                });
            } else if (layEvent === 'edit') {
                $.post("/menu/getsingle", {"mid": trdata.mid}, function (data, status) {
                    if (status == "success") {
                        if (data != "") {
                            var json = $.parseJSON(data);
                            $("#menulist_slcata").val(json.parentcode);
                            $("#menulist_txtmenuname").val(json.menuname);
                            $("#menulist_txturl").val(json.url);
                            $("#menulist_txtmenucode").val(json.menucode);
                            $("#txtparmenucode").val(json.parentcode);
                            $("#trmenucode").show();
                            $("#menulist_hidpid").val(json.mid);
                            $("#menulist_txticonname").val(json.iconname);
                            $("#menulist_txtsort").val(json.sort);
                            $("#menulist_txtpermission").val(json.permission);
                            $("#menulist_btnadd").hide();
                            $("#menulist_btnupdate").show();
                            form.render("select");
                        } else {
                            layer.msg('数据库中没有这条信息', {icon: 2,time:2000});
                        }
                        myindex= layer.open({
                            type: 1,
                            title: '菜单信息修改',
                            zIndex: 10000,
                            area: ['600px', '500px'],
                            content: $("#tableax")
                        });
                    }
                });
            }
        });
    });

    function reloadData() {
        var pnamestr = document.getElementById("perm_txtSearch").value;
        //这里以搜索为例
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                menuname: pnamestr
            }
        });
    }
    function addData() {
        $("#menulist_txtmenuname").val("");
        $("#menulist_txtmenucode").val("");
        $("#txtparmenucode").val("");
        $("#trmenucode").hide();
        $("#menulist_txturl").val("");
        $("#menulist_hidpid").val("");
        $("#menulist_slcata").val("");
        $("#menulist_txticonname").val("");
        $("#menulist_txtsort").val("");
        $("#menulist_txtpermission").val("");
        $("#menulist_btnadd").show();
        $("#menulist_btnupdate").hide();
        form.render("select");
        myindex=layer.open({
            type: 1,
            zIndex: 10000,
            title: '菜单信息添加',
            area: ['600px', '500px'],
            content: $("#tableax") //这里content是一个普通的String
        });
    }
    //增加一条信息
    function menuadd()
    {
        if($("#menulist_txtmenuname").val()==""){
            layer.msg('请填写菜单名称', {icon: 2,time:2000});
            return;
        }
        if($("#menulist_txturl").val()==""){
            layer.msg('请填写菜单路径', {icon: 2,time:2000});
            return;
        }
        $.post("/menu/add",{"cata":$("#menulist_slcata").val(),"menuname":$("#menulist_txtmenuname").val(),"menucode":$("#menulist_txtmenucode").val(),"url":$("#menulist_txturl").val(),"iconname":$("#menulist_txticonname").val(),"sort":$("#menulist_txtsort").val(),"permission":$("#menulist_txtpermission").val()},function(data,status){
            if (status=="success") {
                if (data=="1") {
                    layer.msg('信息添加成功', {icon: 1,time:2000});
                    reloadData();
                    layer.close(myindex);
                } else {
                    layer.msg('信息添加失败', {icon: 2,time:2000});
                }
            }
            else {
                layer.msg('网络问题导致添加信息失败', {icon: 2,time:2000});
            }
        });
    }
    function menuup()
    {
        if($("#menulist_txtmenuname").val()==""){
            layer.msg('请填写菜单名称', {icon: 2,time:2000});
            return;
        }
        if($("#menulist_txturl").val()==""){
            layer.msg('请填写菜单路径', {icon: 2,time:2000});
            return;
        }
        if($("#menulist_txtmenucode").val()==""){
            layer.msg('请填写菜单代码', {icon: 2,time:2000});
            return;
        }
        $.post("/menu/updatebymid",{"mid":$("#menulist_hidpid").val(),"cata":$("#menulist_slcata").val(),"menuname":$("#menulist_txtmenuname").val(),"menucode":$("#menulist_txtmenucode").val(),"url":$("#menulist_txturl").val(),"iconname":$("#menulist_txticonname").val(),"sort":$("#menulist_txtsort").val(),"permission":$("#menulist_txtpermission").val()},function(data,status){
            if (status=="success") {
                if (data=="1") {
                    layer.msg('信息修改成功', {icon: 1,time:2000});
                    layer.close(myindex);
                    reloadData();
                } else {
                    layer.msg('信息修改失败', {icon: 2,time:2000});
                }
            }
            else {
                layer.msg('网络问题导致修改信息失败', {icon: 2,time:2000});
            }
        });
    }
    function getMenu()
    {
        $.post("/menu/getmenu",{},function(data,status)
        {
            if (status=="success") {
                var rsarr=$.parseJSON(data);
                $("#menulist_slcata").html("");
                var strslcata="<option value=''>没有上级菜单</option>";
                for (var i = 0; i < rsarr.length; i++) {
                    if (rsarr[i].cata=="1") {
                        strslcata+="<option value='"+rsarr[i].menucode+"'>"+rsarr[i].menuname+"</option>";
                    }
                }
                $("#menulist_slcata").html(strslcata);
            }
        });
    }
    getMenu();
    function closeW() {
        layer.close(myindex);
    }
</script>
</div>
    </@defaultLayout.layout>
