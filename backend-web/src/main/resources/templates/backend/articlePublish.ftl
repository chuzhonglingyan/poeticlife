<!DOCTYPE html>
<html>
<head lang="en">
    <#include "../common/head.ftl">
    <link href="${basePath}/static/css/plugins/summernote/summernote.css" rel="stylesheet">
    <link href="${basePath}/static/css/plugins/summernote/summernote-bs3.css" rel="stylesheet">
</head>

<body class="gray-bg">
<div id="articleList">
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">


                        <div class="ibox-tools">
                            <a id="edit" class="dropdown-toggle" onclick="edit()">
                                <i class="fa fa-edit"></i>
                            </a>
                        </div>

                        <form class="form-inline" role="form">
                            <div class="form-group">
                                <input type="text" placeholder="请输入文章名" class="input-sm form-control">
                            </div>

                        </form>

                    </div>
                    <div class="ibox-content" id="eg">

                        <div class="click2edit wrapper">
                            <h3>你好，子涵 </h3>
                            <p>
                                H+是一个完全响应式，基于Bootstrap3.3.6最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术，她提供了诸多的强大的可以重新组合的UI组件，并集成了最新的jQuery版本(v2.1.1)，当然，也集成了很多功能强大，用途广泛的就jQuery插件，她可以用于所有的Web应用程序，如<b>网站管理后台</b>，<b>网站会员中心</b>，<b>CMS</b>，<b>CRM</b>，<b>OA</b>等等，当然，您也可以对她进行深度定制，以做出更强系统。
                            </p>
                            <p>
                                <b>当前版本：</b>v4.1.0
                            </p>
                            <p>
                                <b>定价：</b><span class="label label-warning">¥988（不开发票）</span>
                            </p>
                        </div>

                    </div>

                    <div class="ibox-content " style="margin-top: 1px">

                            <button id="save" class="btn btn-white  btn-sm" onclick="save()"
                                    type="button">保存草稿
                            </button>

                            <button id="publish" style="margin-left:10px "
                                    class="btn btn-primary  btn-sm " onclick="save()"
                                    type="button">发布文章
                            </button>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- SUMMERNOTE -->
<script src="${basePath}/static/js/plugins/summernote/summernote.min.js"></script>
<script src="${basePath}/static/js/plugins/summernote/summernote-zh-CN.js"></script>

<script>
    var editFlag;
    $(document).ready(function () {
        $("#eg").addClass("no-padding");
        $('.click2edit').summernote({
            lang: 'zh-CN',
            focus: true
        });
        editFlag=true;
    });

    var edit = function () {
        if (editFlag) {
            $("#eg").removeClass("no-padding");
            //save HTML If you need(aHTML: array).
            var aHTML = $('.click2edit').code();
            $('.click2edit').destroy();

            editFlag=false;
        }else {
            $("#eg").addClass("no-padding");
            $('.click2edit').summernote({
                lang: 'zh-CN',
                focus: true
            });
            editFlag=true;
        }
    };

</script>

</body>

</html>