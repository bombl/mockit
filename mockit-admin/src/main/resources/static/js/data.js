function zkgd() {
    const modalContent = document.getElementById('modal-content');
    const mockValueInput = document.getElementById('mockValueInput');
    const mjsj = document.getElementById('mjsj');
    const isExpanded = mockValueInput.style.height === '430px';
    mockValueInput.style.height = isExpanded ? '172px' : '430px';
    if (isExpanded) {
        mjsj.style.height = '172px';
    } else {
        mjsj.style.height = '430px';
    }
    const expandIcon = document.getElementById("expandIcon");
    if (expandIcon.classList.contains("fa-chevron-down")) {
        expandIcon.classList.remove("fa-chevron-down");
        expandIcon.classList.add("fa-chevron-up");
    } else {
        expandIcon.classList.remove("fa-chevron-up");
        expandIcon.classList.add("fa-chevron-down");
    }
    modalContent.style.height = '798px';
}
function updateMethodNameOptions(obj) {
    $.ajax({
        url: '${request.contextPath}/method/listMethod',
        type: "post",
        contentType: "application/json",
        data: obj,
        success: function(response) {
            // 清空方法名下拉框选项
            $('#selectMethodName').empty();
            // 填充方法名下拉框选项
            response.data.forEach(function(item) {
                
                var modifiedParameters = [];
                for (var i = 0; i < item.parameterList.length; i++) {
                    var parameter = item.parameterList[i];
                    modifiedParameters.push(parameter.substring(parameter.lastIndexOf(".") + 1) + " " + toLowerFirstLetter(parameter.substring(parameter.lastIndexOf(".") + 1)));
                }
                var obj = {};
                obj.accessModifier = item.accessModifer;
                obj.returnType = item.returnType;
                obj.methodName = item.methodName;
                obj.parameters = item.parameterList.join('-');;

                var option = $('<option></option>').attr('value', item.id)
                    .text(item.accessModifer.substring(item.accessModifer.lastIndexOf(".") + 1) + "  "
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
        url: base_url + "/class/listClass",
        type: "post",
        contentType: "application/json",
        data: data,
        success: function(response) {
            
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
            var classId = $("#selectClassName").val();
            var obj = {};
            obj.classId = classId;
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
    
    // 给服务名下拉框添加事件监听器
    $("#selectServiceName").on('change', function() {
        // 获取选中的服务名
        var serviceId = $("#selectServiceName").options[serviceNameElement.selectedIndex].value;
        updateClassNameOptions(serviceId);
    });
}

function addClass() {
    var methodId = $("#selectMethodName").val();
    // 获取输入的备注
    var remarksValue = $("#remarks").val();
    var mockValue = $("#mockValue").val();
    // 获取输入的备注
    var mockEnabled = $("#addStatusInput").val();

    

    // 构造请求参数
    var requestData = {
        methodId: methodId,
        mockValue: mockValue,
        mockEnabled: mockEnabled,
        remarks: remarksValue
    };
    var data = JSON.stringify(requestData);
    
    $.ajax({
        url: base_url + "/mock/saveOrUpdate",
        type: "post",
        contentType: "application/json",
        data:data,
        success: function(response) {
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
    $("#mockValue").val("");
    $("#remarks").val("");
    $.ajax({
        url: base_url + "/registry/alias",
        type: "post",
        contentType: "application/json",
        success: function(response) {
            // 清空下拉框选项
            $('#selectServiceName').empty();
            
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
    
    searchCondition.isMockData = true;
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
            url: base_url + "/mock/batchEnabled",
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
            url: base_url + "/mock/batchEnabled",
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
    $('#methodNameInput').val(rowData.methodInfo);
    $('#enabledStatusInput').val(rowData.mockEnabled);
    $('#remarksInput').val(rowData.remarks);
    const formattedJSON = JSON.stringify(JSON.parse(rowData.mockValue), null, 2);
    // Update the textarea with the formatted JSON
    $('#mockValueInput').val(formattedJSON);
    // 弹出修改弹框
    $('#myModal').modal('show');
}

// 保存记录按钮点击事件处理函数
function saveRecord() {
    var id = $('#recordIdInput').val();
    var remarks = $('#remarksInput').val(); // 获取修改后的备注信息
    var mockValue = $('#mockValueInput').val();
    var mockEnabled = $('#enabledStatusInput').val();
    // 保存记录的逻辑...
    var obj = {};
    obj.methodId = id;
    obj.remarks = remarks;
    obj.mockValue = mockValue;
    obj.mockEnabled = mockEnabled;
    var data = JSON.stringify(obj);
    // 删除记录的逻辑...
    $.ajax({
        url: base_url + "/mock/saveOrUpdate",
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
    var ids = [];
    ids.push(id);
    var obj = {};
    obj.ids = ids;
    var data = JSON.stringify(obj);

    // 删除记录的逻辑...
    $.ajax({
        url: base_url + "/mock/batchDelete",
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
    $('#formatButton').click(function () {
        var serviceName =  $('#serviceNameInput').val();
        var className = $('#selectClassName option:selected').text();
        var id = $('#recordIdInput').val();
        var obj = {};
        obj.methodId = id;
        obj.alias = serviceName;
        
        var data = JSON.stringify(obj);
        $.ajax({
            url: base_url + "/api/generateData",
            type: "post",
            contentType: "application/json",
            data: data,
            success: function(response) {
                try {
                    // Parse the JSON and format it with proper indentation
                    const formattedJSON = JSON.stringify(JSON.parse(response.data), null, 2);
                    
                    // Update the textarea with the formatted JSON
                    $('#mockValueInput').val(formattedJSON);
                } catch (error) {
                    alert(error)
                }
            },
            error: function() {
                // 处理请求错误
                console.log('请求后台接口失败');
            }
        });
    });
    // 调用函数来启动功能
    updateClassNames();
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
                "targets": "_all",
                "className": "text-center"
            },
            {
                "targets": 3,
                "width": "60px",
                "render": function (data, type, row) {
                    const maxChars = 30;

                    if (data && data.length > maxChars) {
                        const lastIndex = data.lastIndexOf('.');
                        if (lastIndex > maxChars - 3) {
                            const truncatedData = data.substr(lastIndex + 1);
                            return '<span class="tooltip-icon" title="' + data + '">' + truncatedData + '</span>';
                        } else {
                            const truncatedData = data.substr(0, maxChars - 3) + '...';
                            return '<span class="tooltip-icon" title="' + data + '">' + truncatedData + '</span>';
                        }
                    } else {
                        return data;
                    }
                }
            },
            {
                "targets": 4,
                "width": "80px",
                "render": function (data, type, row) {
                    const endIndex = data.lastIndexOf("(");
                    const startIndex = data.lastIndexOf(" ", endIndex);
                    if (startIndex !== -1 && endIndex !== -1 && endIndex > startIndex) {
                        const methodName = data.substring(startIndex + 1, endIndex);
                        return '<span title="' + data + '">' + methodName + '</span>';
                    } else {
                        return data;
                    }
                }
            },
            {
                "targets": 6,
                "width": "60px",
                "render": function (data, type, row) {
                    const maxChars = 20;

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
            url: base_url + "/method/list",
            type: "post",
            dataType: "json",
            contentType: "application/json",
            data: function (d) {
                var obj = {};
                
                obj.serviceId = $("#selectServiceName0").val();
                obj.className = $("#className").val();
                obj.methodName = $("#methodName").val();
                obj.mockEnabled = $("#enabled").val();
                obj.isMockData = true;
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
            data: "id",
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
            {"data": "mockValue", "className": 'text-left', "orderable": false},
            {"data": "mockEnabledMc", "orderable": false},
            {"data": "remarks", "orderable": false},
            {"data": "createTime", "orderable": false},
            // {"data": "updateAt", "orderable": false},
            {
                'sTitle': '操作',
                "orderable": false,
                data: "id",
                'render': function (data, type, row) {
                    return `
                            <button type="button" class="btn btn-sm btn-info" onclick="update(this)">模拟数据</button>
                            <button type="button" class="btn btn-sm btn-danger " onclick="del(this)">删除</button>
                                `;
                },
            }
        ],
        "createdRow": function(row, data, dataIndex) {
            // 将methodId的值作为data-method-id属性存储在每个表格行的HTML元素上
            $(row).attr("data-method-id", data.id);
        },
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