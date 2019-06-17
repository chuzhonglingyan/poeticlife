var vm = new Vue({
    el: '#roleList',
    data: {
        url: {
            queryUrl: baseURL + "/role/list",
            addUrl: baseURL + "/role/add",
            updateUrl: baseURL + "/role/update",
            isEnableUrl: baseURL + "/role/isEnable",
            isStopUrl: baseURL + "/role/isStop",
            deleteUrl: baseURL + "/role/delete"
        },
        validator: {},
        rowData:null,
        addRoleModal:null
    },
    methods: {
        queryData: function () {
            var table = $('#roleTable');
            table.bootstrapTable('destroy');
            table.bootstrapTable({
                url: this.url.queryUrl,      //请求后台的URL（*）
                method: 'post',              //请求方式（*）
                dataType: 'json',
                contentType: "application/x-www-form-urlencoded",
                cache: false,                //是否使用缓存，默认为true
                sidePagination: "server",   //分页方式: client客户端分页，server服务端分页（*）
                pagination: true,
                pageList: [10, 25, 50, 100, 500],
                pageNumber: 1,
                pageSize: 10,
                striped: true, //每行表格的背景会显示灰白相间
                queryParams: function (params) { //查询的参数
                    return {
                        pageNum: (params.offset / params.limit) + 1,
                        pageSize: params.limit
                    };
                },
                columns: [{
                    title: "序号",
                    formatter: function (value, row, index) {
                        return index + 1;
                    },
                    align: 'center'
                }, {
                    field: 'roleName',
                    title: "角色名称",
                    align: 'center'
                }, {
                    field: 'roleStatus',
                    title: "角色状态",
                    align: 'center',
                    formatter: function (value, row, index) {
                        return formatStatus(value);
                    }
                }, {
                    field: 'remark',
                    title: "备注",
                    align: 'center',
                    formatter: function (value, row, index) {
                        return formatText(value);
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
                        str += "<button style='margin-left:15px' class='btn btn-xs btn-info' onclick='vm.editRole(" + JSON.stringify(row) + ")'><i class=\"fa fa-pencil-square-o\" ></i>&nbsp;编辑</button> ";
                        str += "<button style='margin-left:15px' class='btn btn-xs btn-info' onclick='vm.allocationAuthority(" + JSON.stringify(row) + ")'><i class=\"fa fa-pencil-square-o\" ></i>&nbsp;分配权限</button> ";
                        if (row.roleStatus === 1) {
                            str += "<button style='margin-left:15px' class='btn btn-xs btn-warning ' onclick='vm.freezeRole(" + row.id + ")'><i class=\"fa fa-ban\" ></i>禁用</button>";
                        } else {
                            str += "<button style='margin-left:15px' class='btn btn-xs btn-success ' onclick='vm.eableRole(" + row.id + ")'><i class=\"fa fa-play\" ></i>启用</button>";
                            str += "<button style='margin-left:15px' class='btn btn-xs btn-danger ' onclick='vm.deleteRole(" + row.id + ")'><i class=\"fa fa-trash-o\" ></i>&nbsp;删除</button>";
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
        eableRole: function (id) {
            postFormFull(vm.url.isEnableUrl,{"id":id},function (data) {
                layer.msg("启用成功");
                vm.queryData();
            },function (msg) {
                layer.msg(msg);
            })
        },
        freezeRole: function (id) {
            postFormFull(vm.url.isStopUrl,{"id":id},function (data) {
                layer.msg("禁用成功");
                vm.queryData();
            },function (msg) {
                layer.msg(msg);
            })
        },
        allocationAuthority: function (row) {
            addTP(baseURL+"/roleManager/authorityList?roleId=" + row.id+"&roleName="+row.roleName,"权限分配");
        },
        editRole: function (row) {
            vm.rowData=row;
            $('#roleName').val(row.roleName);
            $('#remark').val(row.remark);
            var  roleStatus=row.roleStatus;
            $('input:radio[name="roleStatus"][value='+roleStatus+']').prop("checked", "checked");
            setModalTitle( vm.addRoleModal,"编辑角色");
            showModal( vm.addRoleModal);
        },
        addRole: function () {
            vm.rowData=null;
            setModalTitle( vm.addRoleModal,"新增角色");
            showModal( vm.addRoleModal);
        },
        deleteRole: function (id) {
            //询问框
            layer.confirm('确定要删除？', {btn: ['确定', '取消']}, function () {
                postFormFull(vm.url.deleteUrl,{"id":id},function (data) {
                    layer.msg("删除成功");
                    vm.queryData();
                },function (msg) {
                    layer.msg(msg);
                })
            }, function () {
            });
        },
        saveRole: function () {
            var  paramFrom= $('#addRoleForm').serialize();

            vm.validator.validate();
            if (vm.validator.isValid()) {
                if (isEmpty(vm.rowData)) {
                    postFormFull(vm.url.addUrl,paramFrom,function (data) {
                        layer.msg("新增成功");
                        hideModal( vm.addRoleModal);
                        vm.queryData();
                    },function (msg) {
                        layer.msg(msg);
                    })
                }else {
                    var data = $.param({'id': vm.rowData.id}) + '&' + paramFrom;
                    postFormFull(vm.url.updateUrl,data,function (data) {
                        layer.msg("编辑成功");
                        hideModal( vm.addRoleModal);
                        vm.queryData();
                    },function (msg) {
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
        this.addRoleModal=$('#addRoleModal');
        initModalListener();
    }
});




function initModalListener() {
    var  addRoleModal= $("#addRoleModal");
    showModalListener(addRoleModal,function () {
        initValidForm();
        vm.validator = $("#addRoleForm").data("bootstrapValidator");
        console.debug("显示");
    });

    hideModalListener(addRoleModal,function () {
        console.debug("消失");
        $('#addRoleForm')[0].reset();
        vm.validator.destroy();
    })
}

function initValidForm() {
    $('#addRoleForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            roleName: {
                message: '角色名验证失败',
                validators: {
                    notEmpty: {
                        message: '角色名不能为空'
                    }
                }
            },
            remark: {
                validators: {

                }
            }
        }
    });
}
