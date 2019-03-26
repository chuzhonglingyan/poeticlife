var vm = new Vue({
    el: '#scheduleJobLogList',
    data: {
        url: {
            queryUrl: baseURL + "/scheduleJobLog/list",
            deleteUrl: baseURL + "/scheduleJobLog/delete"
        }
    },
    methods: {
        queryData: function () {
            var table = $('#scheduleJobLogTable');
            table.bootstrapTable('destroy');
            table.bootstrapTable({
                url: this.url.queryUrl,      //请求后台的URL（*）
                method: 'post',              //请求方式（*）
                cache: false,                //是否使用缓存，默认为true
                dataType: 'json',
                contentType: "application/x-www-form-urlencoded",
                sidePagination: "server",   //分页方式: client客户端分页，server服务端分页（*）
                pagination: true,
                pageList: [10, 25, 50, 100, 500],
                pageNumber: 1,
                pageSize: 10,
                striped: true, //每行表格的背景会显示灰白相间
                queryParams: function (params) { //查询的参数
                    var startPage = params.offset + 1;
                    return {
                        "pageNum": startPage,
                        "pageSize": params.limit,
                        "jobId": $("#jobId").val()
                    };
                },
                columns: [{
                    title: "序号",
                    formatter: function (value, row, index) {
                        return index + 1;
                    },
                    align: 'center'

                }, {
                    field: 'beanName',
                    title: "任务类名",
                    align: 'center'
                }, {
                    field: 'ip',
                    title: "执行ip",
                    align: 'center',
                    formatter: function (value, row, index) {
                        return value;
                    }
                }, {
                    field: 'status',
                    title: "状态",
                    align: 'center',
                    formatter: function (value, row, index) {
                        return formatStatus(value);
                    }
                }, {
                    field: 'times',
                    title: "耗时",
                    align: 'center',
                    formatter: function (value, row, index) {
                        return value+"毫秒";
                    }
                }, {
                    field: 'error',
                    title: "错误信息",
                    align: 'center',
                    formatter: function (value, row, index) {
                        return escapeHtml(value);
                    }
                }, {
                    field: 'createTime',
                    title: "创建时间",
                    align: 'center',
                    formatter: function (value, row, index) {
                        return formatStrDate(value);
                    }
                }, {
                    field: 'updateTime',
                    title: "更新时间",
                    align: 'center',
                    formatter: function (value, row, index) {
                        return formatStrDate(value);
                    }
                }, {
                    title: "操作",
                    formatter: function option(value, row, index) {
                        var str = "";
                        str += "<button style='margin-left:15px' class='btn btn-xs btn-danger ' onclick='vm.deleteUser(" + row.id + ")'><i class=\"fa fa-trash-o\" ></i>&nbsp;删除</button>";
                        return str;
                    },
                    align: 'center'
                }],
                onLoadSuccess: function (data) {
                    console.debug(data);
                },
                onLoadError: function () {
                    console.debug("数据加载失败！");
                },
                onDblClickRow: function (row, $element) {
                }
            });
        },
        deleteUser: function (id) {
            //询问框
            layer.confirm('确定要删除？', {btn: ['确定', '取消']}, function () {
                postFormFull(vm.url.deleteUrl, {"id": id}, function (data) {
                    layer.msg("删除成功");
                    vm.queryData();
                }, function (msg) {
                    layer.msg(msg);
                })
            }, function () {
            });
        }
    },
    created: function () {

    },
    mounted: function () {
        this.queryData();
        $('#title').html("任务："+$("#beanName").val());
    }
});


function formatStatus(value) {
    if (value === 1) {
        return "成功";
    } else {
        return "失败";
    }
}




