<!DOCTYPE html>
<html>
<head lang="en">
    <#include "../common/head.ftl">
    <link rel="stylesheet" href="${basePath}/static/css/bootstrap-table/bootstrap-table.min.css">
</head>

<body class="gray-bg">
<div id="opreaterImageList">

    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">

                    <div class="ibox-title">
                        <h5>用户图片管理</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                                <li><a href="">选项1</a>
                                </li>
                                <li><a href="">选项2</a>
                                </li>
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>

                    <div class="ibox-content">

                        <form role="form" class="form-inline">
                        <button class="btn btn-primary" type="button"
                                @click="addOpreaterImage()">新增用户图片</button>

                        <div class="input-group pull-right">
                            <input type="text" placeholder="请输入用户图片名"
                                   class="input-sm form-control">
                            <span class="input-group-btn">
                                        <button type="button"
                                                class="btn btn-sm btn-primary">查询</button>
                                </span>
                        </div>

                        </form>

                        <div class="table-responsive" style="margin-top: 10px">
                            <table id="opreaterImageTable"
                                   class="table table-hover "></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>


    <div class="modal inmodal" id="addOpreaterImageModal"
         tabindex="-1" OpreaterImage="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span><span class="sr-only">关闭</span>
                </button>
                <h5 class="modal-title">新增用户图片</h5>
            </div>
            <small>
                <div class="modal-body">
                    <form class="form-horizontal " id="addOpreaterImageForm">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">用户图片名：</label>
                            <div class="col-sm-8">
                                <input id="OpreaterImageName"
                                       name="OpreaterImageName" minlength="2" type="text"
                                       maxlength="30" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注：</label>
                            <div class="col-sm-8">
                                <input id="remark" name="remark" type="text" maxlength="100"
                                       class="form-control">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否启用：</label>
                            <div class="col-sm-8">
                                <label class="checkbox-inline">
                                    <label class="radio-inline">
                                        <input type="radio" name="OpreaterImageStatus"
                                               id="OpreaterImageStart" value="1" checked> 是
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="OpreaterImageStatus"
                                               id="OpreaterImageStop" value="0"> 否
                                    </label>
                                </label>
                            </div>
                        </div>

                    </form>

                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary"
                            @click="saveOpreaterImage()">保存
                    </button>
                </div>

            </small>
        </div>
        <small class="font-bold"></small>
    </div>
    <small class="font-bold"></small>
</div>

</div>


<!-- jQuery Validation plugin javascript&ndash  -->
<script src="${basePath}/static/js/plugins/bootstrap-validator/bootstrapValidator.js"></script>
<script src="${basePath}/static/js/plugins/bootstrap-validator/language/zh_CN.js"></script>
<script src="${basePath}/static/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${basePath}/static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${basePath}/static/js/backend/opreaterImageList.js"></script>


</body>

</html>