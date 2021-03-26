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
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>


    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

    <script type="text/javascript">

        //为创建按钮绑定事件，打开添加操作的模态窗口
        $(function () {

            $("#addBtn").click(function () {


                $(".time").datetimepicker({
                    minView: "month",
                    language: "zh-CN",
                    format: 'yyyy-mm-dd',
                    autoclose: true,
                    todayBtn: true,
                    pickerPosition: "bottom-left"
                });

                /*操作模态窗口的方式:
                    需要操作的模态窗口的jquery对象，调用modal方法，为该方法传递参数：show 打开模态窗口， hide关闭模态窗口

                */
                //alert("134");
                //$("#createActivityModal").modal("show");

                //通过从后台拿取数据，在下拉列表中显示
                $.ajax({
                    url: "workbench/activity/getUserList.do",
                    type: "get",
                    dataType: "json",
                    success: function (data) {
                        var html = "";
                        $.each(data, function (i, n) {
                            html += "<option value='" + n.id + "'>" + n.name + "</option>";
                            //alert(n.id);
                        });

                        $("#create-owner").html(html);
                        //将当前登录的用户，设置为下拉框默认的选项
                        //取得当前登录用户的id
                        //在js中使用el表达式，el表达式一定要套用在字符串中
                        var id = "${user.id}";

                        $("#create-owner").val(id);
                        //所有者下拉框处理完毕后，展现模态窗口
                        $("#createActivityModal").modal("show");
                    }

                });

            });

            //为保存按钮绑定事件,执行添加操作
            $("#savBtn").click(function () {
                $.ajax({
                    url: "workbench/activity/save.do",
                    data: {
                        "owner": $.trim($("#create-owner").val()),
                        "name": $.trim($("#create-name").val()),
                        "startDate": $.trim($("#create-startDate").val()),
                        "endDate": $.trim($("#create-endDate").val()),
                        "cost": $.trim($("#create-cost").val()),
                        "description": $.trim($("#create-description").val()),

                    },
                    dataType: "json",
                    type: "post",
                    success: function (data) {
                        //data
                        //{"success" : true/false}
                        if (data.success) {
                            //添加成功后
                            //刷新市场活动信息列表  (局部刷新)


                            //清空添加操作模态窗口中的数据
                            //jquery中的reset方法不能使用，将jquery对象转成dom对象
                            $("#activityAddForm")[0].reset();

                            //pageList(1, 2);

                            /*$("#activityPage").bs_pagination('getOption', 'currentPage'
                            *   操作后停留在当前页
                            *
                            *  $("#activityPage").bs_pagination('getOption', 'rowsPerPage')
                            *       操作后维持已经设置好的每页展现的记录数
                            *
                            * 这两个参数不需要我们进行修改
                            *   直接使用即可
                            * */

                            //做完操作后，应该回到第一页，维持每页展现的记录数
                            pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
                            //关闭添加操作的模态窗口
                            $("#createActivityModal").modal("hide");
                        } else {
                            alert("添加市场活动失败");
                        }
                    }
                })
            });

            //页面加载完毕之后触发一个方法

            pageList(1, 2);

            //为查询绑定事件，触发pageList方法
            $("#searchBtn").click(function () {
                /*点击查询按钮的时候，我们将搜索框中的信息保存起来，保存到隐藏域中*/
                $("#hidden-name").val($.trim($("#search-name").val()));
                $("#hidden-owner").val($.trim($("#search-owner").val()));
                $("#hidden-startDate").val($.trim($("#search-startDate").val()));
                $("#hidden-endDate").val($.trim($("#search-endDate").val()));


                pageList(1, 2);
            })

            //为全选的复选框绑定事件，触发全选操作
            $("#qx").click(function () {
                $("input[name='xz']").prop("checked", this.checked);
            })

            //以下这种做法是不行的
            /* $("input[name=xz]").click(function () {
                 alert(123)
             })*/

            /*动态生成的元素，是不能以普通绑定事件的形式来进行操作的
            语法:
                $(需要绑定元素的有效的外层元素).on(绑定事件的方式，需要绑定元素的jquery对象，回调函数)*/
            $("#activityBody").on("click", $("input[name='xu']"), function () {
                //alert(123);
                $("#qx").prop("checked", $("input[name='xz']").length == $("input[name='xz']:checked").length);
            })

            //为市场活动的删除按钮添加事件
            $("#deleteBtn").click(function () {

                //找到选择了哪些要删除的按钮
                var $xz = $("input[name='xz']:checked");
                //判断是选择框中是否有元素被勾选
                if ($xz.length == 0) {

                    alert("请选择要删除的市场活动");


                } else {
                    if(confirm("是否确定删除")){
                        var param = "";
                        for (var i = 0; i < $xz.length; i++) {
                            var data = $($xz[i]).val();
                            param += "id=" + data;
                            if (i < $xz.length - 1) {
                                param += "&";
                            }

                        }
                        //alert(data);
                        //id=6b&id=60
                        $.ajax({
                            url: "workbench/activity/delete.do",
                            data: param,
                            urlType: "get",
                            datType: "json",
                            success: function (resp) {
                                if (resp.success) {


                                    //pageList(1, 2);
                                    //删除成功后
                                    //回到第一页，维持每页展现的记录数
                                    pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

                                } else {
                                    alert("删除市场活动失败");
                                }
                            }
                        })
                    }

                }

            })

            //为市场活动修改按钮添加事件
            $("#editBtn").click(function () {

                $(".time").datetimepicker({
                    minView: "month",
                    language: "zh-CN",
                    format: 'yyyy-mm-dd',
                    autoclose: true,
                    todayBtn: true,
                    pickerPosition: "bottom-left"
                });


                var $xz = $("input[name='xz']:checked");

                //判断勾选的√的数量
                if($xz.length == 0){
                    alert("请选择要修改的市场活动");
                }else if($xz.length > 1){
                    alert("只能选择一条市场活动进行修改");
                }else{
                    //获取复选框的id
                    var id = $xz.val();
                    //发送ajax请求
                    $.ajax({
                        url: "workbench/activity/getUserListAndActivity.do",
                        type:"post",
                        data: {"id" : id},
                        datType:"json",

                        //希望的返回值{"userList":[{用户1},{2},...], "a":{activity信息}}
                        success:function (data) {

                            var html = "<option></option>";
                            $.each(data.userList, function (i, n) {
                                html += "<option value='"+n.id+"'>"+n.name+"</option>"
                            })

                            $("#edit-owner").html(html);


                            $("#edit-id").val(data.a.id);
                            $("#edit-name").val(data.a.name);
                            $("#edit-owner").val(data.a.owner);
                            $("#edit-startDate").val(data.a.startDate);
                            $("#edit-endDate").val(data.a.endDate);
                            $("#edit-cost").val(data.a.cost);
                            $("#edit-description").val(data.a.description);

                            //所有值填写好了之后，打开修改操作的模态窗口
                            $("#editActivityModal").modal("show");
                        }


                    })


                }
            })

            //为修改中的更新按钮绑定事件

            $("#updateBtn").click(function () {
                $.ajax({
                    url: "workbench/activity/update.do",
                    data: {
                        "id": $.trim($("#edit-id").val()),
                        "owner": $.trim($("#edit-owner").val()),
                        "name": $.trim($("#edit-name").val()),
                        "startDate": $.trim($("#edit-startDate").val()),
                        "endDate": $.trim($("#edit-endDate").val()),
                        "cost": $.trim($("#edit-cost").val()),
                        "description": $.trim($("#edit-description").val()),

                    },
                    dataType: "json",
                    type: "post",
                    success: function (data) {
                        //data
                        //{"success" : true/false}
                        if (data.success) {
                            //添加成功后
                            //刷新市场活动信息列表  (局部刷新)
                            //pageList(1,2);
                            /*修改操作后，应该维持在当前页，维持每页展现的记录数*/
                            pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
                                ,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));


                            //关闭添加操作的模态窗口
                            $("#editActivityModal").modal("hide");
                        } else {
                            alert("修改市场活动失败");
                        }
                    }
                })
            })



        });

        /*
        对于所有的关系型数据库，做前端的分页的相关操作的基本组价
            就是pageNo和pageSize
            pageNo:页码
            pageSize:每页展现的记录数

         pageList方法：当页面加载完毕后，发出一个ajax请求，后台根据请求返回市场活动列表信息数据，局部刷新市场活动信息列表

        都有哪些地方需要调用pageList方法：
        1.点击左侧"市场活动"时
        2.创建，修改，删除后需要刷新市场活动列表
        3.点击查询按钮时
        4.点击分页组件时

        * */
        function pageList(pageNo, pageSize) {
            //每次调用分页查询时，将复选框的√取消掉

            $("#input[name='xz']").prop("checked", false);

            //查询前，将隐藏域中保存的信息取出来，重新赋予到搜索框中
            $("#search-name").val($.trim($("#hidden-name").val()));
            $("#search-owner").val($.trim($("#hidden-owner").val()));
            $("#search-startDate").val($.trim($("#hidden-startDate").val()));
            $("#search-endDate").val($.trim($("#hidden-endDate").val()));

            $.ajax({
                url: "workbench/activity/pageList.do",
                type: "get",
                dateType: "json",
                data: {
                    "pageNo": pageNo,
                    "pageSize": pageSize,
                    "search-name": $.trim($("#search-name").val()),
                    "search-owner": $.trim($("#search-owner").val()),
                    "search-startDate": $.trim($("#search-startDate").val()),
                    "search-endDate": $.trim($("#search-endDate").val())
                },
                success: function (data) {
                    //alert("接下来执行分页");
                    /* 返回值：{"total":100, dataList:[{市场活动1}，{2}，{3}]}*/
                    var html = "";
                    $.each(data.dataList, function (i, n) {

                        html += '<tr class="active">';
                        html += '<td><input type="checkbox" name="xz" value="' + n.id + '"/></td>';
                        html += '<td><a style="text-decoration: none; cursor: pointer;"onclick="window.location.href=\'workbench/activity/detail.do?id='+n.id+'\';">' + n.name + '</a></td>';
                        html += '<td>' + n.owner + '</td>';
                        html += '<td>' + n.startDate + '</td>';
                        html += '<td>' + n.endDate + '</td>';
                        html += '</tr>';
                    });
                    $("#activityBody").html(html);
                    var totalPages = Math.ceil(data.total / pageSize);
                    //计算总页数

                    //数据处理完毕后，结合分页查询，对前端展现分页信息
                    $("#activityPage").bs_pagination({
                        currentPage: pageNo, // 页码
                        rowsPerPage: pageSize, // 每页显示的记录条数
                        maxRowsPerPage: 20, // 每页最多显示的记录条数
                        totalPages: totalPages, // 总页数
                        totalRows: data.total, // 总记录条数

                        visiblePageLinks: 3, // 显示几个卡片

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,
                        //该回调函数是在，点击分页组件的时候触发的
                        onChangePage: function (event, data) {
                            pageList(data.currentPage, data.rowsPerPage);
                        }
                    });

                }
            })

        }

    </script>
</head>
<body>


<input type="hidden" id="hidden-name"/>
<input type="hidden" id="hidden-owner"/>
<input type="hidden" id="hidden-startDate"/>
<input type="hidden" id="hidden-endDate"/>

<!-- 创d建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">

                <form id="activityAddForm" class="form-horizontal" role="form">

                    <div class="form-group">
                        <label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-owner">


                            </select>
                        </div>
                        <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-startDate" readonly>
                        </div>
                        <label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-endDate" readonly>
                        </div>
                    </div>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-description"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">

                <%--
                    data-dismiss="modal":关闭模态窗口
                --%>

                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="savBtn">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">
                    <input type="hidden" id="edit-id"/>

                    <div class="form-group">
                        <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="edit-owner">
                                <%--<option>zhangsan</option>
                                <option>lisi</option>
                                <option>wangwu</option--%>>
                            </select>
                        </div>
                        <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startTime" class="col-sm-2 control-label ">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-startDate" >
                        </div>
                        <label for="edit-endTime" class="col-sm-2 control-label ">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-endDate" >
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-cost" >
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="edit-description"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="updateBtn">更新</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>市场活动列表</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" type="text" id="search-name">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" type="text" id="search-owner">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input class="form-control" type="text" id="search-startDate"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input class="form-control" type="text" id="search-endDate">
                    </div>
                </div>

                <button type="button" id="searchBtn" class="btn btn-default">查询</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">

                <%--data-toggle="modal"
                        表示触发该按钮，将要打开一个模态窗口
                    data-target="#editActivityModal"
                        表示要打开哪个模态窗口，通过#id的形式找到该窗口
                --%>

                <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span>
                    创建
                </button>
                <button type="button" class="btn btn-default" id="editBtn"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button type="button" class="btn btn-danger" id="deleteBtn"><span
                        class="glyphicon glyphicon-minus"></span> 删除
                </button>
            </div>

        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="qx"/></td>
                    <td>名称</td>
                    <td>所有者</td>
                    <td>开始日期</td>
                    <td>结束日期</td>
                </tr>
                </thead>
                <tbody id="activityBody">
                <%--<tr class="active">
                    <td><input type="checkbox"/></td>
                    <td><a style="text-decoration: none; cursor: pointer;"
                           onclick="window.location.href='detail.jsp';">发传单</a></td>
                    <td>zhangsan</td>
                    <td>2020-10-10</td>
                    <td>2020-10-20</td>
                </tr>
                <tr class="active">
                    <td><input type="checkbox"/></td>
                    <td><a style="text-decoration: none; cursor: pointer;"
                           onclick="window.location.href='detail.jsp';">发传单</a></td>
                    <td>zhangsan</td>
                    <td>2020-10-10</td>
                    <td>2020-10-20</td>
                </tr>--%>
                </tbody>
            </table>
        </div>

        <div style="height: 50px; position: relative;top: 30px;">
            <div id="activityPage"></div>
        </div>

    </div>

</div>
</body>
</html>