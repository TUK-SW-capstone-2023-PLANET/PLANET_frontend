package com.example.planet.data.map

import com.example.planet.R

sealed class TrashImage() {
    data class Battery(val trash: String = "배터리", val image: Int = R.drawable.battery): TrashImage()
    data class Bottle(val trash: String = "병", val image: Int = R.drawable.bottle): TrashImage()
    data class Can(val trash: String = "캔", val image: Int = R.drawable.can): TrashImage()
    data class GeneralWaste(val trash: String = "일반 쓰레기", val image: Int = R.drawable.generalwaste): TrashImage()
    data class Glass(val trash: String = "유리", val image: Int = R.drawable.glass): TrashImage()
    data class PaperCup(val trash: String = "종이컵", val image: Int = R.drawable.papercup): TrashImage()
    data class Paper(val trash: String = "종이", val image: Int = R.drawable.paper): TrashImage()
    data class Pet(val trash: String = "페트", val image: Int = R.drawable.pet): TrashImage()
    data class Plastic(val trash: String = "플라스틱", val image: Int = R.drawable.plastic): TrashImage()
    data class PlasticBag(val trash: String = "비닐 쓰레기", val image: Int = R.drawable.plasticbag): TrashImage()
    data class Styrofoam(val trash: String = "스티로폼", val image: Int = R.drawable.styrofoam): TrashImage()
}

data class Trash(
    val name: String,
    val image: Int,
    var count: Int
)