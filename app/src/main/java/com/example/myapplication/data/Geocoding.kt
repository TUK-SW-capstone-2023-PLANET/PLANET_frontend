package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("name")
    val name: String,
    @SerializedName("code")
    val code: Code,
    @SerializedName("region")
    val region: Region
)

data class Code(
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("mappingId")
    val mappingId: String
)

data class Region(
    @SerializedName("area0")
    val area0: Area,
    @SerializedName("area1")
    val area1: Area,
    @SerializedName("area2")
    val area2: Area,
    @SerializedName("area3")
    val area3: Area,
    @SerializedName("area4")
    val area4: Area
)

data class Area(
    @SerializedName("name")
    val name: String,
    @SerializedName("coords")
    val coords: Coords
)

data class Coords(
    @SerializedName("center")
    val center: Center
)

data class Center(
    @SerializedName("crs")
    val crs: String,
    @SerializedName("x")
    val x: Float,
    @SerializedName("y")
    val y: Float
)


data class Geocoding(
    @SerializedName("status")
    val status: Status,
    @SerializedName("results")
    val results: List<Result>
)

data class Status(
    @SerializedName("code")
    val code: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("message")
    val message: String
)
