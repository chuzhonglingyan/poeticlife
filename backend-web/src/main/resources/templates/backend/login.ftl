<!DOCTYPE html>
<html>
<head >

    <title>登录界面</title>
    <#include "../common/head.ftl">

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
            <form >
                <h3 class="text-center">请登录</h3>
                <div class="form-group">
                    <label for="username" class="sr-only">用户名</label>
                    <input type="text" id="userName" v-model="userName" class="form-control" placeholder="用户名" >
                </div>
                <div class="form-group">
                    <label for="password" class="sr-only">密码</label>
                    <input autocomplete="off" type="password"  id="passWord" v-model="passWord" class="form-control" placeholder="密码" >
                </div>
                <div class="form-group">
                <div class="checkbox">
                    <label>
                        <input type="checkbox" value="remember-me">
                        记住我 </label>
                </div>
                <button @click="login()" class="btn btn-lg btn-primary btn-block" id="login" type="button">登录</button>
                </div>
            </form>
        </div>
    </div>

</div>

<script src="${basePath}/static/js/backend/login.js"></script>
</body>

</html>