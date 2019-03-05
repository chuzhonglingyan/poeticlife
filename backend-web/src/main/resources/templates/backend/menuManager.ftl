<!DOCTYPE html>
<html>
<head lang="en">
    <#include "../common/head.ftl">

    <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery-treegrid/0.2.0/css/jquery.treegrid.min.css" rel="stylesheet">
    <link href="${basePath}/static/css/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
</head>

<body class="gray-bg"  >
<div id="operaterList">

<div  class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">

                <div class="ibox-title">
                    <h5>菜单管理</h5>
                </div>

                <div class="ibox-content">

                    <table id="table"></table>

                    <button style="margin-top: 20px" class="btn btn-primary" onclick="test()">选择</button>
                </div>
            </div>
        </div>
    </div>

</div>


</div>


<script src="${basePath}/static/js/jquery/jquery.treegrid.min.js"></script>
<script src="${basePath}/static/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${basePath}/static/js/plugins/bootstrap-table/extensions/bootstrap-table-treegrid.min.js"></script>
<script src="${basePath}/static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${basePath}/static/js/backend/menuManager.js"></script>


</body>

</html>