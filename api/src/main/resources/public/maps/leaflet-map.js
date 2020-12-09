//See post: http://asmaloney.com/2014/01/code/creating-an-interactive-map-with-leaflet-and-openstreetmap/

let lat = 39.8283;
let lon = -98.5795;
map = L.map('map').setView([lat, lon], 4);
L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors',
    maxZoom: 10,
}).addTo(map);

$(document).ready(function () {
    $.getJSON('/data', function (results) {
        for (let i = 0; i < results.length; ++i) {
            if (results[i].Lat != "" && results[i].Long_ != "") {
                L.marker([results[i].Lat, results[i].Long_]).addTo(map)
                    .bindPopup(results[i].Province_State + "<br/>" + "Deaths: " + results[i].Total_Deaths);
            }
        }
    });
})

/*
for (let i = 0; i < markers.length; ++i) {
    if (markers[i].Lat != "" && markers[i].Long_ != "") {
        L.marker([markers[i].Lat, markers[i].Long_]).addTo(map)
            .bindPopup(markers[i].Province_State + "<br/>" + "Deaths: " + markers[i].Total_Deaths);
    }
}
 */