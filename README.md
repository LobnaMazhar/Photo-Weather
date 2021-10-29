# Photo-Weather

# Overview
Photo Weather is an app to take photos for current weather, get weather info and add it to the image automatically according to user's location

You can also share your photo to facebook or twitter, and view old taken photos from the gallery in home screen.

# Requirements
To be able to run the code on your machine you need to register for the OpenWeatherMap API, generate api key then add it to local.properties file as OPEN_WEATHER_API_KEY

# Coding Architecture
The Project is coded in Kotlin language following MVVM architecture design pattern, using Kotlin Coroutines, LiveData, Room & CameraX. The project is divided into View (UI), Viewmodel, Model, Repository & Datasource Layers.
