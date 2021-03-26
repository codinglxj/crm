# crm
 1. 动态生成的元素，是不能以普通绑定事件的形式来进行操作的
            语法:
                $(需要绑定元素的有效的外层元素).on(绑定事件的方式，需要绑定元素的jquery对象，回调函数)
            $("#activityBody").on("click", $("input[name='xu']"), function () {
                //alert(123);
                $("#qx").prop("checked", $("input[name='xz']").length == $("input[name='xz']:checked").length);
            })

2. javascript:void(0); 禁用超链接，只能以触发事件的形式来操作。
       '<a class="myHref" href="javascript:void(0);" onclick= "deleteRemark(\'' + n.id + '\')"></a>';  回调函数中的参数需要用使用''或者""扩起来
