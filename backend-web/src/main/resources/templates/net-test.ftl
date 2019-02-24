<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>测试网络</title>

    <script src="../static/js/jquery/jquery-1.12.4.js"></script>
    <script src="../static/js/vue/vue-2.6.7.js"></script>
    <script src="../static/js/common/common.js"></script>
    <script src="../static/js/bootstrap/bootstrap.js"></script>
    <link href="../static/css/bootstrap.css" rel="stylesheet">
</head>

<style>
    .col-center-block {
        float: none;
        display: block;
        margin-left: auto;
        margin-right: auto;
    }
</style>

<body>

<div class="container" id="app">
    <div class="row ">
        <div class="col-xs-6 col-md-4 col-center-block">
            <div id="box1" style="margin-top: 10px">
                <input type="button" @click="getForm()" value="点我异步获取数据(getForm)">
            </div>

            <div id="box2" style="margin-top: 10px">
                <input type="button" @click="postForm()" value="点我异步获取数据(postForm)">
            </div>

            <div id="box3" style="margin-top: 10px">
                <input type="button" @click="postJson()" value="点我异步获取数据(postJson)">
            </div>
        </div>
    </div>

</div>



<script src="../static/js/backend/net-test.js"></script>
</body>


</html>