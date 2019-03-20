var vm = new Vue({
    el: '#scheduleJobList',
    data: {
        url: {
            queryUrl: baseURL + "/scheduleJob/list",
            addUrl: baseURL + "/scheduleJob/add",
            updateUrl: baseURL + "/scheduleJob/update",
            isRunUrl: baseURL + "/scheduleJob/run",
            isEnableUrl: baseURL + "/scheduleJob/resume",
            isStopUrl: baseURL + "/scheduleJob/pause",
            deleteUrl: baseURL + "/scheduleJob/delete"
        },
        addScheduleJobModal: null,
        rowData: null,
        validator: {}
    },
    methods: {
        queryData: function () {
            var table = $('#scheduleJobTable');
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
                        "pageSize": params.limit
                    };
                },
                columns: [{
                    title: "序号",
                    formatter: function (value, row, index) {
                        return index + 1;
                    },
                    align: 'center'

                }, {
                    field: 'groupName',
                    title: "组名称",
                    align: 'center'
                }, {
                    field: 'beanName',
                    title: "任务类名",
                    align: 'center'
                }, {
                    field: 'status',
                    title: "状态",
                    align: 'center',
                    formatter: function (value, row, index) {
                        return formatStatus(value);
                    }
                }, {
                    field: 'remark',
                    title: "描述",
                    align: 'center'
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
                        str += "<button class='btn btn-xs btn-info' onclick='vm.editScheduleJob(" + JSON.stringify(row) + ")'><i class=\"fa fa-pencil-square-o\" ></i>&nbsp;编辑</button> ";
                        str += "<button style='margin-left:15px' class='btn btn-xs btn-success ' onclick='vm.runScheduleJob(" + row.id + ")'><i ></i>立即执行</button>";

                        if (row.status === 1) {
                            str += "<button style='margin-left:15px' class='btn btn-xs btn-warning ' onclick='vm.freezeScheduleJob(" + row.id + ")'><i class=\"fa fa-pause\" ></i>暂停</button>";
                        } else {
                            str += "<button style='margin-left:15px' class='btn btn-xs btn-success ' onclick='vm.eableScheduleJob(" + row.id + ")'><i class=\"fa fa-play\" ></i>启动</button>";
                        }
                        str += "<button style='margin-left:15px' class='btn btn-xs btn-danger ' onclick='vm.deleteScheduleJob(" + row.id + ")'><i class=\"fa fa-trash-o\" ></i>&nbsp;删除</button>";
                        str += "<button style='margin-left:15px' class='btn btn-xs btn-info ' onclick='vm.seeScheduleJobLog(" + JSON.stringify(row) + ")'><i class=\"fa fa-forward\" ></i>&nbsp;日志</button>";
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
        runScheduleJob: function (id) {
            postFormFull(vm.url.isRunUrl, {"id": id}, function (data) {
                layer.msg("执行成功");
                vm.queryData();
            }, function (msg) {
                layer.msg(msg);
            })
        },
        eableScheduleJob: function (id) {
            postFormFull(vm.url.isEnableUrl, {"id": id}, function (data) {
                layer.msg("启动成功");
                vm.queryData();
            }, function (msg) {
                layer.msg(msg);
            })
        },
        freezeScheduleJob: function (id) {
            postFormFull(vm.url.isStopUrl, {"id": id}, function (data) {
                layer.msg("暂停成功");
                vm.queryData();
            }, function (msg) {
                layer.msg(msg);
            })
        },
        editScheduleJob: function (row) {
            vm.rowData = row;
            var groupName = $('#groupName');
            disabledInput(groupName, true);

            groupName.selectpicker('val', row.groupName);

            $('#beanName').val(row.beanName);
            $('#methodName').val(row.methodName);
            $('#cronExpression').val(row.cronExpression);
            $('#params').val(row.params);
            $('#remark').val(row.remark);

            var status = row.status;
            $('input:radio[name="status"][value=' + status + ']').prop("checked", "checked");

            setModalTitle(vm.addScheduleJobModal, "编辑用户");
            showModal(vm.addScheduleJobModal);
        },
        addScheduleJob: function () {
            vm.rowData = null;
            disabledInput($('#groupName'), false);
            setModalTitle(vm.addScheduleJobModal, "新增用户");
            showModal(vm.addScheduleJobModal);
        },
        deleteScheduleJob: function (id) {
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
        },
        seeScheduleJobLog: function (row) {
            addTP(baseURL+"/scheduleJobLogManager?jobId=" + row.id+"&beanName="+row.beanName,"定时器日志");
        },
        saveScheduleJob: function () {
            var paramFrom = $('#addScheduleJobForm').serialize();
            var groupName = $('#groupName').val();
            paramFrom = $.param({'groupName': groupName}) + '&' + paramFrom;
            if (isEmpty(groupName)) {
                layer.msg("组名不能为空");
                return
            }
            console.debug(paramFrom);
            vm.validator.validate();
            if (vm.validator.isValid()) {
                if (isEmpty(vm.rowData)) {
                    postFormFull(vm.url.addUrl, paramFrom, function (data) {
                        layer.msg("新增成功");
                        hideModal(vm.addScheduleJobModal);
                        vm.queryData();
                    }, function (msg) {
                        layer.msg(msg);
                    })
                } else {
                    var data = $.param({'id': vm.rowData.id}) + '&' + paramFrom;
                    postFormFull(vm.url.updateUrl, data, function (data) {
                        layer.msg("编辑成功");
                        hideModal(vm.addScheduleJobModal);
                        vm.queryData();
                    }, function (msg) {
                        layer.msg(msg);
                    })
                }
            }
        }
    },
    created: function () {

    },
    mounted: function () {
        this.queryData();
        this.addScheduleJobModal = $('#addScheduleJobModal');
        initModalListener();
    }
});


function initModalListener() {
    var addScheduleJobModal = $("#addScheduleJobModal");
    showModalListener(addScheduleJobModal, function () {
        initValidForm();
        vm.validator = $("#addScheduleJobForm").data("bootstrapValidator");
    });

    hideModalListener(addScheduleJobModal, function () {
        $('#addScheduleJobForm')[0].reset();
        vm.validator.destroy();
        $('#groupName').selectpicker('val', '');
    })
}

function initValidForm() {
    $('#addScheduleJobForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            groupName: {
                validators: {
                    notEmpty: {
                        message: '组名不能为空'
                    }
                }
            },
            beanName: {
                validators: {
                    notEmpty: {
                        message: '任务类不能为空'
                    }
                }
            },
            methodName: {
                validators: {
                    notEmpty: {
                        message: '方法名不能为空'
                    }
                }
            },
            cronExpression: {
                validators: {
                    notEmpty: {
                        message: '时间表达式不能为空'
                    }
                }
            }
        }
    });
}



