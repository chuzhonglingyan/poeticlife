var $table = $('#table');
var $addSysMenu = $('#addSysMenu');

//父id
var pid = null;

var id = null;

$(function () {
    $('#addFirstMenu').click(function () {
        setModalTitle($addSysMenu,"新增一级菜单");
        $("#menuTypeFrom").hide();
        showModal( $addSysMenu);
        pid = 0;
    });
    $('#saveMenu').click(function () {
        saveOrUpdateMenu();
    });

    hideModalListener($addSysMenu, function () {
        $('#addMenuForm')[0].reset();
        pid = null;
    });

    loadTable();
});

function loadTable() {
    $table.bootstrapTable('destroy');
    $table.bootstrapTable({
        url: baseURL + "/menu/getAll",      //请求后台的URL（*）
        method: 'post',              //请求方式（*）
        idField: 'id',
        columns: [
            {
                field: 'check', checkbox: true, formatter: function (value, row, index) {
                    if (row.check === true) {
                        // console.log(row.serverName);
                        //设置选中
                        return {checked: true};
                    }
                }
            },
            {field: 'menuName', title: '名称'},
            // {field: 'id', title: '编号', sortable: true, align: 'center'},
            // {field: 'pid', title: '所属上级'},
            {
                field: 'menuStatus',
                title: '状态',
                sortable: true,
                align: 'center',
                formatter: 'statusFormatter'
            },
            {field: 'menuUrl', title: '权限值'},
            {field: 'menuType', title: '类型', formatter: 'typeFormatter'},
            {
                field: 'operate',
                title: '操作',
                align: 'center',
                events: operateEvents,
                formatter: 'operateFormatter'
            }
        ],

        // bootstrap-table-treegrid.js 插件配置 -- start
        //在哪一列展开树形
        treeShowField: 'menuName',
        //指定父id列
        parentIdField: 'pid',

        onResetView: function (data) {
            //console.log('load');
            $table.treegrid({
                initialState: 'collapsed',// 所有节点都折叠
                // initialState: 'expanded',// 所有节点都展开，默认展开
                treeColumn: 1,
                // expanderExpandedClass: 'glyphicon glyphicon-minus',  //图标样式
                // expanderCollapsedClass: 'glyphicon glyphicon-plus',
                onChange: function () {
                    $table.bootstrapTable('resetWidth');
                }
            });

            //只展开树形的第一级节点
            $table.treegrid('getRootNodes').treegrid('expand');

        },
        onCheck: function (row) {
            var datas = $table.bootstrapTable('getData');
            // 勾选子类
            selectChilds(datas, row, "id", "pid", true);

            // 勾选父类
            selectParentChecked(datas, row, "id", "pid");

            // 刷新数据
            $table.bootstrapTable('load', datas);
        },

        onUncheck: function (row) {
            var datas = $table.bootstrapTable('getData');
            selectChilds(datas, row, "id", "pid", false);
            $table.bootstrapTable('load', datas);
        }
        // bootstrap-table-treetreegrid.js 插件配置 -- end
    });
}

// 格式化按钮
function operateFormatter(value, row, index) {

    var  list=[ '<button type="button" class="RoleOfadd btn btn-xs btn-primary " style="margin-right:15px;"><i class="fa fa-plus" ></i>&nbsp;新增</button>',
        '<button type="button" class="RoleOfedit btn btn-xs btn-info " style="margin-right:15px;"><i class="fa fa-pencil-square-o" ></i>&nbsp;编辑</button>'];
    if (row.menuStatus===0){
        list.push('<button type="button" class="RoleOfdelete btn btn-xs btn-danger" style="margin-right:15px;"><i class="fa fa-trash-o" ></i>&nbsp;删除</button>')
    }
    return list.join('');
}

// 格式化类型
function typeFormatter(value, row, index) {
    if (value === 1) {
        return '菜单';
    }
    if (value === 2) {
        return '按钮';
    }
    if (value === 3) {
        return '接口';
    }
    return '-';
}

// 格式化状态
function statusFormatter(value, row, index) {
    if (value === 1) {
        return '<span class="label label-success">正常</span>';
    } else {
        return '<span class="label label-default">锁定</span>';
    }
}


//初始化操作按钮的方法
window.operateEvents = {
    'click .RoleOfadd': function (e, value, row, index) {
        add(row.id);
    },
    'click .RoleOfdelete': function (e, value, row, index) {
        del(row.id);
    },
    'click .RoleOfedit': function (e, value, row, index) {
        update(row.id);
    }
};


/**
 * 选中父项时，同时选中子项
 * @param datas 所有的数据
 * @param row 当前数据
 * @param id id 字段名
 * @param pid 父id字段名
 */
function selectChilds(datas, row, id, pid, checked) {
    for (var i in datas) {
        if (datas[i][pid] === row[id]) {
            datas[i].check = checked;
            selectChilds(datas, datas[i], id, pid, checked);
        }
        ;
    }
}

function selectParentChecked(datas, row, id, pid) {
    for (var i in datas) {
        if (datas[i][id] == row[pid]) {
            datas[i].check = true;
            selectParentChecked(datas, datas[i], id, pid);
        }
    }
}

function test() {
    var selRows = $table.bootstrapTable("getSelections");
    if (selRows.length === 0) {
        layerAlert("请至少选择一行");
        return;
    }

    var postData = "";
    $.each(selRows, function (i) {
        postData += this.id;
        if (i < selRows.length - 1) {
            postData += "， ";
        }
    });
    layerAlert("你选中行的 id 为：" + postData);

}

function add(id) {
    $addSysMenu.find('.modal-title').text('新增菜单');
    $("#menuTypeFrom").show();
    $addSysMenu.modal("show");
    pid = id;

    this.id=null;
}

function update(id) {
    $addSysMenu.find('.modal-title').text('编辑菜单');
    $addSysMenu.modal("show");
    pid =null;
    this.id=id;
    postFormFull(baseURL + "/menu/detail", {"id":id}, function (data) {
        $('#menuName').val(data.menuName);
        $('#menuValue').val(data.menuUrl);
        $('#menuCode').val(data.menuCode);

        var  menuType=data.menuType;
        var  menuStatus=data.menuStatus;
        $('input:radio[name="menuType"][value='+menuType+']').prop("checked", "checked");
        $('input:radio[name="menuStatus"][value='+menuStatus+']').prop("checked", "checked");

    }, function (msg) {
        layer.msg(msg);
    });
}


function del(id) {
    //询问框
    layer.confirm('确定要删除？', {btn: ['确定', '取消']}, function () {
        delMenu(id);
    }, function () {
    });
}




function saveOrUpdateMenu() {
    var  isAdd=isEmpty(id);

    var menuName = $.trim(($('#menuName').val()));
    var menuValue = $.trim(($('#menuValue').val()));
    var menuCode = $.trim(($('#menuCode').val()));
    var menuType = $('input:radio[name=menuType]:checked').val();
    var menuStatus = $('input:radio[name=menuStatus]:checked').val();
    var menu = {
        "id": id,
        "pid": pid,
        "menuName": menuName,
        "menuUrl": menuValue,
        "menuCode": menuCode,
        "menuType": menuType,
        "menuStatus": menuStatus
    };
    if (isEmpty(menuName)) {
        layer.msg("菜单名不能为空");
        return;
    }
    if (isEmpty(menuValue)) {
        layer.msg("菜单值不能为空");
        return;
    }
    if (isEmpty(menuCode)) {
        layer.msg("菜单权限不能为空");
        return;
    }
    console.debug(menu);

    if (isAdd) {
        postFormFull(baseURL + "/menu/add", menu, function (data) {
            loadTable();
            layer.msg("新增成功");
            $addSysMenu.modal("hide");
        }, function (msg) {
            layer.msg(msg);

        });
    }else {
        postFormFull(baseURL + "/menu/update", menu, function (data) {
            loadTable();
            layer.msg("更新成功");
            $addSysMenu.modal("hide");
        }, function (msg) {
            layer.msg(msg);
        });
    }
}

function delMenu(id) {
    var menu = {"id": id};
    postFormFull(baseURL + "/menu/delete", menu, function (data) {
        loadTable();
        layer.msg("删除成功");
    }, function (msg) {
        layer.msg(msg);
    });
}