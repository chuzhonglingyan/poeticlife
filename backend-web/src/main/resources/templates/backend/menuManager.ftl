<!DOCTYPE html>
<html>
<head lang="en">
    <#include "../common/head.ftl">

    <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery-treegrid/0.2.0/css/jquery.treegrid.min.css"
          rel="stylesheet">
    <link href="${basePath}/static/css/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
</head>

<body class="gray-bg">
<div id="operaterList">

    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">

                    <div class="ibox-title">
                        <h5>菜单管理</h5>
                    </div>

                    <div class="ibox-content">

                        <form role="form" class="form-inline">
                            <button class="btn btn-primary" type="button" id="addFirstMenu">新增导航</button>

                            <div class="input-group pull-right">
                                <input type="text" placeholder="请输入菜单名"
                                       class="input-sm form-control">
                                <span class="input-group-btn">
                                        <button type="button"
                                                class="btn btn-sm btn-primary">查询</button>
                                </span>
                            </div>
                        </form>

                        <table id="table"></table>

                        <button style="margin-top: 20px" class="btn btn-primary" onclick="test()">
                            选择
                        </button>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <div class="modal inmodal" id="addSysMenu" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog " style="width: 500px">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span><span class="sr-only">关闭</span>
                    </button>
                    <h5 class="modal-title">新增菜单</h5>
                <#--<small class="font-bold">这里可以显示副标题。</small>-->
                </div>
                <small>
                    <div class="modal-body">
                        <form class="form-horizontal " id="addMenuForm">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">菜单名：</label>
                                <div class="col-sm-8">
                                    <input id="menuName" name="menuName" minlength="2" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">菜单值：</label>
                                <div class="col-sm-8">
                                    <input id="menuValue" name="menuValue"  maxlength="50"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">菜单权限：</label>
                                <div class="col-sm-8">
                                    <input id="menuCode" name="menuCode" type="text" maxlength="50"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group" id="menuTypeFrom">
                                <label class="col-sm-3 control-label">是否为菜单：</label>
                                <div class="col-sm-8">

                                    <label class="checkbox-inline">
                                    <label class="radio-inline">
                                        <input type="radio" name="menuType" id="isMenu" value="1" checked> 是
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="menuType" id="isNotMenu"  value="2"> 否
                                    </label>
                                    </label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">是否启用：</label>
                                <div class="col-sm-8">
                                    <label class="checkbox-inline">
                                    <label class="radio-inline">
                                        <input type="radio" name="menuStatus" id="menuStart" value="1" checked> 是
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="menuStatus" id="menuStop"  value="0"> 否
                                    </label>
                                    </label>
                                </div>
                            </div>
                        </form>

                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" id="saveMenu">保存</button>
                    </div>

                </small>
            </div>
            <small class="font-bold"></small>
        </div>
        <small class="font-bold"></small>
    </div>


</div>


<script src="${basePath}/static/js/jquery/jquery.treegrid.min.js"></script>
<script src="${basePath}/static/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${basePath}/static/js/plugins/bootstrap-table/extensions/bootstrap-table-treegrid.min.js"></script>
<script src="${basePath}/static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${basePath}/static/js/backend/menuManager.js"></script>


</body>

</html>