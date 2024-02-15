package com.example.weather.data.models

class GeoCode : ArrayList<GeoCodeItem>()

data class GeoCodeItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String
)

data class LocalNames(
    val af: String,
    val ar: String,
    val ascii: String,
    val az: String,
    val bg: String,
    val ca: String,
    val da: String,
    val de: String,
    val el: String,
    val en: String,
    val eu: String,
    val fa: String,
    val feature_name: String,
    val fi: String,
    val fr: String,
    val gl: String,
    val he: String,
    val hi: String,
    val hr: String,
    val hu: String,
    val id: String,
    val `it`: String,
    val ja: String,
    val la: String,
    val lt: String,
    val mk: String,
    val nl: String,
    val no: String,
    val pl: String,
    val pt: String,
    val ro: String,
    val ru: String,
    val sk: String,
    val sl: String,
    val sr: String,
    val th: String,
    val tr: String,
    val vi: String,
    val zu: String
)