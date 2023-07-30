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
        #example2 th,
        #example2 td {
            white-space: nowrap;
        }
        .form-group textarea {
            padding-right: 80px;
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
                        <h1 style="margin: 0;font-size: 24px;">数据管理</h1>
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.container-fluid -->
        </div>
        <!-- /.content-header -->
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
                                            <table id="example2" class="table table-striped table-bordered table-hover" style="width:100%">
                                                <thead>
                                                <tr>
                                                    <th><input name="userState" type="checkbox" onclick="checkItem(this)" class="minimal checkbox-toolbar"></th>
                                                    <th>行号</th>
                                                    <th>服务名</th>
                                                    <th>类名</th>
                                                    <th>方法名</th>
                                                    <th>模拟数据</th>
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
                    <div class="form-group" style="position: relative;">
                        <label for="jsonInput">模拟数据</label>
                        <textarea id="mockValueInput" class="form-control" rows="5"></textarea>
                        <span id="classNameError" class="text-danger"></span>
                        <button id="formatButton" class="btn btn-primary" style="position: absolute; top: 33px; right: 0;background-color: #e83e8c;">重新生成</button>
                    </div>
                    <div class="form-group">
                        <label for="remarksInput">备注</label>
                        <textarea id="remarksInput" class="form-control"></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="saveRecord(this)">保存</button>
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
<script src="${request.contextPath}/static/js/data.js"></script>
</body>
</html>
