<!DOCTYPE html>
<html>
<head lang="en">
    <#include "../common/head.ftl">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/5.0.1/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
</head>

<body class="gray-bg">
<div id="imgaeUpload">
    <input type="hidden" id="imageUrl" value="${imageUrl}">
    <input type="hidden" id="imageType" value="${imageType}">

    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">

                    <div id="title" class="ibox-title">
                        <h5>图片上传</h5>
                    </div>

                    <div class="ibox-content">

                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<!-- the main fileinput plugin file -->
<script src="${basePath}/static/js/plugins/bootstrap-fileinput/fileinput.min.js"></script>
<script src="${basePath}/static/js/backend/imageUpload.js"></script>
</body>

</html>