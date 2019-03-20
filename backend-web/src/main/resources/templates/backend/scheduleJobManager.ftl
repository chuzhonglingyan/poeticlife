<!DOCTYPE html>
<html>
<head lang="en">
    <#include "../common/head.ftl">

    <link rel="stylesheet" href="${basePath}/static/css/bootstrap-table/bootstrap-table.min.css">
    <link rel="stylesheet" href="${basePath}/static/css/bootstrap-select.min.css">
</head>

<body class="gray-bg">
<div id="scheduleJobList">

    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">

                    <div class="ibox-title">
                        <h5>定时器管理</h5>
                    </div>

                    <div class="ibox-content">

                        <form role="form" class="form-inline">
                            <button class="btn btn-primary" type="button" @click="addScheduleJob()">
                                新增任务
                            </button>

                            <div class="input-group pull-right">
                                <input type="text" placeholder="请输入任务名"
                                       class="input-sm form-control">
                                <span class="input-group-btn">
                                        <button type="button"
                                                class="btn btn-sm btn-primary">查询</button>
                                </span>
                            </div>

                        </form>

                        <div class="table-responsive" style="margin-top: 10px">
                            <table id="scheduleJobTable" class="table table-hover "></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>


    <div class="modal inmodal" id="addScheduleJobModal" tabindex="-1" role="dialog"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span><span class="sr-only">关闭</span>
                    </button>
                    <h5 class="modal-title">新增任务</h5>
                <#--<small class="font-bold">新增任务</small>-->
                </div>
                <small>
                    <div class="modal-body">
                        <form class="form-horizontal " id="addScheduleJobForm">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">组名：</label>

                                <div class="col-sm-8">
                                    <select class="selectpicker" title="请选择" data-style="btn-info" id="groupName">
                                        <option value="test">test</option>
                                        <option value="good">good</option>
                                        <option value="order">order</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">任务类名：</label>
                                <div class="col-sm-8">
                                    <input id="beanName" name="beanName" minlength="2"
                                           maxlength="50" type="text" class="form-control">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">方法名：</label>
                                <div class="col-sm-8">
                                    <input id="methodName" name="methodName" minlength="2"
                                           maxlength="50" type="text" class="form-control">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">时间表达式：</label>
                                <div class="col-sm-8">
                                    <input id="cronExpression" name="cronExpression" type="text"
                                           maxlength="30"
                                           class="form-control">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">参数：</label>
                                <div class="col-sm-8">
                                    <input id="params" name="params" type="text" maxlength="30"
                                           class="form-control">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">是否启动：</label>
                                <div class="col-sm-8">
                                    <label class="checkbox-inline">
                                        <label class="radio-inline">
                                            <input type="radio" name="status" id="statusStart"
                                                   value="1"> 是
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="status" id="statusStop"
                                                   value="0" checked> 否
                                        </label>
                                    </label>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-sm-3 control-label">备注：</label>
                                <div class="col-sm-8">
                                    <input id="remark" name="remark" type="text" maxlength="30"
                                           class="form-control">
                                </div>
                            </div>
                        </form>

                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" @click="saveScheduleJob()">保存
                        </button>
                    </div>

                </small>
            </div>
            <small class="font-bold"></small>
        </div>
        <small class="font-bold"></small>
    </div>


</div>

<!-- jQuery Validation plugin javascript-->
<script src="${basePath}/static/js/plugins/bootstrap-validator/bootstrapValidator.js"></script>
<script src="${basePath}/static/js/plugins/bootstrap-validator/language/zh_CN.js"></script>

<script src="${basePath}/static/js/bootstrap/bootstrap-select.min.js"></script>
<script src="${basePath}/static/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${basePath}/static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${basePath}/static/js/backend/scheduleJobList.js"></script>
</body>

</html>