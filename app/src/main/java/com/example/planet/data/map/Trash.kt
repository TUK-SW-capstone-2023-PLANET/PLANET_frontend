package com.example.planet.data.map

import com.example.planet.R

sealed class TrashImage() {
    data class Battery(val trash: String = "배터리", val image: Int = R.drawable.battery, val score: Int = 5): TrashImage()
    data class Bottle(val trash: String = "병", val image: Int = R.drawable.bottle, val score: Int = 40): TrashImage()
    data class Can(val trash: String = "캔", val image: Int = R.drawable.can, val score: Int = 20): TrashImage()
    data class GeneralWaste(val trash: String = "일반 쓰레기", val image: Int = R.drawable.generalwaste, val score: Int = 50): TrashImage()
    data class Glass(val trash: String = "유리", val image: Int = R.drawable.glass, val score: Int = 40): TrashImage()
    data class PaperCup(val trash: String = "종이컵", val image: Int = R.drawable.papercup, val score: Int = 15): TrashImage()
    data class Paper(val trash: String = "종이", val image: Int = R.drawable.paper, val score: Int = 20): TrashImage()
    data class Pet(val trash: String = "페트", val image: Int = R.drawable.pet, val score: Int = 20): TrashImage()
    data class Plastic(val trash: String = "플라스틱", val image: Int = R.drawable.plastic, val score: Int = 20): TrashImage()
    data class PlasticBag(val trash: String = "비닐 쓰레기", val image: Int = R.drawable.plasticbag, val score: Int = 10): TrashImage()
    data class Styrofoam(val trash: String = "스티로폼", val image: Int = R.drawable.styrofoam, val score: Int = 30): TrashImage()
}

data class Trash(
    val name: String,
    val image: Int,
    var count: Int
)