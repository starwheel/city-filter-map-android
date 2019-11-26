## Description
This is an Android app that shows a list of cities, filters them by a given prefix string and shows them on the map.

There is a list of cities containing around 200k entries in JSON format. Each entry contains the following information:

```
{
    "country":"UA",
    "name":"Hurzuf",
    "_id":707860,
    "coord":{
            "lon":34.283333,
        "lat":44.549999
    }
}
```
     
* The UI is dynamic, when in portrait mode different screens are used for the list and map but when in landscape mode, a single screen is displayed:

| Portrait List | Portrait Map | Dual Pane (mMster-detail) |
|      :---:    |     :---:    |     :---:    |
| <img width="90%" vspace="10" src="https://github.com/starwheel/city-filter-map-android/raw/master/mockups/portrait_list.jpg">  | <img width="90%" vspace="10" src="https://github.com/starwheel/city-filter-map-android/raw/master/mockups/portrait_map.jpg">  | <img width="90%" vspace="10" src="https://github.com/starwheel/city-filter-map-android/raw/master/mockups/landscape_master_detail.jpg">  |

## Requirements

- Compatible with Android 4.1+
- A Google Maps API key.

## Build the Project

**Note:** First you will need to set your Google Maps API key in google_maps_key.xml.

    # Build the app
    $ ./gradlew build

    # Run the tests
    $ ./gradlew test

    # Run the app
    $ Open the project with Android Studio, let it build the project and hit Run


