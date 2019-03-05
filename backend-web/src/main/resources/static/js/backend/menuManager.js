var $table = $('#table');


$(function() {

    $table.bootstrapTable({
        url: baseURL+"/menu/getAll",      //请求后台的URL（*）
        method: 'post',              //请求方式（*）
        idField: 'id',
        columns: [
            { field: 'check',  checkbox: true, formatter: function (value, row, index) {
                    if (row.check === true) {
                        // console.log(row.serverName);
                        //设置选中
                        return {  checked: true };
                    }
                }
            },
            { field: 'menuName',  title: '名称' },
            // {field: 'id', title: '编号', sortable: true, align: 'center'},
            // {field: 'pid', title: '所属上级'},
            { field: 'isFrozen',  title: '状态', sortable: true,  align: 'center', formatter: 'statusFormatter'  },
            { field: 'menuUrl', title: '权限值'  },
            { field: 'menuType', title: '类型' ,formatter:'typeFormatter' },
            { field: 'operate', title: '操作', align: 'center', events : operateEvents, formatter: 'operateFormatter' }
        ],

        // bootstrap-table-treegrid.js 插件配置 -- start
        //在哪一列展开树形
        treeShowField: 'menuName',
        //指定父id列
        parentIdField: 'pid',

        onResetView: function(data) {
            //console.log('load');
            $table.treegrid({
                initialState: 'collapsed',// 所有节点都折叠
                // initialState: 'expanded',// 所有节点都展开，默认展开
                treeColumn: 1,
                // expanderExpandedClass: 'glyphicon glyphicon-minus',  //图标样式
                // expanderCollapsedClass: 'glyphicon glyphicon-plus',
                onChange: function() {
                    $table.bootstrapTable('resetWidth');
                }
            });

            //只展开树形的第一级节点
            $table.treegrid('getRootNodes').treegrid('expand');

        },
        onCheck:function(row){
            var datas = $table.bootstrapTable('getData');
            // 勾选子类
            selectChilds(datas,row,"id","pid",true);

            // 勾选父类
            selectParentChecked(datas,row,"id","pid");

            // 刷新数据
            $table.bootstrapTable('load', datas);
        },

        onUncheck:function(row){
            var datas = $table.bootstrapTable('getData');
            selectChilds(datas,row,"id","pid",false);
            $table.bootstrapTable('load', datas);
        },
        // bootstrap-table-treetreegrid.js 插件配置 -- end
    });
});

// 格式化按钮
function operateFormatter(value, row, index) {
    return [
        '<button type="button" class="RoleOfadd btn btn-xs btn-primary " style="margin-right:15px;"><i class="fa fa-plus" ></i>&nbsp;新增</button>',
        '<button type="button" class="RoleOfedit btn btn-xs btn-info " style="margin-right:15px;"><i class="fa fa-pencil-square-o" ></i>&nbsp;编辑</button>',
        '<button type="button" class="RoleOfdelete btn btn-xs btn-danger" style="margin-right:15px;"><i class="fa fa-trash-o" ></i>&nbsp;删除</button>'
    ].join('');
}
// 格式化类型
function typeFormatter(value, row, index) {
    if (value === 1) {  return '菜单';  }
    if (value === 2) {  return '按钮'; }
    if (value === 3) {  return '接口'; }
    return '-';
}
// 格式化状态
function statusFormatter(value, row, index) {
    if (value ===0) {
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
function selectChilds(datas,row,id,pid,checked) {
    for(var i in datas){
        if(datas[i][pid] == row[id]){
            datas[i].check=checked;
            selectChilds(datas,datas[i],id,pid,checked);
        };
    }
}

function selectParentChecked(datas,row,id,pid){
    for(var i in datas){
        if(datas[i][id] == row[pid]){
            datas[i].check=true;
            selectParentChecked(datas,datas[i],id,pid);
        };
    }
}

function test() {
    var selRows = $table.bootstrapTable("getSelections");
    if(selRows.length == 0){
        layerAlert("请至少选择一行");
        return;
    }

    var postData = "";
    $.each(selRows,function(i) {
        postData +=  this.id;
        if (i < selRows.length - 1) {
            postData += "， ";
        }
    });
    layerAlert("你选中行的 id 为："+postData);

}

function add(id) {
    layerAlert("add 方法 , id = " + id);
}
function del(id) {
    layerAlert("del 方法 , id = " + id);
}
function update(id) {
    layerAlert("update 方法 , id = " + id);
}


