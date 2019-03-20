var $table = $('#table');

var  roleId;
var  roleName;

$(function () {
    roleId=$("#roleId").val();
    roleName=$("#roleName").val();

    console.debug(roleName);

    $('#title').html("角色："+roleName);

    loadTable();
});



function saveSelectMenuList(roleId,menuList) {
    var  saveMenuListUrl=baseURL + "/role/menu/saveMenuListByRoleId";
    var param={"roleId":roleId,"menuIdList":menuList};
    postJson(saveMenuListUrl,param,function (data) {
        layer.msg("保存成功");
    },function (msg) {
        layer.msg(msg);
    })
}



function loadTable() {
    $table.bootstrapTable('destroy');
    $table.bootstrapTable({
        url: baseURL + "/role/menu/getMenuListByRoleId",      //请求后台的URL（*）
        method: 'post',              //请求方式（*）
        dataType: 'json',
        contentType: "application/x-www-form-urlencoded",
        idField: 'id',
        queryParams: function (params) { //查询的参数
            return {
                "roleId": roleId
            };
        },
        columns: [
            {
                field: 'check', checkbox: true, formatter: function (value, row, index) {
                    if (row.check === true) {
                        //设置选中
                        return {checked: true};
                    }
                }
            },
            {field: 'menuName', title: '名称'},
            {field: 'menuType', title: '类型', formatter: 'typeFormatter'},
        ],

        // bootstrap-table-treegrid.js 插件配置 -- start
        //在哪一列展开树形
        treeShowField: 'menuName',
        //指定父id列
        parentIdField: 'pid',

        onResetView: function (data) {
            //console.log('load');
            $table.treegrid({
                initialState: 'expanded',// 所有节点都展开，默认展开
                treeColumn: 1,
                // expanderExpandedClass: 'glyphicon glyphicon-minus',  //图标样式
                // expanderCollapsedClass: 'glyphicon glyphicon-plus',
                onChange: function () {
                    $table.bootstrapTable('resetWidth');
                }
            });
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
    });
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
    }
}

function selectParentChecked(datas, row, id, pid) {
    for (var i in datas) {
        if (datas[i][id] === row[pid]) {
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
    var menuIdList = [];
    $.each(selRows, function (i) {
        postData += this.id;
        menuIdList.push(this.id);
        if (i < selRows.length - 1) {
            postData += ",";
        }
    });
    console.debug("你选中行的 id 为：" + postData);
    saveSelectMenuList(roleId,menuIdList);
}





