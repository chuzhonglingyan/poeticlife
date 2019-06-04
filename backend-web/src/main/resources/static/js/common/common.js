var rootPath = "";

var baseURL = "../";


/**
 * 把对象转换为json
 * @return {string}
 */
var toJson = function (data) {
    return JSON.stringify(data);
};


var getForm = function (url, successcallback) {
    getFormFull(url, successcallback, null);
};

var getFormFull = function (url, successCallback, failureCallback) {
    $.ajax({
        type: 'get',
        url: url,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8"
    }).success(function (data) {
        console.debug(data);
        if (data.success) {
            successCallback(data.data);
        } else {
            if (failureCallback != null) {
                failureCallback(data.message);
            }
        }
    }).error(function (msg) {
        if (failureCallback != null) {
            failureCallback(msg);
        }
        console.error("request failed:" + msg);
    });
};

var postForm = function (url, requestBody, successcallback) {
    postFormFull(url, requestBody, successcallback, null);
};

var postFormFull = function (url, requestBody, successCallback, failureCallback) {
    //loading层
    var index = layer.load(1, {
        shade: [0.1, '#fff'] //0.1透明度的白色背景
    });
    $.ajax({
        type: 'post',
        url: url,
        data: requestBody,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8"
    }).success(function (data) {
        console.debug(data);
        if (data.success) {
            successCallback(data.data);
        } else {
            if (failureCallback != null) {
                failureCallback(data.message);
            }
        }
        layer.close(index);
    }).error(function (xhr) {
        if (failureCallback != null) {
            failureCallback(xhr.statusText);
        }
        layer.close(index);
        console.debug("request failed： " + xhr.status + " " + xhr.statusText);
    });
};

var postJson = function (url, requestBody, successCallback, failureCallback) {
    postJsonFull(url, toJson(requestBody), successCallback, failureCallback);
};


var postJsonFull = function (url, requestBody, successCallback, failureCallback) {
    $.ajax({
        type: 'post',
        url: url,
        data: requestBody,
        contentType: 'application/json'
    }).success(function (data) {
        console.debug(data);
        if (data.success) {
            successCallback(data);
        } else {
            if (failureCallback != null) {
                failureCallback(data.message);
            }
        }
    }).error(function (msg) {
        if (failureCallback != null) {
            failureCallback(msg);
        }
        console.error("request failed:" + msg);
    });
};

/**
 * 重定向页面
 * @param path
 */
function gotoPage(path) {
    window.location.href = baseURL + path;
}

// 新开页签
function addTP(t, i) {
    var a = 0,
        n = !0;
    if (void 0 === t || 0 === $.trim(t).length) return !1;
    if ($(".J_menuTab", window.parent.document).each(function () {
        return $(this).data("id") === t ? ($(this).hasClass("active") || ($(this).addClass("active").siblings(".J_menuTab").removeClass("active"), openTabPage(this), $(".J_mainContent .J_iframe", window.parent.document).each(function () {
            return $(this).data("id") === t ? ($(this).show().siblings(".J_iframe").hide(), !1) : void 0
        })), n = !1, !1) : void 0
    }), n) {
        var s = '<a href="javascript:;" class="active J_menuTab",data-index="' + i + '" data-id="' + t + '">' + i + ' <i   class="fa fa-refresh"    style="margin-right: 5px;margin-left: 5px;"></i><i class="fa fa-times-circle"></i></a>';
        $(".J_menuTab", window.parent.document).removeClass("active");
        var r = '<iframe class="J_iframe" name="iframe' + a + '" width="100%" height="100%" src="' + t + '" frameborder="0" data-id="' + t + '" seamless></iframe>';
        $(".J_mainContent", window.parent.document).find("iframe.J_iframe").hide().parents(".J_mainContent").append(r);
        $(".J_menuTabs .page-tabs-content", window.parent.document).append(s);
        openTabPage($(".J_menuTab.active"));
    }
    return !1
}


// onclick="addTP(\''+t+'\',\''+i+'\')"

function openTabPage(e) {
    var a = switchTabPage($(e).prevAll()),
        i = switchTabPage($(e).nextAll()),
        n = switchTabPage($(".content-tabs", window.parent.document).children().not(".J_menuTabs")),
        s = $(".content-tabs", window.parent.document).outerWidth(!0) - n,
        r = 0;
    if ($(".page-tabs-content", window.parent.document).outerWidth() < s) r = 0;
    else if (i <= s - $(e).outerWidth(!0) - $(e).next().outerWidth(!0)) {
        if (s - $(e).next().outerWidth(!0) > i) {
            r = a;
            for (var o = e; r - $(o).outerWidth() > $(".page-tabs-content", window.parent.document).outerWidth() - s;) r -= $(o).prev().outerWidth(),
                o = $(o).prev()
        }
    } else a > s - $(e).outerWidth(!0) - $(e).prev().outerWidth(!0) && (r = a - $(e).prev().outerWidth(!0));
    $(".page-tabs-content", window.parent.document).animate({
            marginLeft: 0 - r + "px"
        },
        "fast")
}

function switchTabPage(t) {
    var e = 0;
    return $(t).each(function () {
        e += $(this).outerWidth(!0)
    }), e
}


function refreshPage(t, i) {
    var a = 0,
        n = !0;
    if (void 0 === t || 0 === $.trim(t).length) return !1;
    if ($(".J_menuTab", window.parent.document).each(function () {
        return $(this).data("id") === t ? ($(this).hasClass("active") || ($(this).addClass("active").siblings(".J_menuTab").removeClass("active"), openTabPage(this), $(".J_mainContent .J_iframe", window.parent.document).each(function () {
            return $(this).data("id") === t ? ($(this).show().siblings(".J_iframe").hide(), !1) : void 0
        })), $('.J_iframe[data-id="' + t + '"]', window.parent.document).attr('src', t), n = !1 , !1) : void 0
    }), n) {
        var s = '<a href="javascript:;" class="active J_menuTab" data-id="' + t + '">' + i + ' <i class="fa fa-refresh" style="margin-right: 5px;margin-left: 5px;"></i><i class="fa fa-times-circle"></i></a>';
        $(".J_menuTab", window.parent.document).removeClass("active");
        var r = '<iframe class="J_iframe" name="iframe' + a + '" width="100%" height="100%" src="' + t + '" frameborder="0" data-id="' + t + '" seamless></iframe>';
        $(".J_mainContent", window.parent.document).find("iframe.J_iframe").hide().parents(".J_mainContent").append(r);
        $(".J_menuTabs .page-tabs-content", window.parent.document).append(s);
        openTabPage($(".J_menuTab.active"));
    }
    return !1
}

// 关闭页签
function closeByUrl(url) {
    $('.J_menuTab[data-id="' + url + '"]', window.parent.document).remove();
    $('.J_iframe[data-id="' + url + '" ]', window.parent.document).remove();
}

function escapeHtml(text) {
    if (isEmpty(text)){
        return '';
    }
    var map = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#039;',
        " ": '&nbsp;'
    };
    return text.replace(/[&<>"' ]/g, function (m) {
        return map[m];
    });
}


function formatStrDate(value) {
    if (value === undefined) {
        return "";
    } else {
        return formatDate(value, 'yyyy-MM-dd HH:mm:ss');
    }
}


/**
 * 格式化时间
 * @param time
 * @param format
 * @returns {*}
 */
function formatDate(time, format) {
    var t = new Date(time);
    var tf = function (i) {
        return (i < 10 ? '0' : '') + i
    };
    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function (a) {
        switch (a) {
            case 'yyyy':
                return tf(t.getFullYear());
            case 'MM':
                return tf(t.getMonth() + 1);
            case 'dd':
                return tf(t.getDate());
            case 'HH':
                return tf(t.getHours());
            case 'mm':
                return tf(t.getMinutes());
            case 'ss':
                return tf(t.getSeconds());
        }
    })
}



function hideModalListener(modal,listener){
    modal.on('hidden.bs.modal',function() {
        if (listener !=null){
            listener();
        }
    });
}

function showModalListener(modal,listener){
    modal.on('show.bs.modal',function() {
        if (listener !=null){
            listener();
        }
    });
}

function setModalTitle(modal, title) {
    modal.find('.modal-title').text(title);
}

function showModal(modal) {
    modal.modal('show')
}

function hideModal(modal) {
    modal.modal('hide')
}

function isEmpty(obj) {
    if (obj === null) return true;
    if (typeof obj === 'undefined') {
        return true;
    }
    if (typeof obj === 'string') {
        if (obj === "") {
            return true;
        }
        var reg = new RegExp("^([ ]+)|([　]+)$");
        return reg.test(obj);
    }
    return false;
}

function layerAlert(msg) {
    //信息框-例1
    layer.alert(msg, {icon: 6});
}


function formatText(text) {
    if (isEmpty(text)) {
        return "";
    } else {
        if (text.length > 10) {
            return '<span title =' + escapeHtml(text) + '>' + escapeHtml(text).substring(0, 10) + "..." + '</span>';
        } else {
            return text;
        }
    }
}


function formatStatus(value){
    if (value === 1) {
        return "启用";
    } else {
        return "冻结";
    }
}


function disabledInput(view,flag) {
    view.attr("disabled",flag);
}

function readonlyInput(view,flag) {
    view.attr("readonly",flag);
}