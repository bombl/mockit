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
                                           data-title="启用" data-width="850" data-height="550" onclick="enableAll()"><i class="fa fa-check"></i>启用</a>
                                    </div>
                                </div>
                                <div class="box-header">
                                    <div class="input-group">
                                        <a class="btn btn-primary dialog" style="margin-right: 3px;background-color: #dd4b39;" href="javascript:;" href="javascript:;" data-url="/system/user/add"
                                           data-title="禁用" data-width="850" data-height="550" onclick="disableAll()"><i class="fa fa-times"></i>禁用</a>
                                    </div>
                                </div>
                                <div class="input-group">
                                    <input type="text" name="search" id="alias" value="${search!}" class="form-control"
                                           placeholder="请输入服务名" style="margin-right: 3px;">
                                    <input type="text" name="search" id="ip" value="${search!}" class="form-control"
                                           placeholder="请输入IP地址" style="margin-right: 3px;">
                                    <!-- 启用状态下拉框 -->
                                    <label for="enabledSelect">启用状态：</label>
                                    <select id="online" class="form-control" style="margin-right: 3px;">
                                        <option value="">全部</option>
                                        <option value="1">启用</option>
                                        <option value="0">禁用</option>
                                    </select>

                                    <!-- 在线状态下拉框 -->
                                    <label for="enabled">在线状态：</label>
                                    <select id="enabled" class="form-control" style="margin-right: 3px;">
                                        <option value="">全部</option>
                                        <option value="1">在线</option>
                                        <option value="0">离线</option>
                                    </select>
                                    <div class="input-group-btn">
                                        <button class="btn btn-default" onclick="searchTableData(event)"><i class="fa fa-search"></i></button>
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
    <!-- 修改弹框 -->
    <div id="myModal" class="modal fade">
        <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">修改</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="serviceNameInput">服务名</label>
                                <input id="serviceNameInput" type="text" class="form-control" readonly>
                            </div>
                            <div class="form-group">
                                <label for="ipAddressInput">IP地址</label>
                                <input id="ipAddressInput" type="text" class="form-control" readonly>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="onlineStatusInput">在线状态</label>
                                <select id="onlineStatusInput" class="form-control" disabled>
                                    <option value="1">在线</option>
                                    <option value="0">离线</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="enabledStatusInput">启用状态</label>
                                <select id="enabledStatusInput" class="form-control" disabled>
                                    <option value="1">启用</option>
                                    <option value="0">禁用</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="remarksInput">备注</label>
                        <textarea id="remarksInput" class="form-control"></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="saveRecord()">保存</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>



    <!-- 删除确认框 -->
    <div id="confirmDeleteModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">确认删除</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="recordIdInput" value="">
                    <p>确定要删除该记录吗？</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" onclick="deleteRecord()">删除</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>
    <!-- footer -->
    <@netCommon.commonFooter />

</div>
<!-- ./wrapper -->
<@netCommon.commonScript />
<script>

    function searchTableData(event) {
        event.preventDefault();
        var alias = $('#alias').val().trim();
        var ip = $('#ip').val();
        var online = $('#online').val();
        var enabled = $('#enabled').val();
        var searchCondition = {};

        if (alias) {
            searchCondition.alias = alias;
        }
        if (ip) {
            searchCondition.ip = ip;
        }
        if (online) {
            searchCondition.online = online;
        }
        if (enabled) {
            searchCondition.enabled = enabled;
        }
        table.search(JSON.stringify(searchCondition)).draw();
    }
    // 批量启用
    function enableAll() {
        var ids = [];

        table.rows().every(function () {
            var rowData = this.data();
            var checkbox = this.nodes().to$().find('input[name="userState"]');
            if (checkbox.prop('checked')) {
                var selectedId = rowData.id; // Assuming the ID field name is "id"
                ids.push(selectedId);
            }
        });
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
            url: "${request.contextPath}/registry/list",
            type: "POST",
            success: function (data) {
                $('input[name="userState"]').prop('checked', false);
                table.rows.add(data).draw();
                toastr.success("操作成功");
            },
            error: function (xhr, status, error) {
                // toastr.success("操作失败");
            }
        });
    }

    // 修改按钮点击事件处理函数
    function update(me) {
        var rowData = table.rows($(me).parents('tr')).data()[0]; // 选中行数的数据
        var id = rowData.id;
        $('#recordIdInput').val(id);
        // 将行数据填充到弹框的表单控件中
        $('#serviceNameInput').val(rowData.alias);
        $('#ipAddressInput').val(rowData.ip);
        $('#onlineStatusInput').val(rowData.online);
        $('#enabledStatusInput').val(rowData.enabled);
        $('#remarksInput').val(rowData.remarks);

        // 弹出修改弹框
        $('#myModal').modal('show');
    }

    // 保存记录按钮点击事件处理函数
    function saveRecord() {
        var id = $('#recordIdInput').val();
        var remarks = $('#remarksInput').val(); // 获取修改后的备注信息
        // 保存记录的逻辑...
        var obj = {};
        obj.id = id;
        obj.remarks = remarks;
        var data = JSON.stringify(obj);

        // 删除记录的逻辑...
        $.ajax({
            url: "${request.contextPath}/registry/update",
            type: "post",
            data: data,
            contentType: "application/json",
            success: function (data) {
                refreshTableData();
            },
            error: function (xhr, status, error) {
                // toastr.success("操作失败");
            }
        });
        // 关闭修改弹框
        $('#myModal').modal('hide');

        // 刷新表格数据
        refreshTableData();
    }

    // 删除按钮点击事件处理函数
    function del(me) {
        Swal.fire({
            title: '确认删除',
            text: '您确定要删除该记录吗？',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6',
            confirmButtonText: '确认',
            cancelButtonText: '取消'
        }).then((result) => {
            if (result.isConfirmed) {
                // 发送删除请求的代码
                deleteRecord(me);
            }
        });
    }


    // 执行删除操作的逻辑
    function deleteRecord(me) {
        var rowData = table.rows($(me).parents('tr')).data()[0]; // 选中行数的数据
        var id = rowData.id;

        var obj = {};
        obj.id = id;
        obj.deleted = 1;
        var data = JSON.stringify(obj);

        // 删除记录的逻辑...
        $.ajax({
            url: "${request.contextPath}/registry/update",
            type: "post",
            data: data,
            contentType: "application/json",
            success: function (data) {
                refreshTableData();
            },
            error: function (xhr, status, error) {
                toastr.success("操作失败");
            }
        });
        // 关闭删除确认框
        $('#confirmDeleteModal').modal('hide');

        // 刷新表格数据
        refreshTableData();
    }

    // 删除
    // async function del(me) {
    //     var row = table.rows($(me).parents('tr')).data()[0]; // 选中行数的数据
    //     var {first_name} = row; //这里一般是主键，但是没有传过来的id值，这里就用name替代了
    //     var param = {first_name, method: 'del'}; //传递的参数，也可以在添加一些判断条件
    //     $(me).parents('tr').remove();
    //     alert('删除成功')
    // }

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
            "columnDefs": [
                {
                    "targets": "_all", // Apply to all columns
                    "className": "text-center" // Center align the content
                },
                {
                    "targets": 6,
                    "width": "35px",
                    "render": function (data, type, row) {
                        const maxChars = 10; // Adjust the maximum characters as needed

                        if (data && data.length > maxChars) {
                            const truncatedData = data.substr(0, maxChars - 3) + '...';
                            return '<span title="' + data + '">' + truncatedData + '</span>';
                        } else {
                            return data;
                        }
                    }
                },
            ],
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
                    obj.alias = $("#alias").val();
                    obj.ip = $("#ip").val();
                    obj.online = $("#online").val();
                    obj.enabled = $("#enabled").val();
                    obj.currentPage = d.start;
                    obj.pageSize = d.length;
                    return JSON.stringify(obj);
                }
            },
            "initComplete": function (settings, json) {
                // Toastr初始化
                toastr.options = {
                    closeButton: true,
                    progressBar: false,
                    preventDuplicates: true,
                    positionClass: "toast-top-center",
                    timeOut: 1000
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
                // "width": '20%',
                'render': function (data, type, row) {
                    return "<span style='color:blue'>" + data + '</span>';
                },
                'class': 'text-center',
                "orderable": false
                },
                {"data": "ip", "orderable": false},
                {"data": "onlineMc", "orderable": false},
                {"data": "enabledMc", "orderable": false},
                {"data": "remarks", "orderable": false},
                {"data": "createTime", "orderable": false},
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
