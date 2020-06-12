$(function () {
    $.ajax({
        url:"/fore/nav/get_nav",
        type:"get",
        success:function (data) {
            $.each(data.data,function (i,obj) {
                $(".meninmenu").append("<li class='drop'><a href='"+obj.url+"'>"+obj.title+"</a></li>")
            })
        }
    })
})