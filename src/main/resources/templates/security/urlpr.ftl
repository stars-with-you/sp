<#-- 引入布局指令的命名空间 -->
<#import "../master/admin.ftl" as defaultLayout>
<#-- 调用布局指令 -->
<@defaultLayout.layout>
<div >
<table id="tableax" class="layui-table layui-form" style="display: none;">
    <tr>
        <td>Url路径:</td>
        <td><input id="urlpr_hidid" type="hidden"
                   style="width: 100px; height: 25px; border-radius: 5px; border: 1px solid #A4D3EE;"/>
            <input id="urlpr_txtskey" class="layui-input" type="text" style="width:300px;"></input>
        </td>
    </tr>
    <tr>
        <td>权限名称:</td>
        <td><input id="urlpr_txtsvalue" class="layui-input" type="text" style="width:300px;"></input></td>
    </tr>
    <tr>
        <td>显示顺序:</td>
        <td><input id="urlpr_txtssx" class="layui-input" type="text" style="width:300px;"></input></td>
    </tr>
    <tr>
        <td>Url拦截说明:</td>
        <td><input id="urlpr_txtsms" class="layui-input" type="text" style="width:300px;"></input></td>
    </tr>
    <tr>
        <td colspan="2" style="text-align: center;"><a id="urlpr_btnadd" href="javascript:void(0)" class="layui-btn  layui-btn-normal"
                                                       onclick="urlpradd()">保存</a>
            <a id="urlpr_btnupdate" href="javascript:void(0)" class="layui-btn  layui-btn-normal" onclick="urlprup()">修改</a>
            <button type="button" class="layui-btn  layui-btn-normal" onclick="closeW();">取消</button>
        </td>
    </tr>
</table>
<div class="layui-body">
    <!-- 内容主体区域 -->
    <div class="layui-card">
        <div class="layui-card-header">
            <h2>访问路径权限设置</h2>
        </div>
        <div class="layui-card-body">
                    <button class="layui-btn layui-btn-normal  layui-btn-sm" id="btnAdd" onclick="addData();">+ 添加信息</button>
                <div  style="display: inline-block;">
                    <label >权限名称：</label>
                    <div class="layui-input-inline">
                        <input id="urlpr_txtSearch" class="layui-input" type="text">
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
            </script>
        </div>
    </div>

<script>
    var tableIns = null, layer = null, myindex = 0;
    layui.use(['table', 'layer', 'laydate', 'element'], function () {
        var table = layui.table, laydate = layui.laydate, element = layui.element;
        layer = layui.layer;
        //第一个实例
        tableIns = table.render(
            {
            elem: '#test',
            url: '/urlpr/data',
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
                field: 'skey',
                title: '请求地址'
            }, {
                field: 'svalue',
                title: '地址权限设置'
            }, {
                field: 'supdatetime',
                title: '修改时间'
            }, {
                field: 'ssx',
                title: '加载顺序'
            },
                {
                    field: 'sms',
                    title: '说明'
                }, {
                    fixed: 'right',
                    align: 'center',
                    width: 120,
                    toolbar: '#barDemo',
                    title: '操作'
                }
            ]
            ]
        }
        );
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
                        url: "/urlpr/delbysguid",
                        type: "post",
                        data: {"sguid": trdata.sguid},
                        dataType: "text",
                        success: function (data) {
                            if (data == "1") {
                                reloadData();
                            }
                            else{
                                layer.msg('删除失败', {icon: 2});
                            }
                        }
                    });
                });
            } else if (layEvent === 'edit') {
                $.post("/urlpr/getsingle", {"sguid": trdata.sguid}, function (data, status) {
                    if (status == "success") {
                        if (data != "") {
                            var json = $.parseJSON(data);
                            $("#urlpr_hidid").val(json.sguid);
                            $("#urlpr_txtskey").val(json.skey);//
                            $("#urlpr_txtsvalue").val(json.svalue);//
                            $("#urlpr_txtssx").val(json.ssx);//
                            $("#urlpr_txtsms").val(json.sms);//
                            $("#urlpr_btnadd").hide();
                            $("#urlpr_btnupdate").show();
                        } else {
                            layer.msg('数据库中没有这条信息', {icon: 2});
                        }
                        myindex = layer.open({
                            type: 1,
                            title: 'url拦截信息修改',
                            zIndex: 10000,
                            area: ['600px', '400px'],
                            content: $("#tableax")
                        });
                    }
                });
            }
        });
    });

    function reloadData() {
        var urlpr_txtSearch = document.getElementById("urlpr_txtSearch").value;
        //这里以搜索为例
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                skey: urlpr_txtSearch
            }
        });
    }

    function addData() {
        $("#urlpr_txtskey").val("");//
        $("#urlpr_txtsvalue").val("");//
        $("#urlpr_txtssx").val("");//
        $("#urlpr_txtsms").val("");//
        $("#urlpr_btnadd").show();
        $("#urlpr_btnupdate").hide();
        myindex = layer.open({
            type: 1,
            zIndex: 10000,
            title: 'url拦截信息添加',
            area: ['600px', '400px'],
            content: $("#tableax")
        });
    }

    //增加一条信息
    function urlpradd() {
        if ($("#urlpr_txtskey").val() == "") {
            layer.msg('请填写url路径', {icon: 2});
            return;
        }
        if ($("#urlpr_txtsvalue").val() == "") {
            layer.msg('请填写url对应的权限', {icon: 2});
            return;
        }
        var opts = {
            "skey": $("#urlpr_txtskey").val(),
            "svalue": $("#urlpr_txtsvalue").val(),
            "ssx": $("#urlpr_txtssx").val(),
            "sms": $("#urlpr_txtsms").val()
        };
        $.post("/urlpr/add", opts, function (data, status) {
            if (status == "success") {
                if (data == "1") {
                    layer.msg('信息添加成功', {icon: 1});
                    reloadData();
                    layer.close(myindex);
                } else {
                    layer.msg('信息添加失败', {icon: 2});
                }
            }
            else {
                layer.msg('网络问题导致添加信息失败', {icon: 2});
            }
        });
    }

    function urlprup() {
        if ($("#urlpr_txtskey").val() == "") {
            layer.msg('请填写url路径', {icon: 2});
            return;
        }
        if ($("#urlpr_txtsvalue").val() == "") {
            layer.msg('请填写url对应的权限', {icon: 2});
            return;
        }
        var opts = {
            "sguid": $("#urlpr_hidid").val(),
            "skey": $("#urlpr_txtskey").val(),
            "svalue": $("#urlpr_txtsvalue").val(),
            "ssx": $("#urlpr_txtssx").val(),
            "sms": $("#urlpr_txtsms").val()
        };
        $.post("/urlpr/updatebysguid", opts, function (data, status) {
            if (status == "success") {
                if (data == "1") {
                    layer.msg('信息修改成功', {icon: 1});
                    reloadData();
                    layer.close(myindex);
                } else {
                    layer.msg('信息修改失败', {icon: 2});
                }
            }
            else {
                layer.msg('网络问题导致修改信息失败', {icon: 2});
            }
        });
    }
    function closeW() {
        layer.close(myindex);
    }
</script>
</div>
    </@defaultLayout.layout>
