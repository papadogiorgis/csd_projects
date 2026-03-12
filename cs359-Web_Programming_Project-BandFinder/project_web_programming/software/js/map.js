// js/map.js

//map variables
let map = null;
let markersLayer = null;

//function that converts wgs84 to spherical mercator
function wgs84ToSpherMercator(lon, lat){
    const x = lon * 20037508.34 / 180;
    let y = Math.log(Math.tan((90+lat) * Math.PI / 360)) / (Math.PI / 180);
    y = y * 20037508.34 / 180;
    return [x,y];
}

//function that initializes the map
function initMap(containerId){
    //clear any existing map
    if(map){
        map.destroy();
    }

    //ψρεατε νες μαπ
    map = new OpenLayers.Map(containerId, {
        projection: new OpenLayers.Projection("EPSG:900913"),
        displayProjection: new OpenLayers.Projection("EPSG:4326"),
        units: "m",
        numZoomLevels: 18,
        maxResolution: 156543.0339,
        maxExtent: new OpenLayers.Bounds(-20037508.34, -20037508.34, 20037508.34, 20037508.34)
    });

    //create osm layer
    const osmLayer = new OpenLayers.Layer.OSM("OpenStreetMap");
    //create markers layer
    markersLayer = new OpenLayers.Layer.Markers("Markers");
    map.addLayers([osmLayer, markersLayer]);
    return map;
}

//function that adds marker to the map
function addMarkerToMap(mapObj, lon, lat, title=""){
    if(!mapObj || !markersLayer) return;

    //clear existing markers
    markersLayer.clearMarkers();

    //convert coordinates to spherical mercator
    const mercatorCoord = wgs84ToSpherMercator(lon, lat);

    //create marker
    const size = new OpenLayers.Size(25,41);
    const offset = new OpenLayers.Pixel(-(size.w / 2), -size.h);
    const icon = new OpenLayers.Icon('https://cdnjs.cloudflare.com/ajax/libs/openlayers/2.13.1/img/marker.png', size, offset);
    const marker = new OpenLayers.Marker(new OpenLayers.LonLat(mercatorCoord[0], mercatorCoord[1]), icon);
    markersLayer.addMarker(marker);
    //center map on marker
    mapObj.setCenter(new OpenLayers.LonLat(mercatorCoord[0], mercatorCoord[1]), 15);

    return marker;
}

//function that hides the map
function hideMap(){
    const mapContainer = document.getElementById('map-container');
    const showMapBtn = document.getElementById('show-map-btn');

    mapContainer.style.display = 'none';
    showMapBtn.textContent = 'Show Location on Map';

    //destroy map resources
    if(map){
        map.destroy();
        map = null;
        markersLayer = null;
    }
}

//function that shows map with location
function showMapWithLocation(latitude, longitude, address){
    const mapContainer = document.getElementById('map-container');
    const showMapBtn = document.getElementById('show-map-btn');

    //show map container
    mapContainer.style.display = 'block';

    //initialize map
    const mapObj = initMap('map');

    //add marker
    addMarkerToMap(mapObj, parseFloat(longitude), parseFloat(latitude), address);

    //update button texxt
    showMapBtn.textContent = 'Hide Map';
}

//function to update map button visibility based on address verification
function updateMapButtonVisibility(){
    const showMapBtn = document.getElementById('show-map-btn');
    const latitude = document.getElementById('latitude').value;
    const longitude = document.getElementById('longitude').value;

    if(latitude && longitude){
        showMapBtn.style.display = 'inline-block';
        showMapBtn.disabled = false;
    }else{
        showMapBtn.style.display = 'none';
        hideMap();
    }
}

//function that verifies address with OpenStreetMaps API
function verifyAddress(){
    const country = document.getElementById('country').value;
    const city = document.getElementById('city').value;
    const address = document.getElementById('address').value;
    const verifyBtn = document.getElementById('verify-address-btn');
    const resultDiv = document.getElementById('address-verification-result');
    const showMapBtn = document.getElementById('show-map-btn');

    //check if country is Greece
    if(country !== 'GR'){
        resultDiv.className = 'verification-message error';
        resultDiv.textContent = 'Service is currently available only for Greece.';
        resultDiv.style.display = 'block';
        showMapBtn.style.display = 'none';
        hideMap();
        return;
    }

    //check if all fields have values
    if(!country || !city || !address){
        resultDiv.className = 'verification-message error';
        resultDiv.textContent = 'Please fill in the fields Country, City and Address before verification.';
        resultDiv.style.display = 'block';
        showMapBtn.style.display = 'none';
        hideMap();
        return;
    }

    //disable Verify button during request
    verifyBtn.disabled = true;
    verifyBtn.textContent = 'Verifying in progress';
    resultDiv.style.display = 'none';
    showMapBtn.style.display = 'none';
    hideMap();
    
    //create query
    const query = `${address}, ${city}, ${country}`;
    const encodeQuery = encodeURIComponent(query);

    //call API
    const url=`https://forward-reverse-geocoding.p.rapidapi.com/v1/search?q=${encodeQuery}&format=json`;
    const options = {
        method: 'GET',
        headers: {
            'X-RapidAPI-Key': 'fdca85054cmshaa64c117e38ccc4p13fb03jsn2a91d994b9dc',
            'X-RapidAPI-Host': 'forward-reverse-geocoding.p.rapidapi.com'
        }
    };

    fetch(url, options)
        .then(response =>{
            if(!response.ok){
                throw new Error('Network response wasnt ok');
            }
            return response.json();
        })
        .then(data => {
            //check if i have results
            if(Array.isArray(data) && data.length>0){
                //choose the first result
                const firstResult = data[0];

                //save lat and lon
                document.getElementById('latitude').value = firstResult.lat;
                document.getElementById('longitude').value = firstResult.lon;

                //show success message
                resultDiv.className = 'verification-message success';
                resultDiv.innerHTML = `Address verified successfully!<br>Coordinates: ${firstResult.lat}, ${firstResult.lon}<br>Display Name: ${firstResult.display_name}`;
                resultDiv.style.display = 'block';

                //enable map button
                updateMapButtonVisibility();
            }else{
                //address not found
                document.getElementById('latitude').value = '';
                document.getElementById('longitude').value = '';
                resultDiv.className = 'verification-message error';
                resultDiv.textContent = 'Address not found!';
                resultDiv.style.display = 'block';

                showMapBtn.style.display = 'none';
                hideMap();
            }
        })
        .catch(error => {
            console.error('Error verifying address: ', error);
            document.getElementById('latitude').value = '';
            document.getElementById('longitude').value = '';
            resultDiv.className = 'verification-message error';
            resultDiv.textContent = 'Error verifying address!';
            resultDiv.style.display = 'block';

            showMapBtn.style.display = 'none';
            hideMap();
        })
        .finally(() => {
            //enable button
            verifyBtn.disabled = false;
            verifyBtn.textContent = 'Verify Address';
        });
}

//function to toggle map
function toggleMap(){
    const latitude = document.getElementById('latitude').value;
    const longitude = document.getElementById('longitude').value;
    const address = document.getElementById('address').value;
    const mapContainer = document.getElementById('map-container');

    if(mapContainer.style.display === 'none'){
        if(latitude && longitude){
            showMapWithLocation(latitude, longitude, address);
        }
    }else{
        hideMap();
    }
}

//export initialization for map features
export function createMapFeatures(){
    //event listener for the button that verifies address
    const verifyAddressBtn = document.getElementById('verify-address-btn');
    if(verifyAddressBtn){
        verifyAddressBtn.addEventListener('click', verifyAddress);
    }

    //event listener for the show map button
    const showMapBtn = document.getElementById('show-map-btn');
    if(showMapBtn){
        showMapBtn.addEventListener('click', toggleMap);
    }
    updateMapButtonVisibility(); //initialize map button visibility

    //unverify address, if country, city or adddress changes
    document.getElementById('country').addEventListener('change', function() {
        document.getElementById('address-verification-result').style.display = 'none';
        document.getElementById('latitude').value = '';
        document.getElementById('longitude').value = '';
        updateMapButtonVisibility();
    });

    document.getElementById('city').addEventListener('input', function() {
        document.getElementById('address-verification-result').style.display = 'none';
        document.getElementById('latitude').value = '';
        document.getElementById('longitude').value = '';
        updateMapButtonVisibility();
    });

    document.getElementById('address').addEventListener('input', function() {
        document.getElementById('address-verification-result').style.display = 'none';
        document.getElementById('latitude').value = '';
        document.getElementById('longitude').value = '';
        updateMapButtonVisibility();
    });
}