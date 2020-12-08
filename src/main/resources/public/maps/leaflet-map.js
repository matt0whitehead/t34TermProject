// See post: http://asmaloney.com/2014/01/code/creating-an-interactive-map-with-leaflet-and-openstreetmap/

let lat = 39.8283;
let lon = -98.5795;
map = L.map('map').setView([lat, lon], 4);
L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors',
    maxZoom: 10,
}).addTo(map);

let stateMarker;

for (let i=0; i < markers.length; ++i) {
    if(markers[i].Lat != "" && markers[i].Long_ != "") {
        stateMarker = L.marker([markers[i].Lat, markers[i].Long_]).addTo(map)
        stateMarker.bindPopup(markers[i].Province_State + "<br/>" + "Deaths:" + markers[i].Total_Deaths);
    }
}