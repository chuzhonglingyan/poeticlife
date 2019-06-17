var vm = new Vue({
    el: '#articleList',
    data: {
        url: {
            queryUrl: baseURL + "/article/list",
            addUrl: baseURL + "/article/add",
            updateUrl: baseURL + "/article/update",
            isEnableUrl: baseURL + "/article/isEnable",
            isStopUrl: baseURL + "/article/isStop",
            deleteUrl: baseURL + "/article/delete"
        },
        validator: {},
        rowData:null,
        addarticleModal:null
    },
    methods: {
        queryData: function () {
            var table = $('#articleTable');
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
                    field: 'articleName',
                    title: "文章名称",
                    align: 'center'
                }, {
                    field: 'articleStatus',
                    title: "文章状态",
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
                        str += "<button style='margin-left:15px' class='btn btn-xs btn-info' onclick='vm.editArticle(" + JSON.stringify(row) + ")'><i class=\"fa fa-pencil-square-o\" ></i>&nbsp;编辑</button> ";
                        if (row.articleStatus === 1) {
                            str += "<button style='margin-left:15px' class='btn btn-xs btn-warning ' onclick='vm.freezeArticle(" + row.id + ")'><i class=\"fa fa-ban\" ></i>禁用</button>";
                        } else {
                            str += "<button style='margin-left:15px' class='btn btn-xs btn-success ' onclick='vm.eableArticle(" + row.id + ")'><i class=\"fa fa-play\" ></i>启用</button>";
                            str += "<button style='margin-left:15px' class='btn btn-xs btn-danger ' onclick='vm.deleteArticle(" + row.id + ")'><i class=\"fa fa-trash-o\" ></i>&nbsp;删除</button>";
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
        eableArticle: function (id) {
            postFormFull(vm.url.isEnableUrl,{"id":id},function (data) {
                layer.msg("启用成功");
                vm.queryData();
            },function (msg) {
                layer.msg(msg);
            })
        },
        freezeArticle: function (id) {
            postFormFull(vm.url.isStopUrl,{"id":id},function (data) {
                layer.msg("禁用成功");
                vm.queryData();
            },function (msg) {
                layer.msg(msg);
            })
        },
        editArticle: function (row) {
            vm.rowData=row;
            $('#articleName').val(row.ArticleName);
            $('#remark').val(row.remark);
            var  articleStatus=row.ArticleStatus;
            $('input:radio[name="articleStatus"][value='+articleStatus+']').prop("checked", "checked");
            setModalTitle( vm.addArticleModal,"编辑文章");
            showModal( vm.addArticleModal);
        },
        addArticle: function () {
            vm.rowData=null;
            setModalTitle( vm.addArticleModal,"新增文章");
            showModal( vm.addArticleModal);
        },
        deleteArticle: function (id) {
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
        saveArticle: function () {
            var  paramFrom= $('#addArticleForm').serialize();

            vm.validator.validate();
            if (vm.validator.isValid()) {
                if (isEmpty(vm.rowData)) {
                    postFormFull(vm.url.addUrl,paramFrom,function (data) {
                        layer.msg("新增成功");
                        hideModal( vm.addArticleModal);
                        vm.queryData();
                    },function (msg) {
                        layer.msg(msg);
                    })
                }else {
                    var data = $.param({'id': vm.rowData.id}) + '&' + paramFrom;
                    postFormFull(vm.url.updateUrl,data,function (data) {
                        layer.msg("编辑成功");
                        hideModal( vm.addArticleModal);
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
        this.addArticleModal=$('#addArticleModal');
        initModalListener();
    }
});




function initModalListener() {
    var  addArticleModal= $("#addArticleModal");
    showModalListener(addArticleModal,function () {
        initValidForm();
        vm.validator = $("#addArticleForm").data("bootstrapValidator");
        console.debug("显示");
    });

    hideModalListener(addArticleModal,function () {
        console.debug("消失");
        $('#addArticleForm')[0].reset();
        vm.validator.destroy();
    })
}

function initValidForm() {
    $('#addArticleForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            ArticleName: {
                message: '文章验证失败',
                validators: {
                    notEmpty: {
                        message: '文章名不能为空'
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
