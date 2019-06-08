$(document).ready(function () {
    $('.chart').easyPieChart({
        barColor: '#f8ac59',
        //                scaleColor: false,
        scaleLength: 5,
        lineWidth: 4,
        size: 80
    });

    $('.chart2').easyPieChart({
        barColor: '#1c84c6',
        //                scaleColor: false,
        scaleLength: 5,
        lineWidth: 4,
        size: 80
    });

    var d1 = [
        [1262304000000, 1],
        [1264982400000, 100],
        [1267401600000, 1],
        [1270080000000, 200],
        [1272672000000, 1],
        [1275350400000, 100],
        [1277942400000, 1],
        [1280620800000, 300]
    ];
    var d2 = [
        [1262304000000, 100],
        [1264982400000, 1],
        [1267401600000, 150],
        [1270080000000, 1],
        [1272672000000, 230],
        [1275350400000, 1],
        [1277942400000, 150],
        [1280620800000, 10]
    ];

    var data3 = [
        {
            label: "Data 1",
            data: d1,
            color: '#23c6c8'
        },
        {
            label: "Data 2",
            data: d2,
            color: '#1ab394'
        }
    ];
    $.plot($("#flot-chart3"), data3, {
        xaxis: {
            tickDecimals: 0
        },
        series: {
            lines: {
                show: true,
                fill: true,
                fillColor: {
                    colors: [{
                        opacity: 1
                    }, {
                        opacity: 1
                    }]
                },
            },
            curvedLines: {
                active: true,
                fit: true,
                apply: true
            },
            points: {
                width: 0.1,
                show: false
            },
        },
        grid: {
            show: false,
            borderWidth: 0
        },
        legend: {
            show: false,
        }
    });


    var mapData = {
        "US": 298,
        "SA": 200,
        "DE": 220,
        "FR": 540,
        "CN": 120,
        "AU": 760,
        "BR": 550,
        "IN": 200,
        "GB": 120,
    };

    $('#world-map').vectorMap({
        map: 'world_mill_en',
        backgroundColor: "transparent",
        regionStyle: {
            initial: {
                fill: '#e4e4e4',
                "fill-opacity": 0.9,
                stroke: 'none',
                "stroke-width": 0,
                "stroke-opacity": 0
            }
        },

        series: {
            regions: [{
                values: mapData,
                scale: ["#1ab394", "#22d6b1"],
                normalizeFunction: 'polynomial'
            }]
        },
    });
});