<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

    <script type="text/javascript">
        $(function () {

            //使login.jsp始终在顶层窗口中打开

            if(window.top != window){
                window.top.location = window.top;
            }

            //页面加载完毕后，将用户文本框中的内容清空
            $("#loginAct").html("");
            //页面加载完毕后，让用户的文本框自动获得焦点
            $("#loginAct").focus();

            //为登录按钮绑定事件，执行登录操作
            $("#submitBtn").click(login);

            //为当前登录页窗口绑定敲键盘事件
            $(window).keydown(fun2);


        })

        function login() {
            //去除前后空白
            var loginAct = $.trim($("#loginAct").val());
            var loginPwd = $.trim($("#loginPwd").val());

            //
            if (loginAct == "" || loginPwd == "") {
                $("#msg").html("账号密码不能为空");
                //如果账号密码为空，则及时终止该方法
                return false;
            }
            //去后台验证登录信息操作
            $.ajax({
                url: "settings/user/login.do",
                type: "post",
                data: {
                    "loginAct": loginAct,
                    "loginPwd": loginPwd
                },
                dataType: "json",
                success: function (data) {

                    /*data: {"success" : true, "msg" : "错误信息}*/
                    if (data.success) {

                        //如何信息验证成功，跳转到工作台的起始页(欢迎页)
                        window.location.href = "workbench/index.jsp";
                    } else {
                        $("#msg").html(data.msg);
                    }

                }
            })

        }

        function fun2(event) {
            //alert(event.keyCode);
            if (event.keyCode == 13) {
                login();
            }
        }
    </script>

</head>
<body>
<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
    <img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
</div>
<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
    <div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">
        CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
</div>

<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
    <div style="position: absolute; top: 0px; right: 60px;">
        <div class="page-header">
            <h1>登录</h1>
        </div>
        <form action="workbench/index.jsp" class="form-horizontal" role="form">
            <div class="form-group form-group-lg">
                <div style="width: 350px;">
                    <input class="form-control" type="text" placeholder="用户名" id="loginAct">
                </div>
                <div style="width: 350px; position: relative;top: 20px;">
                    <input class="form-control" type="password" placeholder="密码" id="loginPwd">
                </div>
                <div class="checkbox" style="position: relative;top: 30px; left: 10px;">

                    <span id="msg" style="color: red"></span>

                </div>
                <%--将提交改为普通按钮，需要先验证信息，然后在做页面的跳转--%>
                <button type="button" id="submitBtn" class="btn btn-primary btn-lg btn-block"
                        style="width: 350px; position: relative;top: 45px;">登录
                </button>
            </div>
        </form>
    </div>
</div>
</body>
</html>