new Vue({
    el: '#app',
    data: {
        loginUrl:"/backend/operater/login",
        errors: [],
        userName: '',
        passWord: ''
    },
    methods: {
        checkForm: function (e) {
            if (this.userName && this.passWord) {
                return true;
            }
            this.errors = [];
            if (!this.userName) {
                this.errors.push('userName required.');
            }
            if (!this.passWord) {
                this.errors.push('password required.');
            }
            e.preventDefault();
        },
        login: function () {
            if (!this.userName) {
                alert("userName required.");
                return
            }
            if (!this.passWord) {
                alert("passWord required.");
                return
            }

            var params = {userName: this.userName, passWord: this.passWord};
            postForm(this.loginUrl, params, function (data) {
                console.debug("登录成功了");
                gotoPage("/index");
            });
        }
    }
});