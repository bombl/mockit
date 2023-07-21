<!DOCTYPE html>
<html lang="en">
<head>
    <#import "./common/common.ftl" as netCommon>
    <@netCommon.commonStyle />
    <style>
        .custom-select {
            width: 100%;
        }
        .modal-dialog.custom-dialog {
            max-width: 800px;
        }

        .modal-content.custom-content {
            width: 100%;
        }
        /* Apply ellipsis to the text content */
        #example2 td span {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        #example2 th {
            text-align: center;
        }
        .ellipsis {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
    </style>
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
                        <h1 style="margin: 0;font-size: 24px;">方法管理</h1>
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
                                           data-title="新增" data-width="850" data-height="550" onclick="add()"><i class="fa fa-check"></i>新增</a>
                                    </div>
                                </div>
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
                                <div class="input-group" style="width: 930px;">
                                    <label for="selectServiceName">服务名：</label>
                                    <select class="form-control custom-select" id="selectServiceName0" style="height: 38px;width: 100px;">
                                        <input type="text" name="search" id="className" value="${search!}" class="form-control"
                                               placeholder="请输入类名" style="margin-right: 3px;border-left-width: 2px;width: 180px;">
                                        <input type="text" name="search" id="methodName" value="${search!}" class="form-control"
                                               placeholder="请输入方法名" style="margin-right: 3px;border-left-width: 2px;width: 180px;">
                                        <!-- 启用状态下拉框 -->
                                        <label for="enabledSelect">启用状态：</label>
                                        <select id="enabled" class="form-control" style="margin-right: 3px;">
                                            <option value="">全部</option>
                                            <option value="1">启用</option>
                                            <option value="0">禁用</option>
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
                                                    <th>类名</th>
                                                    <th>方法信息</th>
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
                                <label for="classNameInput">类名</label>
                                <input id="classNameInput" type="text" class="form-control" style="width: 766px;"  readonly>
                            </div>
                            <div class="form-group">
                                <label for="methodNameInput">方法信息</label>
                                <input id="methodNameInput" type="text" class="form-control" style="width: 766px;"  readonly>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="enabledStatusInput">启用状态</label>
                                <select id="enabledStatusInput" class="form-control">
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

    <!-- 新增 -->
    <div class="modal fade" id="addNewModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog custom-dialog" role="document">
            <div class="modal-content custom-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel">新增</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="selectServiceName1">服务名</label>
                                <select class="form-control custom-select" id="selectServiceName" style="height: 38px;">
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="selectClassName">类名</label>
                                <select class="form-control custom-select" id="selectClassName" style="width: 766px;">
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="selectMethodName">方法名</label>
                                <select multiple class="form-control custom-select" id="selectMethodName" style="width: 766px;">
                                </select>
                                <span id="classNameError" class="text-danger"></span>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="addEnabledStatusInput">启用状态</label>
                                <select id="addStatusInput" class="form-control">
                                    <option value="1">启用</option>
                                    <option value="0">禁用</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="remarksInput">备注</label>
                        <textarea id="remarks" class="form-control"></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="addClass()">保存</button>
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
    function updateMethodNameOptions(obj) {
        $.ajax({
            url: '${request.contextPath}/api/methodList',
            type: "post",
            contentType: "application/json",
            data: obj,
            success: function(response) {
                // 清空方法名下拉框选项
                $('#selectMethodName').empty();
                // 填充方法名下拉框选项
                response.data.forEach(function(item) {
                    debugger
                    var modifiedParameters = [];
                    for (var i = 0; i < item.parameters.length; i++) {
                        var parameter = item.parameters[i];
                        modifiedParameters.push(parameter.substring(parameter.lastIndexOf(".") + 1) + " " + toLowerFirstLetter(parameter.substring(parameter.lastIndexOf(".") + 1)));
                    }
                    var obj = {};
                    obj.accessModifier = item.accessModifier;
                    obj.returnType = item.returnType;
                    obj.methodName = item.methodName;
                    obj.parameters = item.parameters.join('-');;

                    var option = $('<option></option>').attr('value', JSON.stringify(obj))
                        .text(item.accessModifier.substring(item.accessModifier.lastIndexOf(".") + 1) + "  "
                            + item.returnType.substring(item.returnType.lastIndexOf(".") + 1) + "  "
                            + item.methodName.substring(item.methodName.lastIndexOf(".") + 1) + "(" + modifiedParameters.toString() + ")");
                    $('#selectMethodName').append(option);
                });
            },
            error: function() {
                // 处理请求错误
                console.log('请求后台接口失败');
            }
        });
    }
    function updateClassNameOptions(serviceId) {
        // 清空下拉框选项
        $('#selectClassName').empty();
        var obj = {};
        obj.serviceId = serviceId;
        var data = JSON.stringify(obj);
        $.ajax({
            url: "${request.contextPath}/class/listClass",
            type: "post",
            contentType: "application/json",
            data: data,
            success: function(response) {
                debugger
                if (response.data.length === 0) {
                    return;
                }
                // 填充下拉框选项
                response.data.forEach(function(item) {
                    var option = $('<option></option>').attr('value', item.id).text(item.className);
                    $('#selectClassName').append(option);
                });

                var serviceName =  $('#selectServiceName option:selected').text();
                var className = $('#selectClassName option:selected').text();
                var obj = {};
                obj.className = className;
                obj.alias = serviceName;
                var data = JSON.stringify(obj);
                updateMethodNameOptions(data);
            },
            error: function() {
                // 处理请求错误
                console.log('请求后台接口失败');
            }
        });
    }
    function updateClassNames() {
        debugger
        // 给服务名下拉框添加事件监听器
        $("#selectServiceName").on('change', function() {
            // 获取选中的服务名
            var serviceId = $("#selectServiceName").options[serviceNameElement.selectedIndex].value;
            updateClassNameOptions(serviceId);
        });
    }

    function addClass() {
        var classId = $("#selectClassName").val();
        // 获取输入的备注
        var remarksValue = $("#remarks").val();

        // 获取输入的备注
        var mockEnabled = $("#addStatusInput").val();

        // 获取选中的方法名
        var methodNameElement = document.getElementById("selectMethodName");
        var mockitMethodList = Array.from(methodNameElement.options)
            .filter(option => option.selected)
            .map(option => {
                var obj = JSON.parse(option.value);
                obj.classId = classId;
                obj.mockEnabled = mockEnabled;
                obj.remarks = remarksValue;
                obj.accessModifer = obj.accessModifier;
                return obj;
            });
        if (mockitMethodList.length === 0) {
            // Show the validation message and add a red border to the input field
            $("#classNameError").text("方法不能为空，请选择方法！"); // Display validation message
            $("#inputClassName").addClass("is-invalid"); // Add a red border to the input field
            return; // Stop the save process
        } else {
            // If the "类名" field is not empty, clear the validation message and remove the red border
            $("#classNameError").text(""); // Clear the validation message
            $("#inputClassName").removeClass("is-invalid"); // Remove the red border from the input field
        }

        debugger

        // 构造请求参数
        var requestData = {
            mockitMethodList: mockitMethodList
        };
        var data = JSON.stringify(requestData);
        debugger
        $.ajax({
            url: "${request.contextPath}/method/saveOrUpdate",
            type: "post",
            contentType: "application/json",
            data:data,
            success: function(response) {
                $("#selectMethodName").val("");
                $("#remarks").val("");
                refreshTableData();
                $('#addNewModal').modal('hide');
            },
            error: function() {
                // 处理请求错误
                console.log('请求后台接口失败');
                $('#addNewModal').modal('hide');
            }
        });
    }
    function add() {
        $('#addNewModal').modal('show');
        $.ajax({
            url: "${request.contextPath}/registry/alias",
            type: "post",
            contentType: "application/json",
            success: function(response) {
                // 清空下拉框选项
                $('#selectServiceName').empty();
                debugger
                if (response.length === 0) {
                    return;
                }
                // 填充下拉框选项
                response.forEach(function(item) {
                    var option = $('<option></option>').attr('value', item.id).text(item.alias);
                    $('#selectServiceName').append(option);
                });
                updateClassNameOptions($('#selectServiceName').val());
            },
            error: function() {
                // 处理请求错误
                console.log('请求后台接口失败');
            }
        });
    }

    function searchTableData(event) {
        event.preventDefault();
        var serviceNameElement = document.getElementById("selectServiceName0");
        var serviceId = serviceNameElement.options[serviceNameElement.selectedIndex].value;
        var className = $('#className').val().trim();
        var methodName = $('#methodName').val().trim();
        var mockEnabled = $('#enabled').val();
        var searchCondition = {};
        debugger
        if (serviceId) {
            searchCondition.serviceId = serviceId;
        }
        if (className) {
            searchCondition.className = className;
        }
        if (methodName) {
            searchCondition.methodName = methodName;
        }
        if (mockEnabled) {
            searchCondition.mockEnabled = mockEnabled;
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
                url: "${request.contextPath}/method/batchEnabled",
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
                url: "${request.contextPath}/method/batchEnabled",
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
            url: "${request.contextPath}/class/list",
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
        $('#classNameInput').val(rowData.className);
        $('#methodNameInput').val(rowData.methodInfo);
        $('#enabledStatusInput').val(rowData.mockEnabled);
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

        var mockitMethodList = [];
        mockitMethodList.push(obj);
        debugger
        var requestData = {
            mockitMethodList: mockitMethodList
        };

        var data = JSON.stringify(requestData);
        // 删除记录的逻辑...
        $.ajax({
            url: "${request.contextPath}/method/saveOrUpdate",
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
        debugger
        var rowData = table.rows($(me).parents('tr')).data()[0]; // 选中行数的数据
        var id = rowData.id;
        var ids = [];
        ids.push(id);
        var obj = {};
        obj.ids = ids;
        var data = JSON.stringify(obj);

        // 删除记录的逻辑...
        $.ajax({
            url: "${request.contextPath}/method/batchDelete",
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

    function toLowerFirstLetter(str) {
        return str.charAt(0).toLowerCase() + str.slice(1);
    }

    $(document).ready(function () {
        // 调用函数来启动功能
        updateClassNames();
        $.ajax({
            url: "${request.contextPath}/registry/alias",
            type: "post",
            contentType: "application/json",
            success: function(response) {
                // 清空下拉框选项
                $('#selectServiceName').empty();

                var option0 = $('<option></option>').attr('value', '').text('全部');
                $('#selectServiceName0').append(option0);
                // 填充下拉框选项
                response.forEach(function(item) {
                    var option = $('<option></option>').attr('value', item.id).text(item.alias);
                    $('#selectServiceName0').append(option);
                });
            },
            error: function() {
                // 处理请求错误
                console.log('请求后台接口失败');
            }
        });
        table = $('#example2').DataTable({
            "columnDefs": [
                {
                    "targets": "_all", // Apply to all columns
                    "className": "text-center" // Center align the content
                },
                {
                    "targets": 0,
                    "width": "4px"
                },
                {
                    "targets": 1, // Index of the "类名" column (zero-based)
                    "width": "40px"
                },
                {
                    "targets": 2, // Index of the "类名" column (zero-based)
                    "width": "60px"
                },
                {
                    "targets": 3, // Index of the "类名" column (zero-based)
                    "width": "60px",
                    "render": function (data, type, row) {
                        const maxChars = 30; // Adjust the maximum characters as needed

                        if (data && data.length > maxChars) {
                            const truncatedData = data.substr(0, maxChars - 3) + '...';
                            return '<span title="' + data + '">' + truncatedData + '</span>';
                        } else {
                            return data;
                        }
                    }
                },
                {
                    "targets": 4, // Index of the "类名" column (zero-based)
                    "width": "80px",
                    "render": function (data, type, row) {
                        const maxChars = 30; // Adjust the maximum characters as needed

                        if (data && data.length > maxChars) {
                            const truncatedData = data.substr(0, maxChars - 3) + '...';
                            return '<span title="' + data + '">' + truncatedData + '</span>';
                        } else {
                            return data;
                        }
                    }
                }
            ],
            "searching": false,
            "processing": true,
            "serverSide": true,
            "ajax": {
                url: "${request.contextPath}/method/list",
                type: "post",
                dataType: "json",
                contentType: "application/json",
                data: function (d) {
                    var obj = {};
                    debugger
                    obj.serviceId = $("#selectServiceName0").val();
                    obj.className = $("#className").val();
                    obj.methodName = $("#methodName").val();
                    obj.mockEnabled = $("#enabled").val();
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
                    return '<div style="text-align: center;"><input name="userState" type="checkbox" class="minimal checkbox-toolbar" data-id="' + id + '" style="width: 20px;"></div>';
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
                        return "<span style='display: inline-block; max-width: 120px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; color: blue;' title='" + data + "'>" + data + "</span>";
                    },
                    'class': 'text-center',
                    "orderable": false
                },
                {"data": "className", "orderable": false},
                {"data": "methodInfo", "orderable": false},
                {"data": "mockEnabledMc", "orderable": false},
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
