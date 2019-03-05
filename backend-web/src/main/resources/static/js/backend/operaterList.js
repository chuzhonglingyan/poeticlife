var vm = new Vue({
    el: '#operaterList',
    data: {
        url: {
            queryUrl: baseURL + "/operater/list",
            insert: baseURL + "/operater/add"
        },
        validator: {}
    },
    methods: {
        queryData: function () {
            var table = $('#operaterTable');
            table.bootstrapTable('destroy');
            table.bootstrapTable({
                url: this.url.queryUrl,      //请求后台的URL（*）
                method: 'post',              //请求方式（*）
                cache: false,                //是否使用缓存，默认为true
                sidePagination: "server",   //分页方式: client客户端分页，server服务端分页（*）
                pagination: true,
                pageList: [10, 25, 50, 100, 500],
                pageNumber: 1,
                pageSize: 10,
                striped: true, //每行表格的背景会显示灰白相间
                queryParams: function (params) { //查询的参数
                    var startPage = params.offset + 1;
                    return {
                        size: params.limit,
                        page: startPage
                    };
                },
                columns: [{
                    title: "序号",
                    formatter: function (value, row, index) {
                        return index + 1;
                    },
                    align: 'center'
                }, {
                    field: 'userName',
                    title: "用户名",
                    align: 'center'
                }, {
                    field: 'email',
                    title: "邮箱",
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
                        str += "<button class='btn btn-xs btn-info' onclick='vm.editUser(" + JSON.stringify(row) + ")'><i class=\"fa fa-pencil-square-o\" ></i>&nbsp;编辑</button> ";
                        str += "<button style='margin-left:15px' class='btn btn-xs btn-primary' onclick='vm.roleAssignment(" + row.id + ")'>角色分配</button>";
                        if (row.isEnable === 1) {
                            str += "<button style='margin-left:15px' class='btn btn-xs btn-warning ' onclick='vm.freezeUser(" + row.id + ")'><i class=\"fa fa-ban\" ></i>禁用</button>";
                        } else {
                            str += "<button style='margin-left:15px' class='btn btn-xs btn-success ' onclick='vm.eableUser(" + row.id + ")'><i class=\"fa fa-play\" ></i>启用</button>";
                            str += "<button style='margin-left:15px' class='btn btn-xs btn-danger ' onclick='vm.deleteUser(" + row.id + ")'><i class=\"fa fa-trash-o\" ></i>&nbsp;删除</button>";
                        }
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
        roleAssignment: function (id) {
            console.debug("roleAssignment:" + id)
        },
        eableUser: function (id) {
            console.debug("eableUser:" + id)
        },
        freezeUser: function (id) {
            console.debug("freezeUser:" + id)
        },
        editUser: function (row) {
            console.debug("editUser:" + row.id)
        },
        deleteUser: function (id) {
            console.debug("deleteUser:" + id)
        },
        addUser: function () {
            console.debug("进入表单验证");
            vm.validator.validate();
            if (vm.validator.isValid()) {
                console.debug("通过表单验证");
            }
        }
    },
    created: function () {

    },
    mounted: function () {
        this.queryData();
        initModalListener();
    }
});


function initModalListener() {
    var  addUserModal= $("#addUserModal");
    showModal(addUserModal,function () {
        initValidForm();
        vm.validator = $("#addUserForm").data("bootstrapValidator");
        console.debug("显示");
    });

    hideModal(addUserModal,function () {
        console.debug("消失");
        $('#addUserForm')[0].reset();
        vm.validator.destroy();
    })
}

function initValidForm() {
    $('#addUserForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            userName: {
                message: '用户名验证失败',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    }
                }
            },
            userEmail: {
                validators: {
                    notEmpty: {
                        message: '邮箱地址不能为空'
                    }
                }
            }
        }
    });
}
