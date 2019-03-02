var rootPath = "";

var baseURL = "/backend";


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
        }else {
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
    $.ajax({
        type: 'post',
        url: url,
        data: requestBody,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8"
    }).success(function (data) {
        console.debug(data);
        if (data.success) {
            successCallback(data.data);
        }else {
            if (failureCallback != null) {
                failureCallback(data.message);
            }
        }
    }).error(function (xhr) {
        if (failureCallback != null) {
            failureCallback(xhr.statusText);
        }
        console.debug("request failed： " + xhr.status + " " + xhr.statusText);
    });
};

var postJson = function (url, requestBody, successcallback) {
    postJsonFull(url, toJson(requestBody), successcallback, null);
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
        }else {
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

var swalWarning = function (text) {
    swal({
        title: "确认删除?",
        text: text,
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        closeOnConfirm: false,
        closeOnCancel: false
    }, function (isConfirm) {
        if (isConfirm) {
            swal("Deleted!", "Your imaginary file has been deleted.", "success");
        } else {
            swal("Cancelled", "Your imaginary file is safe :)", "error");
        }
    });

};

/**
 * 重定向页面
 * @param path
 */
function gotoPage(path) {
    window.location.href=baseURL+path;
}