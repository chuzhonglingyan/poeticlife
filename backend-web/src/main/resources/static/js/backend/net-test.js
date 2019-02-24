
new Vue({
    el: '#app',
    data: {
        msg: 'Hello World!'
    },
    methods: {
        getForm: function () {
            getForm('https://api.apiopen.top/searchPoetry?name=古风二首 二',function (data) {
                console.debug(data.result);
                console.debug(data.code);
                console.debug("解析结果：");
                //直接转换为json对象了
                var  list=data.result;
                console.debug(list[0]);
                console.debug(list[0].authors);
            });
        },
        postForm: function () {
            var  params={name:"古风二首 二"};
            postForm('https://api.apiopen.top/searchPoetry',params,function (data) {

            });
        },
        postJson: function () {
            var  params={name:"古风二首 二"};
            postJson('https://api.apiopen.top/searchPoetry',params,function (data) {
            });
        }
    }
});
