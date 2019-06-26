var vm = new Vue({
    el: '#imageList',
    data: {
        url: {
            queryUrl: baseURL + "/image/list",
            addUrl: baseURL + "/image/add",
            updateUrl: baseURL + "/image/update",
            isEnableUrl: baseURL + "/image/isEnable",
            isStopUrl: baseURL + "/image/isStop",
            deleteUrl: baseURL + "/image/delete"
        },
        validator: {},
        rowData:null,
        addimageModal:null
    },
    methods: {
        queryData: function () {
            var table = $('#imageTable');
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
                    field: 'imageName',
                    title: "图片名称",
                    align: 'center'
                }, {
                    field: 'imageStatus',
                    title: "图片状态",
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
                        str += "<button style='margin-left:15px' class='btn btn-xs btn-info' onclick='vm.editImage(" + JSON.stringify(row) + ")'><i class=\"fa fa-pencil-square-o\" ></i>&nbsp;编辑</button> ";
                        if (row.imageStatus === 1) {
                            str += "<button style='margin-left:15px' class='btn btn-xs btn-warning ' onclick='vm.freezeImage(" + row.id + ")'><i class=\"fa fa-ban\" ></i>禁用</button>";
                        } else {
                            str += "<button style='margin-left:15px' class='btn btn-xs btn-success ' onclick='vm.eableImage(" + row.id + ")'><i class=\"fa fa-play\" ></i>启用</button>";
                            str += "<button style='margin-left:15px' class='btn btn-xs btn-danger ' onclick='vm.deleteImage(" + row.id + ")'><i class=\"fa fa-trash-o\" ></i>&nbsp;删除</button>";
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
        eableImage: function (id) {
            postFormFull(vm.url.isEnableUrl,{"id":id},function (data) {
                layer.msg("启用成功");
                vm.queryData();
            },function (msg) {
                layer.msg(msg);
            })
        },
        freezeImage: function (id) {
            postFormFull(vm.url.isStopUrl,{"id":id},function (data) {
                layer.msg("禁用成功");
                vm.queryData();
            },function (msg) {
                layer.msg(msg);
            })
        },
        editImage: function (row) {
            vm.rowData=row;
            $('#imageName').val(row.ImageName);
            $('#remark').val(row.remark);
            var  imageStatus=row.ImageStatus;
            $('input:radio[name="imageStatus"][value='+imageStatus+']').prop("checked", "checked");
            setModalTitle( vm.addImageModal,"编辑图片");
            showModal( vm.addImageModal);
        },
        addImage: function () {
            vm.rowData=null;
            setModalTitle( vm.addImageModal,"新增图片");
            showModal( vm.addImageModal);
        },
        deleteImage: function (id) {
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
        saveImage: function () {
            var  paramFrom= $('#addImageForm').serialize();

            vm.validator.validate();
            if (vm.validator.isValid()) {
                if (isEmpty(vm.rowData)) {
                    postFormFull(vm.url.addUrl,paramFrom,function (data) {
                        layer.msg("新增成功");
                        hideModal( vm.addImageModal);
                        vm.queryData();
                    },function (msg) {
                        layer.msg(msg);
                    })
                }else {
                    var data = $.param({'id': vm.rowData.id}) + '&' + paramFrom;
                    postFormFull(vm.url.updateUrl,data,function (data) {
                        layer.msg("编辑成功");
                        hideModal( vm.addImageModal);
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
        this.addImageModal=$('#addImageModal');
        initModalListener();
    }
});




function initModalListener() {
    var  addImageModal= $("#addImageModal");
    showModalListener(addImageModal,function () {
        initValidForm();
        vm.validator = $("#addImageForm").data("bootstrapValidator");
        console.debug("显示");
    });

    hideModalListener(addImageModal,function () {
        console.debug("消失");
        $('#addImageForm')[0].reset();
        vm.validator.destroy();
    })
}

function initValidForm() {
    $('#addImageForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            ImageName: {
                message: '图片验证失败',
                validators: {
                    notEmpty: {
                        message: '图片名不能为空'
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
