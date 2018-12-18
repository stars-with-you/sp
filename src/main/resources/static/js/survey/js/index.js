$(document).ready(function (e) {
    $('#addquerstions').change(function () {

        // debugger



    });

    //鼠标移上去显示按钮
    $(".movie_box").hover(function () {
        alert("hao");
        var html_cz = "<div class='kzqy_czbut'><a href='javascript:void(0)' class='sy'>上移</a><a href='javascript:void(0)'  class='xy'>下移</a><a href='javascript:void(0)'  class='bianji' onclick='surveybj()'>编辑</a><a href='javascript:void(0)' class='del' >删除</a></div>"
        $(this).css({
            "border": "1px solid #0099ff"
        });
        $(this).children(".wjdc_list").after(html_cz);
        //下移
        $(".xy").live("click", );

    }, function () {
        $(this).css({
            "border": "1px solid #215"
        });
        $(this).children(".kzqy_czbut").remove();
        //$(this).children(".dx_box").hide();
     });



    // body...
});
