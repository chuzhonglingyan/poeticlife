var app = new Vue({
    el: '#wrapper',
    data: {
        loginOutUrl : baseURL+"/operater/loginOut",
        getMenuListByOperater :baseURL+"/operater/getMenuListByOperater",
        menuList:[],
        msg:""
    },
    methods: {
        initMenuList:function () {
            console.group('initMenuList===============》');
            getForm(this.getMenuListByOperater, function (data) {
                this.menuList=data;
                console.debug(JSON.stringify(data));
            });
        },
        loginOut: function () {
            getForm(this.loginOutUrl, function (data) {
                console.debug("退出登录");
                gotoPage("/login");
            });
        }
    },
    created: function () {
        console.group('created 创建完毕状态===============》');
        this.initMenuList();
    },
    mounted: function () {
        this.msg = "首页";
        console.group('mounted 挂载结束状态===============》');
    }
});