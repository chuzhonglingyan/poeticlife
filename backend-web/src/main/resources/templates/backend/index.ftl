<!DOCTYPE html>
<html>
<head>
    <#include "../common/head.ftl">
    <title>后台管理首页</title>
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">

<div id="wrapper">


    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close"><i class="fa fa-times-circle"></i></div>
        <div class="sidebar-collapse">

        <#--侧滑菜单-->
            <ul class="nav" id="side-menu">
               <#--头部信息-->
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <span><img alt="image" class="img-circle" src="${basePath}/static/img/profile_small.jpg"/></span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                                <span class="block m-t-xs"><strong
                                       class="font-bold">Beaut-zihan</strong></span>
                                <span class="text-muted text-xs block" >{{userName}}<b
                                        class="caret"></b></span>
                                </span>
                        </a>
                    <#--下拉选择菜单-->
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a @click="editImage()" class="J_menuItem" href="">修改头像</a>
                            <li><a class="J_menuItem" href="">个人资料</a>
                            <li><a class="J_menuItem" href="">安全退出</a>
                        </ul>
                    </div>
                    <div class="logo-element">H+</div>
                </li>
             <#--菜单选项-->
                <#--<li  v-for="(item,index) in menuList" :id="item.id" >-->
                    <#--<a href="#">-->
                        <#--<i class="fa fa-home"></i>-->
                        <#--<span class="nav-label">{{item.menuName}}</span>-->
                        <#--<span class="fa arrow"></span>-->
                    <#--</a>-->
                    <#--<ul   class="nav nav-second-level ">-->
                        <#--<li v-for="(item,index) in  item.childList" :key="item.id">-->
                            <#--<a class="J_menuItem" href=""  :data-index="item.id"  >{{item.menuName}}</a>-->
                        <#--</li>-->
                    <#--</ul>-->
                <#--</li>-->

            </ul>

        </div>
    </nav>
    <!--左侧导航结束-->

    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <#--头部导航-->
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">

                <div class="navbar-header">
                    <#--侧滑开关-->
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#">
                        <i class="fa fa-bars"></i>
                    </a>
                   <#--搜索框-->
                    <form role="search" class="navbar-form-custom" method="post" >
                        <div class="form-group">
                            <input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search" id="top-search">
                        </div>
                    </form>
                </div>

            </nav>
        </div>

        <#--页面导航tab-->
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i></button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a  class="active J_menuTab" data-id="main" >首页</a>
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i></button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span></button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a></li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
                </ul>
            </div>
            <#--<a @click="loginOut()" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>-->
            <a href="/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
        </div>

        <#--中间内容-->
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="main" frameborder="0" data-id="main" seamless></iframe>
        </div>
        <div class="footer">
            <div class="pull-right">&copy; 2018-2020 <a href="http://www.zi-han.net/" target="_blank">zihan's blog</a>
            </div>
        </div>

    </div>
   <!--右侧部分结束-->
</div>

<!-- 自定义js -->
<script src="${basePath}/static/js/hplus.js?v=4.1.0"></script>
<script src="${basePath}/static/js/plugins/pace/pace.min.js"></script>
<script type="text/javascript" src="${basePath}/static/js/contabs.min.js"></script>
<script src="${basePath}/static/js/backend/index.js"></script>


</body>
</html>