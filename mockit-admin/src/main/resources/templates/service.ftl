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
                                           data-title="创建新用户" data-width="850" data-height="550" onclick="enableAll()"><i class="fa fa-check"></i>启用</a>
                                    </div>
                                </div>
                                <div class="box-header">
                                    <div class="input-group">
                                        <a class="btn btn-primary dialog" style="margin-right: 3px;background-color: #dd4b39;" href="javascript:;" href="javascript:;" data-url="/system/user/add"
                                           data-title="创建新用户" data-width="850" data-height="550" onclick="disableAll()"><i class="fa fa-times"></i>禁用</a>
                                    </div>
                                </div>
                                <div class="input-group">
                                    <input type="text" name="search" value="${search!}" class="form-control"
                                           placeholder="Search">
                                    <div class="input-group-btn">
                                        <button class="btn btn-default" type="submit"><i class="fa fa-search"></i></button>
                                        <a class="btn btn-default" onclick="refreshTableData()"><i class="fas fa-sync-alt"></i></a>
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
                                                    <th><input name="userState" type="checkbox" onclick="checkItem(this)" class="minimal checkbox-toolbar"></th>
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
    // 批量启用
    function enableAll() {
        debugger
        var ids = [];

        table.rows().every(function () {
            var rowData = this.data();
            var checkbox = this.nodes().to$().find('input[name="userState"]');
            if (checkbox.prop('checked')) {
                var selectedId = rowData.id; // Assuming the ID field name is "id"
                ids.push(selectedId);
            }
        });

        // Perform the batch enable action using the serviceNames array
        console.log('Enable:', ids);

        if (ids.length > 0) {
            var obj = {};
            obj.ids = ids;
            obj.enabled = true;
            var data = JSON.stringify(obj);
            $.ajax({
                url: "${request.contextPath}/registry/enabled",
                type: "post",
                data: data,
                contentType: "application/json",
                success: function (response) {
                    refreshTableData();
                    toastr.success("操作成功");
                },
                error: function (xhr, status, error) {
                    toastr.error("操作失败");
                }
            });
        }
    }

    // 批量禁用
    function disableAll() {
        var ids = [];

        table.rows().every(function () {
            var rowData = this.data();
            var checkbox = this.nodes().to$().find('input[name="userState"]');
            if (checkbox.prop('checked')) {
                var selectedId = rowData.id; // Assuming the ID field name is "id"
                ids.push(selectedId);
            }
        });

        // Perform the batch enable action using the serviceNames array
        console.log('Enable:', ids);

        if (ids.length > 0) {
            var obj = {};
            obj.ids = ids;
            obj.enabled = false;
            var data = JSON.stringify(obj);
            $.ajax({
                url: "${request.contextPath}/registry/enabled",
                type: "post",
                data: data,
                contentType: "application/json",
                success: function (response) {
                    refreshTableData();
                    toastr.success("操作成功");
                },
                error: function (xhr, status, error) {
                    toastr.error("操作失败");
                }
            });
        }
    }

    // 刷新表格数据
    function refreshTableData() {
        var table = $('#example2').DataTable();

        // Clear existing table data
        table.clear().draw();

        // Fetch updated data from the server
        $.ajax({
            url: "${request.contextPath}/registry/list", // Replace with the actual URL to fetch updated data
            type: "POST",
            success: function (data) {
                $('input[name="userState"]').prop('checked', false);
                table.rows.add(data).draw();
            },
            error: function (xhr, status, error) {
                // Handle any errors that occur during the request
                console.error("Failed to fetch updated data");
                console.error(xhr.responseText);
            }
        });
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
        $(me).parents('tr').remove();
        alert('删除成功')
    }

    function checkItem(checkbox){
        var isChecked = checkbox.checked;
        var checkboxes = document.querySelectorAll('input[name="userState"]');

        checkboxes.forEach(function(item) {
            if (isChecked) {
                item.checked = true;
            } else {
                item.checked = false;
            }
        });
    };

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
                    obj.currentPage = d.start;
                    obj.pageSize = d.length;
                    return JSON.stringify(obj);
                }
            },
            "initComplete": function (settings, json) {
                // Toastr初始化
                toastr.options = {
                    closeButton: true,
                    progressBar: true,
                    positionClass: "toast-top-right",
                    timeOut: 3000
                };
            },
            "columns": [{
                    orderable: false,
                    className: 'select-checkbox',
                    targets: 0,
                    checkboxes: {
                        selectRow: true
                    },
                    render: function (data, type, row, meta) {
                        var id = row.id;
                        return '<input name="userState" type="checkbox" class="minimal checkbox-toolbar" data-id="' + id + '">';
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
