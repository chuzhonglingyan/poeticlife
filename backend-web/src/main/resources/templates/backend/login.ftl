<!DOCTYPE html>
<html>
<head>

    <title>登录界面</title>
    <#include "../common/head.ftl">
    <link href="${basePath}/static/css/plugins/iCheck/custom.css" rel="stylesheet">
</head>

<body class="gray-bg">
<div id="app" class="middle-box text-center loginscreen  animated fadeInDown">
    <div>
        <div>

            <h1 class="logo-name">H+</h1>

        </div>
        <h3>欢迎使用 H+</h3>
        <form class="m-t" role="form" id="loginForm">
            <div class="form-group">
                <input type="text" name="userName" v-model="userName" class="form-control"
                       placeholder="用户名" required="">
            </div>
            <div class="form-group">
                <input type="password" name="passWord" v-model="passWord" class="form-control"
                       placeholder="密码" required="">
            </div>

            <div class="form-group">
                    <label style="float:left" class="checkbox-inline i-checks" ><input type="checkbox" name="rememberMe" id="rememberMe" value="1" >  自动登录</label>
                    <label class="checkbox-inline i-checks"  ><input type="checkbox"  name="rememberAccount" id="rememberAccount" value="1">  记住账号</label>
            </div>
            <div class="form-group">
                <button type="button" @click="login()" class="btn btn-primary block full-width m-b">
                    登录
                </button>
            </div>
        </form>
        <p class="text-muted text-center"><a href="login">
            <small>忘记密码了？</small>
        </a> | <a href="register">注册一个新账号</a></p>
    </div>
</div>

<!-- jQuery Validation plugin javascript-->
<script src="${basePath}/static/js/plugins/iCheck/icheck.min.js"></script>
<script src="${basePath}/static/js/plugins/validate/jquery.validate.min.js"></script>
<script src="${basePath}/static/js/plugins/validate/messages_zh.min.js"></script>

<script src="${basePath}/static/js/backend/login.js"></script>
</body>

</html>