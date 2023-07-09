<#macro commonStyle>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Google Font: Source Sans Pro -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${request.contextPath}/static/plugins/fontawesome-free/css/all.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Tempusdominus Bootstrap 4 -->
    <link rel="stylesheet" href="${request.contextPath}/static/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="${request.contextPath}/static/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
    <!-- JQVMap -->
    <link rel="stylesheet" href="${request.contextPath}/static/plugins/jqvmap/jqvmap.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${request.contextPath}/static/dist/css/adminlte.min.css">
    <!-- overlayScrollbars -->
    <link rel="stylesheet" href="${request.contextPath}/static/plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
    <!-- Daterange picker -->
    <link rel="stylesheet" href="${request.contextPath}/static/plugins/daterangepicker/daterangepicker.css">
    <!-- summernote -->
    <link rel="stylesheet" href="${request.contextPath}/static/plugins/summernote/summernote-bs4.min.css">

    <!-- DataTables -->
    <link rel="stylesheet" href="${request.contextPath}/static/plugins/datatables-bs4/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="${request.contextPath}/static/plugins/datatables-responsive/css/responsive.bootstrap4.min.css">
    <link rel="stylesheet" href="${request.contextPath}/static/plugins/datatables-buttons/css/buttons.bootstrap4.min.css">
    <!-- Select2 -->
    <link rel="stylesheet" href="${request.contextPath}/static/plugins/select2/css/select2.min.css">
    <link rel="stylesheet" href="${request.contextPath}/static/plugins/toastr/toastr.min.css">
</#macro>

<#macro commonScript>
    <!-- jQuery -->
    <script src="${request.contextPath}/static/plugins/jquery/jquery.min.js"></script>
    <!-- jQuery UI 1.11.4 -->
    <script src="${request.contextPath}/static/plugins/jquery-ui/jquery-ui.min.js"></script>
    <!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
    <script>
        $.widget.bridge('uibutton', $.ui.button)
    </script>
    <!-- Bootstrap 4 -->
    <script src="${request.contextPath}/static/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
    <!-- ChartJS -->
    <script src="${request.contextPath}/static/plugins/chart.js/Chart.min.js"></script>
    <!-- Sparkline -->
    <script src="${request.contextPath}/static/plugins/sparklines/sparkline.js"></script>
    <!-- JQVMap -->
    <script src="${request.contextPath}/static/plugins/jqvmap/jquery.vmap.min.js"></script>
    <script src="${request.contextPath}/static/plugins/jqvmap/maps/jquery.vmap.usa.js"></script>
    <!-- jQuery Knob Chart -->
    <script src="${request.contextPath}/static/plugins/jquery-knob/jquery.knob.min.js"></script>
    <!-- daterangepicker -->
    <script src="${request.contextPath}/static/plugins/moment/moment.min.js"></script>
    <script src="${request.contextPath}/static/plugins/daterangepicker/daterangepicker.js"></script>
    <!-- Tempusdominus Bootstrap 4 -->
    <script src="${request.contextPath}/static/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
    <!-- Summernote -->
    <script src="${request.contextPath}/static/plugins/summernote/summernote-bs4.min.js"></script>
    <!-- overlayScrollbars -->
    <script src="${request.contextPath}/static/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
    <!-- AdminLTE App -->
    <script src="${request.contextPath}/static/dist/js/adminlte.js"></script>
    <!-- AdminLTE dashboard demo (This is only for demo purposes) -->
    <script src="${request.contextPath}/static/dist/js/pages/dashboard.js"></script>
    <!-- Bootstrap 4 -->
    <script src="${request.contextPath}/static/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
    <!-- DataTables  & Plugins -->
    <script src="${request.contextPath}/static/plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="${request.contextPath}/static/plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
    <script src="${request.contextPath}/static/plugins/datatables-responsive/js/dataTables.responsive.min.js"></script>
    <script src="${request.contextPath}/static/plugins/datatables-responsive/js/responsive.bootstrap4.min.js"></script>
    <script src="${request.contextPath}/static/plugins/datatables-buttons/js/dataTables.buttons.min.js"></script>
    <script src="${request.contextPath}/static/plugins/datatables-buttons/js/buttons.bootstrap4.min.js"></script>
    <script src="${request.contextPath}/static/plugins/jszip/jszip.min.js"></script>
    <script src="${request.contextPath}/static/plugins/datatables-buttons/js/buttons.html5.min.js"></script>
    <script src="${request.contextPath}/static/plugins/datatables-buttons/js/buttons.print.min.js"></script>
    <script src="${request.contextPath}/static/plugins/datatables-buttons/js/buttons.colVis.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/select/1.3.3/js/dataTables.select.min.js"></script>
    <!-- Select2 -->
    <script src="${request.contextPath}/static/plugins/select2/js/select2.full.min.js"></script>
    <script src="${request.contextPath}/static/plugins/toastr/toastr.min.js"></script>
    <!-- Page specific script -->
    <script>
        $(function () {
            // $('#example2').DataTable({
            //     "paging": true,
            //     "searching": false,
            //     "ordering": true,
            //     "info": true,
            //     "autoWidth": false,
            //     "responsive": true,
            //     "language" : {
            //         "sLengthMenu" : "显示条数： _MENU_ " },
            // });
            $('.select2').select2();
        });
    </script>

</#macro>

<#macro commonHeader>
    <!-- Preloader -->
    <div class="preloader flex-column justify-content-center align-items-center">
        <img class="animation__shake" src="${request.contextPath}/static/dist/img/AdminLTELogo.png" alt="AdminLTELogo" height="60" width="60">
    </div>

    <!-- Navbar -->
    <nav class="main-header navbar navbar-expand navbar-white navbar-light" style="background: #3c8dbc">
        <!-- Left navbar links -->
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
            </li>
            <li class="nav-item d-none d-sm-inline-block">
                <a href="${request.contextPath}" class="nav-link">Home</a>
            </li>
        </ul>

        <!-- Right navbar links -->
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" data-widget="fullscreen" href="#" role="button">
                    <i class="fas fa-expand-arrows-alt"></i>
                </a>
            </li>
        </ul>
    </nav>
    <!-- /.navbar -->

</#macro>

<#macro commonLeft pageName >
    <!-- Main Sidebar Container -->
    <aside class="main-sidebar sidebar-dark-primary elevation-4">
        <!-- Brand Logo -->
        <a href="index3.html" class="brand-link">
            <img src="${request.contextPath}/static/dist/img/AdminLTELogo.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3" style="opacity: .8">
            <span class="brand-text font-weight-light">Mockit</span>
        </a>

        <!-- Sidebar -->
        <div class="sidebar">
            <#--            <!-- SidebarSearch Form &ndash;&gt;-->
            <div class="form-inline">
                <div class="input-group" data-widget="sidebar-search">
                    <input class="form-control form-control-sidebar" type="search" placeholder="Search" aria-label="Search">
                    <div class="input-group-append">
                        <button class="btn btn-sidebar">
                            <i class="fas fa-search fa-fw"></i>
                        </button>
                    </div>
                </div>
            </div>

            <!-- Sidebar Menu -->
            <nav class="mt-2">
                <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                    <!-- Add icons to the links using the .nav-icon class
                         with font-awesome or any other icon font library -->
                    <li class="nav-item">
                        <a href="index" class="nav-link">
                            <i class="nav-icon far fa-circle text-danger"></i>
                            <p class="text">运行报表</p>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="service" class="nav-link">
                            <i class="nav-icon far fa-circle text-warning"></i>
                            <p>服务管理</p>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="class" class="nav-link">
                            <i class="nav-icon far fa-circle text-info"></i>
                            <p>服务类管理</p>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="method" class="nav-link">
                            <i class="nav-icon far fa-circle text-success"></i>
                            <p>方法管理</p>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="data" class="nav-link">
                            <i class="nav-icon far fa-circle text-primary"></i>
                            <p>数据管理</p>
                        </a>
                    </li>
                </ul>
            </nav>
            <!-- /.sidebar-menu -->
        </div>
        <!-- /.sidebar -->
    </aside>
</#macro>

<#macro commonFooter >
    <footer class="main-footer">
        <div class="float-right d-none d-sm-inline-block">
            <b>Version</b> 0.0.1
        </div>
        <strong>Copyright &copy; 2014-2023 <a href="https://github.com/bombl/mockit">Mockit</a>.</strong>
    </footer>
</#macro>