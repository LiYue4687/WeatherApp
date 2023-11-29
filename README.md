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


# TODO List
View
- modifier the WeatherMainsScreen, make it better
- screen of add city to know the weather
- screen of manage the cities

Navigation
- add another navigation to screen of manage the city that want to know the weather

Animation:
- add weather animation

DataBase
- ~~add the init of database~~
- add migration of database

Api
- Change to a more feature-rich API
