package com.example.movieappcompose.utils

import com.example.movieappcompose.models.Advert

class MockData {

    val data = listOf<Advert>(
        Advert("ARE YOU READY?", "IN ALL CINEMA OF KZ", "https://www.shutterstock.com/image-vector/red-banner-coming-soon-260nw-1024754041.jpg"),
        Advert("Let Coca Cola in your Life!", "Make movies better with Cola!", "https://d2td6mzj4f4e1e.cloudfront.net/wp-content/uploads/sites/9/2019/11/The-Original-Taste-of-Christmas-620x330.png"),
        Advert("EVENT", "Come and win!", "https://supervalu.ie/thumbnail/1440x550/var/files/competitions/AB-win-landing-1440x550-LS.jpg"),
        Advert("HAPPY EASTER", "Celebrate with us and win!", "https://supervalu.ie/thumbnail/1440x550/var/files/competitions/CT_Birdseye-1440x550--1-.png"),
        Advert("BLACK FRIDAY IS COMING", "You better get ready", "https://cdn.agriland.ie/uploads/2018/11/Black-Friday-Offer-Desktop-Banners-1440x550-24.jpg")
    )
}