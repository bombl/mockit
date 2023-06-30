<!DOCTYPE html>
<html lang="en">
<head>
    <#import "./common/common.ftl" as netCommon>
    <@netCommon.commonStyle />
    <!-- daterangepicker -->
    <#--    <link rel="stylesheet" href="${request.contextPath}/static/adminlte/bower_components/bootstrap-daterangepicker/daterangepicker.css">-->
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
        <div class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h1 class="m-0">运行报表</h1>
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.container-fluid -->
        </div>
        <!-- /.content-header -->

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-2">
                    <div class="input-group">
                        <span class="input-group-addon">执行期</span>
                        <select class="form-control" id="jobGroup"  paramVal="啊啊啊啊" >
                            <option value="0" >全部</option>  <#-- 仅管理员支持查询全部；普通用户仅支持查询有权限的 jobGroup -->
                            <#--                                <#list JobGroupList as group>-->
                            <#--                                    <option value="${group.id}" >${group.title}</option>-->
                            <#--                                </#list>-->
                        </select>
                    </div>
                </div>
                <div class="col-xs-2">
                    <div class="input-group">
                        <span class="input-group-addon">任务</span>
                        <select class="form-control" id="jobId" paramVal="啊啊啊" >
                            <option value="0" >全部</option>
                        </select>
                    </div>
                </div>

                <div class="col-xs-2">
                    <div class="input-group">
                        <span class="input-group-addon">状态</span>
                        <select class="form-control" id="logStatus" >
                            <option value="-1" >全部</option>
                            <option value="1" >成功</option>
                            <option value="2" >失败</option>
                            <option value="3" >进行中</option>
                        </select>
                    </div>
                </div>

                <div class="col-xs-4">
                    <div class="input-group">
                		<span class="input-group-addon">
	                  		调度时间
	                	</span>
                        <input type="text" class="form-control" id="filterTime" readonly >
                    </div>
                </div>

                <div class="col-xs-1">
                    <button class="btn btn-block btn-info" id="searchBtn">搜索</button>
                </div>

                <div class="col-xs-1">
                    <button class="btn btn-block btn-default" id="clearLog">清理</button>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <#--<div class="box-header hide"><h3 class="box-title">调度日志</h3></div>-->
                        <div class="box-body">
                            <table id="joblog_list" class="table table-bordered table-striped display" width="100%" >
                                <thead>
                                <tr>
                                    <th name="jobId" >111</th>
                                    <th name="jobGroup" >jobGroup</th>
                                    <#--<th name="executorAddress" >执行器地址</th>
                                    <th name="glueType" >运行模式</th>
                                      <th name="executorParam" >任务参数</th>-->
                                    <th name="triggerTime" >111</th>
                                    <th name="triggerCode" >222</th>
                                    <th name="triggerMsg" >333</th>
                                    <th name="handleTime" >444</th>
                                    <th name="handleCode" >555</th>
                                    <th name="handleMsg" >666</th>
                                    <th name="handleMsg" >777</th>
                                </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- /.content -->
    </div>
    <!-- footer -->
    <@netCommon.commonFooter />

</div>
<!-- ./wrapper -->
<@netCommon.commonScript />
</body>
</html>
