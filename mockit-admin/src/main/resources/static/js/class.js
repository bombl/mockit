function addClass() {
    // 获取选中的服务名
    var serviceNameElement = document.getElementById("selectServiceName");
    var serviceId = serviceNameElement.options[serviceNameElement.selectedIndex].value;

    // 获取输入的类名
    var className = document.getElementById("inputClassName").value;

    // 获取输入的备注
    var remarksValue = $("#remarks").val();

    // 获取输入的备注
    var remarksValue = $("#remarks").val();

    var addEnabledStatusInput = $("#addEnabledStatusInput").value;

    // Check if the "类名" field is empty
    if (className === "") {
        // Show the validation message and add a red border to the input field
        $("#classNameError").text("类名不能为空，请输入类名！"); // Display validation message
        $("#inputClassName").addClass("is-invalid"); // Add a red border to the input field
        return; // Stop the save process
    } else {
        // If the "类名" field is not empty, clear the validation message and remove the red border
        $("#classNameError").text(""); // Clear the validation message
        $("#inputClassName").removeClass("is-invalid"); // Remove the red border from the input field
    }

    // 构造请求参数
    var requestData = {};

    requestData.serviceId = serviceId;
    requestData.className = className;
    requestData.remarks = remarksValue;
    requestData.mockEnabled = addEnabledStatusInput;
    var data = JSON.stringify(requestData);
    
    $.ajax({
        url: base_url + "/class/add",
        type: "post",
        contentType: "application/json",
        data:data,
        success: function(response) {
            $('#selectServiceName').empty();
            $("#inputClassName").val("");
            $("#remarksInput").val("");
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
        url: base_url + "/registry/alias",
        type: "post",
        contentType: "application/json",
        success: function(response) {
            // 清空下拉框选项
            $('#selectServiceName').empty();

            // 填充下拉框选项
            response.forEach(function(item) {
                var option = $('<option></option>').attr('value', item.id).text(item.alias);
                $('#selectServiceName').append(option);
            });
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
    var enabled = $('#enabled').val();
    var searchCondition = {};
    
    if (serviceId) {
        searchCondition.serviceId = serviceId;
    }
    if (className) {
        searchCondition.className = className;
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
            url: base_url + "/class/enabled",
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
            url: base_url + "/class/enabled",
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
        url: base_url + "/class/list",
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
    $('#enabledStatusInput').val(rowData.mockEnabled);
    $('#remarksInput').val(rowData.remarks);

    // 弹出修改弹框
    $('#myModal').modal('show');
}

// 保存记录按钮点击事件处理函数
function saveRecord() {
    var id = $('#recordIdInput').val();
    var remarks = $('#remarksInput').val(); // 获取修改后的备注信息
    var className = $('#classNameInput').val(); // 获取修改后的备注信息
    // 保存记录的逻辑...
    var obj = {};
    obj.id = id;
    obj.remarks = remarks;
    obj.className = className;
    var data = JSON.stringify(obj);

    // 删除记录的逻辑...
    $.ajax({
        url: base_url + "/class/update",
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
        url: base_url + "/class/update",
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
    $.ajax({
        url: base_url + "/registry/alias",
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
        "autoWidth": true,
        "scrollX": true,
        "scrollCollapse": true,
        fixedColumns: {
            left: 2,
            right: 1
        },
        scrollY: true,
        "columnDefs": [
            {
                "targets": "_all", // Apply to all columns
                "className": "text-center" // Center align the content
            },
            {
                "targets": 3, // Index of the "类名" column (zero-based)
                "width": "120px",
                "render": function (data, type, row) {
                    const maxChars = 30;

                    if (data && data.length > maxChars) {
                        const lastIndex = data.lastIndexOf('.');
                        if (lastIndex > maxChars - 3) {
                            const truncatedData = data.substr(lastIndex + 1);
                            return '<span title="' + data + '">' + truncatedData + '</span>';
                        } else {
                            const truncatedData = data.substr(0, maxChars - 3) + '...';
                            return '<span title="' + data + '">' + truncatedData + '</span>';
                        }
                    } else {
                        return data;
                    }
                }
            },
            {
                "targets": 5,
                "width": "60px",
                "render": function (data, type, row) {
                    const maxChars = 20; // Adjust the maximum characters as needed

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
            url: base_url + "/class/list",
            type: "post",
            dataType: "json",
            contentType: "application/json",
            data: function (d) {
                var obj = {};
                
                // obj.serviceId = $("#selectServiceName0").options[serviceNameElement.selectedIndex].value;
                obj.serviceId = $("#selectServiceName0").val();
                obj.className = $("#className").val();
                obj.mockEnabled = $("#mockEnabled").val();
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
            data: null,
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
            {"data": "mockEnabledMc", "orderable": false},
            {"data": "remarks", "orderable": false},
            {"data": "createTime", "orderable": false},
            // {"data": "updateAt", "orderable": false},
            {
                'sTitle': '操作',
                "orderable": false,
                data: null,
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