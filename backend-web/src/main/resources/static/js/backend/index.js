var app = new Vue({
    el: '#wrapper',
    data: {
        loginOutUrl : baseURL+"/operater/loginOut",
        getMenuListByOperater :baseURL+"/operater/getMenuListByOperater",
        userName:""
    },
    methods: {
        initMenuList:function () {
            console.group('initMenuList===============》');
            var self=this;
            getForm(this.getMenuListByOperater, function (data) {
                self.userName="超级管理员";
                if (data==null||data.length<=0){
                    return;
                }
                self.crentMenus(data);
            });
        },
        crentMenus:function(data){
            $.each(data, function (idx, pNode) {
                var navHtml = '';
                navHtml += '<li id="li'+pNode.id+'" ';
                if (idx === 0)
                    navHtml += 'class="active"';
                navHtml += ' >';
                navHtml += ' <a><i href="#" class="fa fa-th-large"></i> <span class="nav-label">' + pNode.menuName + '</span> <span class="fa arrow"></span></a>';
                navHtml += ' <ul class="nav nav-first-level">';
                if(pNode.childList!=null && pNode.childList.length>0) {
                    $.each(pNode.childList, function (idx, child) {
                        if(child.childList!=null && child.childList.length>0) {

                            navHtml += '<li id="li'+child.id+'" ';
                            navHtml += ' >';
                            navHtml += ' <a><i href="#" class="fa fa-th-large"></i> <span class="nav-label">' + child.menuName + '</span> <span class="fa arrow"></span></a>';
                            navHtml += ' <ul class="nav nav-second-level">';

                            $.each(child.childList, function (idx, cchild) {
                                if (cchild.pid === child.id){
                                    navHtml += "<li><a  href='javascript:;' onclick='addTP("+"\""+cchild.menuUrl+"\""+","+"\""+cchild.menuName+"\""+")'  class='J_menuItem' > "+ cchild.menuName +" </a></li>";
                                }
                            });
                            navHtml += '   </ul>';
                            navHtml += '</li>';
                        }else {
                            if (child.pid === pNode.id){
                                navHtml += "<li><a  href='javascript:;' onclick='addTP("+"\""+child.menuUrl+"\""+","+"\""+child.menuName+"\""+")'  class='J_menuItem' > "+ child.menuName +" </a></li>";
                            }
                        }

                    });
                }
                navHtml += '   </ul>';
                navHtml += '</li>';
                $('#side-menu').append(navHtml);
            });
            $('#side-menu').metisMenu();
        },
        loginOut: function () {
            getForm(this.loginOutUrl, function (data) {
                console.debug("退出登录");
                gotoPage("/login");
            });
        },
        editImage: function () {
            addTP(baseURL+"/imageUpload?imageUrl=" + "test"+"&imageType="+ "1","图片上传");
        }
    },
    created: function () {
        console.group('created 创建完毕状态===============》');
        this.initMenuList();
    },
    mounted: function () {
        console.group('mounted 挂载结束状态===============》');
    }
});