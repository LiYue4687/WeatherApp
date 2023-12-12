# WeatherApp
Develop a weather app based on compose. Contains third-party libraries such as Room and Retrofit.

# FrameWork
```
.
├── java
│   └── com
│       └── example
│           └── weatherapp
│               ├── data
│               │   ├── retrofit
│               │   │   └── entity
│               │   └── room
│               │       ├── dao
│               │       ├── database
│               │       ├── entity
│               │       └── repository
│               └── ui
│                   ├── add
│                   ├── theme
│                   └── weather
│                       ├── util
│                       └── widget
└── res
    ├── drawable
    ├── mipmap-anydpi
    ├── mipmap-hdpi
    ├── mipmap-mdpi
    ├── mipmap-xhdpi
    ├── mipmap-xxhdpi
    ├── mipmap-xxxhdpi
    ├── values
    └── xml
```

# Commit History
2023/11/24:basic functions, like use room to save data, use retrofit to get weather data from GaoDe map's Api, and basic ui.

2023/11/27:Change the topBar of WeatherMainsScreen to transparent Row so that it no longer blocks the picture

2023/11/28:An initial database has been added, which records the adcodes and names of all cities.
complete the database init, and changed the database structure.

2023/11/30:Add the screen of manage the cities, accomplish the function of show the cities and slide to delete.

2023/12/3:Add search box of addScreen.

2023/12/5:Beautified the city adding and management page.

2023/12/10:add lottie animation

2023/12/11:
1. Fixed a bug: the main page will not be updated after deleting and adding cities. Put the weatherModel used by multiple pages on the outermost layer of navGraph. Repeatedly building multiple viewmodels will obviously cause errors.
2. Tried using coil to load the background image. (Commented for now, not necessary) (coil uses kotlin's coroutine, which is more suitable for use in compose. coil is lightweight and fast)
3. After deleting a city, the sliding bar on the page is also deleted.
4. add weather animation

2023/12/12:Displaying Search Results Using a Join Query




# TODO List
View
- modifier the WeatherMainsScreen, make it better
- ~~screen of add city to know the weather~~
- ~~screen of manage the cities~~
- add a view to show the position
- Add feedback after adding a city. After adding, the Add button is no longer displayed.

Navigation
- ~~add another navigation to screen of manage the city that want to know the weather~~
- Add display of current page under WeatherMainsScreen page

Animation:
- ~~add weather animation~~
- Set up a topbar that can be scaled.
- Learn nested scrolling.

DataBase
- ~~add the init of database~~
- ~~add migration of database~~
- make database structure more sensible
- ~~Implement the function of obtaining related table data~~

Api
- Change to a more feature-rich API
