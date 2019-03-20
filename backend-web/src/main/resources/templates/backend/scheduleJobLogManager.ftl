<!DOCTYPE html>
<html>
<head lang="en">
    <#include "../common/head.ftl">

    <link rel="stylesheet" href="${basePath}/static/css/bootstrap-table/bootstrap-table.min.css">
</head>

<body class="gray-bg">
<div id="scheduleJobLogList">
    <input type="hidden" id="jobId" value="${jobId}">
    <input type="hidden" id="beanName" value="${beanName}">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">

                    <div id="title" class="ibox-title">
                        <h5>定时器日志管理</h5>
                    </div>

                    <div class="ibox-content">

                        <div class="table-responsive" style="margin-top: 10px">
                            <table id="scheduleJobLogTable" class="table table-hover "></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>


<script src="${basePath}/static/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${basePath}/static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${basePath}/static/js/backend/scheduleJobLogList.js"></script>
</body>

</html>