<!DOCTYPE html>
<html lang="en">
<head>
    <#import "./common/common.ftl" as netCommon>
    <@netCommon.commonStyle />
    <title>Mockit</title>
</head>
<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">

    <!-- header -->
    <@netCommon.commonHeader />
    <!-- left -->
    <@netCommon.commonLeft "jobinfo" />
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <div class="content-header" style="padding-bottom: 5px;">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h1 style="margin: 0;font-size: 24px;">服务管理</h1>
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.container-fluid -->
        </div>
        <!-- /.content-header -->
        <!-- Main content -->

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-12">
                    <div class="col-xs-12">
                        <div class="box">
                            <form id="myForm" method="post" class="form-inline">
                                <div class="box-header">
                                    <div class="input-group">
                                        <a class="btn btn-primary dialog" style="margin-right: 3px;background-color: #3c8dbc;" href="javascript:;" data-url="/system/user/add"
                                           data-title="创建新用户" data-width="850" data-height="550"><i class="fa fa-check"></i>启用</a>
                                    </div>
                                </div>
                                <div class="box-header">
                                    <div class="input-group">
                                        <a class="btn btn-primary dialog" style="margin-right: 3px;background-color: #dd4b39;" href="javascript:;" href="javascript:;" data-url="/system/user/add"
                                           data-title="创建新用户" data-width="850" data-height="550"><i class="fa fa-times"></i>禁用</a>
                                    </div>
                                </div>
                                <div class="input-group">
                                    <input type="text" name="search" value="${search!}" class="form-control"
                                           placeholder="Search">
                                    <div class="input-group-btn">
                                        <button class="btn btn-default" type="submit"><i class="fa fa-search"></i></button>
                                        <a href="/system/user/list/1" class="btn btn-default"><i class="fas fa-sync-alt"></i></a>
                                    </div>
                                </div>
                            </form>
                            <div class="row" style="margin-top: 1px;">
                                <div class="col-12">
                                    <div class="card">
                                        <!-- /.card-header -->
                                        <div class="card-body">
                                            <table id="example2" class="table table-striped table-bordered table-hover">
                                                <thead>
                                                <tr>
                                                    <th><input name="userState" type="checkbox" class="minimal checkbox-toolbar"></th>
                                                    <th>行号</th>
                                                    <th>服务名</th>
                                                    <th>IP地址</th>
                                                    <th>在线状态</th>
                                                    <th>启用状态</th>
                                                    <th>备注</th>
                                                    <th>创建时间</th>
                                                    <th>操作</th>
                                                </tr>
                                                </thead>
                                            </table>
                                        </div>
                                        <!-- /.card-body -->
                                    </div>
                                    <!-- /.card -->
                                </div>
                                <!-- /.col -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.container-fluid -->
        </section>
        <!-- /.content -->
    </div>
    <!-- footer -->
    <@netCommon.commonFooter />

</div>
<!-- ./wrapper -->
<@netCommon.commonScript />
<script>
    var timer = null; //延时搜索，
    var timeout = 1000; // 当在搜索时，只有超过间隔时间（1000）才开始搜索
    var url = "./registry/list" //请求地址
    var delurl = "./demo.php" //请求地址
    var table = null; //表格
    var request = (url, params, method = "POST") => {
        return new Promise((resolve, reject) => {
            $.ajax({
                type: method,
                url: url,
                cache: false, //禁用缓存
                data: params, //传入组装的参数
                dataType: "json",
                contentType: "application/json",
                success: function (result) {
                    debugger
                    resolve(result);
                },
                error: function () {
                    reject('出错')
                }
            });
        })
    }

    // 修改
    function update(me) {
        var row = table.rows($(me).parents('tr')).data()[0]; // 选中行数的数据
        alert('修改：' + JSON.stringify(row))
    }

    // 删除
    async function del(me) {
        var row = table.rows($(me).parents('tr')).data()[0]; // 选中行数的数据
        var {first_name} = row; //这里一般是主键，但是没有传过来的id值，这里就用name替代了
        var param = {first_name, method: 'del'}; //传递的参数，也可以在添加一些判断条件
        var res = await request(delurl, param);
        if (res && res.code == 0) { //判断返回的数据
            $(me).parents('tr').remove();
            alert('删除成功')
        }
    }

    $(document).ready(function () {
        table = $('#example2').DataTable({
            "searching": false,
            "processing": true,
            "serverSide": true,
            "ajax": {
                url: "${request.contextPath}/registry/list",
                type: "post",
                dataType: "json",
                contentType: "application/json",
                data: function (d) {
                    var obj = {};
                    obj.alias = $("#serviceName").val();
                    obj.ip = $("#ip").val();
                    debugger
                    obj.currentPage = d.start;
                    obj.pageSize = d.length;
                    return JSON.stringify(obj);
                }
            },
            "columns": [{
                    orderable: false,
                    className: 'select-checkbox',
                    targets: 0,
                    checkboxes: {
                        selectRow: true
                    },
                    render: function (data, type, row, meta) {
                        return '<input name="userState" type="checkbox" class="minimal checkbox-toolbar">';
                    }
                },
                {
                    targets: 1,
                    data: null,
                    render: function (data, type, row, meta) {
                        return meta.row + 1;
                    }
                },
                {
                "data": "alias",
                "width": '20%',
                'render': function (data, type, row) {
                    return "<span style='color:blue'>" + data + '</span>';
                },
                'class': 'text-center',
                "orderable": false
                },
                {"data": "ip", "orderable": false},
                {"data": "online", "orderable": false},
                {"data": "enabled", "orderable": false},
                {"data": "remarks", "orderable": false},
                {"data": "createAt", "orderable": false},
                // {"data": "updateAt", "orderable": false},
                {
                    'sTitle': '操作',
                    "orderable": false,
                    'render': function (data, type, row) {
                        return `
                            <button type="button" class="btn btn-sm btn-info" onclick="update(this)">修改</button>
                            <button type="button" class="btn btn-sm btn-danger " onclick="del(this)">删除</button>
                                `;
                    },
                }
            ],
            select: {
                style: 'multi',
                selector: 'td:first-child'
            },
            "language": //把文字变为中文
                {
                    // "sProcessing": "加载中...",
                    "sLengthMenu": "显示条数： _MENU_ ",
                    "sZeroRecords": "没有匹配结果",
                    "sInfo": "第 _PAGE_ 页 ( 总共 _PAGES_ 页，_TOTAL_ 条记录 )",
                    "sInfoEmpty": "无记录",
                    "sInfoFiltered": "",
                    "sInfoPostFix": "",
                    "sSearch": "搜索:",
                    "sUrl": "",
                    "sEmptyTable": "表中数据为空",
                    // "sLoadingRecords": "载入中...",
                    "sInfoThousands": ",",
                    "oPaginate": {
                        "sPrevious": "上一页", //上一页
                        "sNext": "下一页", //下一页
                    },
                    select: {
                        rows: {
                            _: "%d 行已选择",
                            0: "",
                            1: "1 行已选择"
                        }
                    }
                },
            'aLengthMenu': [10, 20, 30, 50], //设置每页显示记录的下拉菜单
            'ordering': false
        })
    });
</script>
</body>
</html>
