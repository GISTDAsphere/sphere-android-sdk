package th.or.gistda.sphere.tutorial

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import th.or.gistda.sphere.SphereMapViewModel
import th.or.gistda.sphere.SphereMap

val idBasicMarker = System.currentTimeMillis()

@Composable
fun AddMarker(
    button: String,
    args: List<Map<String, Any>>,
    id: Long = System.currentTimeMillis()
) {
    val vm = viewModel<SphereMapViewModel>()
    Button(onClick = {
        vm.call.value = mapOf(
            SphereMapViewModel.METHOD to "Overlays.add",
            SphereMapViewModel.ARGS to SphereMap.SphereObject(
                type = "Marker",
                args = args,
                id = id
            )
        )
    }) {
        Text(text = button)
    }
}

@Composable
fun AddBasicMarker() {
    AddMarker(
        button = "Basic",
        args = listOf(
            mapOf(
                "lon" to 100.56,
                "lat" to 13.74
            )
        ),
        id = idBasicMarker
    )
}

@Composable
fun RemoveMarker() {
    val vm = viewModel<SphereMapViewModel>()
    Button(onClick = {
        vm.call.value = mapOf(
            SphereMapViewModel.METHOD to "Overlays.remove",
            SphereMapViewModel.ARGS to SphereMap.SphereObject(
                type = "Marker",
                id = idBasicMarker
            )
        )
    }) {
        Text(text = "Remove")
    }
}

@Composable
fun ClearMarker() {
    val vm = viewModel<SphereMapViewModel>()
    Button(onClick = {
        vm.call.value = mapOf(SphereMapViewModel.METHOD to "Overlays.clear")
    }) {
        Text(text = "Clear")
    }
}

@Composable
fun AddOption1Marker() {
    AddMarker(
        button = "Option 1",
        args = listOf(
            mapOf(
                "lon" to 101.2,
                "lat" to 12.8
            ),
            mapOf(
                "title" to "Marker",
                "detail" to "Drag me",
                "visibleRange" to mapOf("min" to 7, "max" to 9),
                "draggable" to true
            )
        )
    )
}

@Composable
fun AddOption2Marker() {
    AddMarker(
        button = "Option 2",
        args = listOf(
            mapOf(
                "lon" to 99.35,
                "lat" to 14.25
            ),
            mapOf(
                "title" to "Custom Marker",
                "icon" to mapOf(
                    "html" to "Marker",
                    "offset" to mapOf("x" to 18, "y" to 21)
                ),
                "popup" to mapOf(
                    "html" to "<div style='background: #eeeeff;'>popup</div>"
                )
            )
        )
    )
}

@Composable
fun AddOption3Marker() {
    AddMarker(
        button = "Option 3",
        args = listOf(
            mapOf(
                "lon" to 100.41,
                "lat" to 13.84
            ),
            mapOf(
                "title" to "Rotate Marker",
                "rotate" to 90
            )
        )
    )
}

@Composable
fun AddOptionGeoJsonMarker() {
    AddMarker(
        button = "GeoJson",
        args = listOf(
            mapOf(
                "type" to "Feature",
                "properties" to mutableMapOf<String, Any>(),
                "geometry" to mapOf(
                    "type" to "Point",
                    "coordinates" to listOf(100.49477577209473, 13.752891404314328)
                ),
            )
        )
    )
}
