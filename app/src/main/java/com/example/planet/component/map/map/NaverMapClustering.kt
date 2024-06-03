package com.example.planet.component.map.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.DisposableMapEffect
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import ted.gun0912.clustering.clustering.TedClusterItem
import ted.gun0912.clustering.geometry.TedLatLng
import ted.gun0912.clustering.naver.TedNaverClustering
import com.example.planet.R

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NaverMapClustering(items: List<TrashCanItem> = emptyList()) {

    val context = LocalContext.current
    var clusterManager by remember { mutableStateOf<TedNaverClustering<TrashCanItem>?>(null) }
    DisposableMapEffect(items) { map ->
        if (clusterManager == null) {
            clusterManager =
                TedNaverClustering.with<TrashCanItem>(context, map)
                    .customMarker { clusterItem ->
                        Marker(clusterItem.itemPosition).apply {
                            icon = OverlayImage.fromResource(R.drawable.trash_icon)
                            width = 110
                            height = 110
                        }
                    }
                    .items(items)
                    .make()
        }

//        clusterManager?.addItems(items)
        onDispose {
            clusterManager?.clearItems()
        }
    }
}

data class TrashCanItem(
    val itemPosition: LatLng,
    val trashCanId: Int,
    val itemTitle: String = "",
    val itemSnippet: String = "",
) : TedClusterItem {

    override fun getTedLatLng(): TedLatLng {
        return TedLatLng(
            latitude = itemPosition.latitude,
            longitude = itemPosition.longitude,
        )
    }
}