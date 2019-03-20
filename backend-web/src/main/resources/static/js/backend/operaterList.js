var vm = new Vue({
    el: '#operaterList',
    data: {
        url: {
            queryUrl: baseURL + "/operater/list",
            addUrl: baseURL + "/operater/add",
            updateUrl: baseURL + "/operater/update",
            isEnableUrl: baseURL + "/operater/isEnable",
            isStopUrl: baseURL + "/operater/isStop",
            deleteUrl: baseURL + "/operater/delete"
        },
        addUserModal: null,
        editorRoleModal: null,
        rowData: null,
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
                    field: 'accountName',
                    title: "账号名",
                    align: 'center'
                }, {
                    field: 'UserName',
                    title: "用户名",
                    align: 'center'
                }, {
                    field: 'email',
                    title: "邮箱",
                    align: 'center'
                }, {
                    field: 'isEnable',
                    title: "状态",
                    align: 'center',
                    formatter: function (value, row, index) {
                        return formatStatus(value);
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
                        str += "<button class='btn btn-xs btn-info' onclick='vm.editUser(" + JSON.stringify(row) + ")'><i class=\"fa fa-pencil-square-o\" ></i>&nbsp;编辑</button> ";
                        str += "<button    style='margin-left:15px' class='btn btn-xs btn-primary' onclick='vm.roleAssignment(" + JSON.stringify(row) + ")'>角色分配</button>";
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
        roleAssignment: function (row) {
            vm.rowData = row;
            loadRoleTable(row.id);
            showModal(vm.editorRoleModal);
        },
        eableUser: function (id) {
            postFormFull(vm.url.isEnableUrl, {"id": id}, function (data) {
                layer.msg("启用成功");
                vm.queryData();
            }, function (msg) {
                layer.msg(msg);
            })
        },
        freezeUser: function (id) {
            postFormFull(vm.url.isStopUrl, {"id": id}, function (data) {
                layer.msg("禁用成功");
                vm.queryData();
            }, function (msg) {
                layer.msg(msg);
            })
        },
        editUser: function (row) {
            vm.rowData = row;
            var $accountName = $('#accountName');
            disabledInput($accountName, true);

            $accountName.val(row.accountName);
            $('#UserName').val(row.UserName);
            $('#email').val(row.email);
            $('#phone').val(row.phone);
            $('#passWord').val(row.passWord);

            $("#passWordForm").show();
            setModalTitle(vm.addUserModal, "编辑用户");
            showModal(vm.addUserModal);
        },
        addUser: function () {
            vm.rowData = null;
            $("#passWordForm").hide();
            disabledInput($('#accountName'), false);
            setModalTitle(vm.addUserModal, "新增用户");
            showModal(vm.addUserModal);
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
        },
        saveUser: function () {
            var paramFrom = $('#addUserForm').serialize();
            vm.validator.validate();
            if (vm.validator.isValid()) {
                if (isEmpty(vm.rowData)) {
                    postFormFull(vm.url.addUrl, paramFrom, function (data) {
                        layer.msg("新增成功");
                        hideModal(vm.addUserModal);
                        vm.queryData();
                    }, function (msg) {
                        layer.msg(msg);
                    })
                } else {
                    var data = $.param({'id': vm.rowData.id}) + '&' + paramFrom;
                    postFormFull(vm.url.updateUrl, data, function (data) {
                        layer.msg("编辑成功");
                        hideModal(vm.addUserModal);
                        vm.queryData();
                    }, function (msg) {
                        layer.msg(msg);
                    })
                }
            }
        },
        saveEditorRole: function () {
            var selRows = $editorRoleTable.bootstrapTable("getSelections");
            var roleIdList = [];
            $.each(selRows, function (i) {
                roleIdList.push(this.id);
            });
            console.debug(roleIdList);
            saveSelectRoleList(vm.rowData.id, roleIdList);
        }
    },
    created: function () {

    },
    mounted: function () {
        this.queryData();
        this.addUserModal = $('#addUserModal');
        this.editorRoleModal = $('#editorRoleModal');
        initModalListener();
    }
});


function initModalListener() {
    var addUserModal = $("#addUserModal");
    showModalListener(addUserModal, function () {
        initValidForm();
        vm.validator = $("#addUserForm").data("bootstrapValidator");
    });

    hideModalListener(addUserModal, function () {
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
            accountName: {
                message: '账号验证失败',
                validators: {
                    notEmpty: {
                        message: '账号名不能为空'
                    }
                }
            },
            UserName: {
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: '邮箱地址不能为空'
                    }
                }
            }
        }
    });
}

var $editorRoleTable = $('#editorRoleTable');


function loadRoleTable(operaterId) {
    $editorRoleTable.bootstrapTable('destroy');
    $editorRoleTable.bootstrapTable({
        url: baseURL + "/operater/role/getRoleListByOperaterId",      //请求后台的URL（*）
        method: 'post',              //请求方式（*）
        dataType: 'json',
        contentType: "application/x-www-form-urlencoded",
        idField: 'id',
        queryParams: function (params) { //查询的参数
            return {
                "operaterId": operaterId
            };
        },
        columns: [
            {
                title: "序号",
                formatter: function (value, row, index) {
                    return index + 1;
                },
                align: 'center'

            },
            {
                field: 'check', checkbox: true, formatter: function (value, row, index) {
                    if (row.check === true) {
                        //设置选中
                        return {checked: true};
                    }
                }
            },
            {field: 'roleName', title: '角色名称'}
        ],
        onCheck: function (row) {
        },
        onUncheck: function (row) {
        }
    });
}


function saveSelectRoleList(operaterId, roleList) {
    var saveListUrl = baseURL + "/operater/role/saveRoleListByOperaterId";
    var param = {"operaterId": operaterId, "roleList": roleList};
    postJson(saveListUrl, param, function (data) {
        layer.msg("保存成功");
        hideModal(vm.editorRoleModal);
    }, function (msg) {
        layer.msg(msg);
    })
}


