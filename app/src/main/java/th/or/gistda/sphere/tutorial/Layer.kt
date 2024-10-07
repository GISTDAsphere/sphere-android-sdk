package th.or.gistda.sphere.tutorial

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import th.or.gistda.sphere.SphereMap
import th.or.gistda.sphere.SphereMapViewModel

@Composable
fun SetLayer(
    button: String,
    name: String,
    args: Map<String, Any>
) {
    val vm = viewModel<SphereMapViewModel>()
    Button(onClick = {
        vm.call.value = mapOf(
            SphereMapViewModel.METHOD to "Layers.add",
            SphereMapViewModel.ARGS to SphereMap.SphereObject(
                type = "Layer",
                args = listOf(name, args)
            )
        )
    }) {
        Text(text = button)
    }
}

@Composable
fun SetWMSLayer() {
    SetLayer(
        button = "WMS",
        name = "roadnet2:Road_FGDS",
        args = mapOf(
            "type" to SphereMap.SphereStatic("LayerType", "WMS"),
            "url" to "https://apix.longdo.com/vector/test-tile.php",
            "zoomRange" to mapOf("min" to 1, "max" to 9),
            "refresh" to 180,
            "opacity" to 0.5,
            "weight" to 10,
            "bound" to mapOf(
                "minLon" to 100,
                "minLat" to 10,
                "maxLon" to 105,
                "maxLat" to 20
            )
        )
    )
}

@Composable
fun SetTMSLayer() {
    SetLayer(
        button = "TMS",
        name = "roadnet2:Road_FGDS@EPSG:900913@png",
        args = mapOf(
            "type" to SphereMap.SphereStatic("LayerType", "TMS"),
            "url" to "https://apix.longdo.com/vector/test-tile.php?tms=",
            "zoomOffset" to 0
        )
    )
}

@Composable
fun SetWMTSLayer() {
    SetLayer(
        button = "WMTS",
        name = "roadnet2:Road_FGDS",
        args = mapOf(
            "type" to SphereMap.SphereStatic("LayerType", "WMTS"),
            "url" to "https://apix.longdo.com/vector/test-tile.php",
            "srs" to "EPSG:900913",
            "tileMatrix" to "(z) => 'EPSG :900913:' + z"
        )
    )
}

@Composable
fun SetWMTSRESTLayer() {
    SetLayer(
        button = "WMTS REST",
        name = "bluemarble_terrain",
        args = mapOf(
            "type" to SphereMap.SphereStatic("LayerType", "WMTS_REST"),
            "url" to "https://ms.longdo.com/mapproxy/wmts",
            "srs" to "GLOBAL_WEBMERCATOR"
        )
    )
}
