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
            url: base_url + "/registry/enabled",
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
            url: base_url + "/registry/enabled",
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
        url: base_url + "/registry/list",
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
        url: base_url + "/registry/update",
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
        url: base_url + "/registry/update",
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
                "targets": 0,
                "width": "2px",
            },
            {
                "targets": 6,
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
            },
        ],
        "searching": false,
        "processing": true,
        "serverSide": true,
        "ajax": {
            url: base_url + "/registry/list",
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
            data: null,
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