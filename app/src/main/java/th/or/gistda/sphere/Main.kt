package th.or.gistda.sphere

import android.app.Application
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import org.json.JSONObject
import th.or.gistda.sphere.ui.theme.MyApplicationTheme

class Main : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val message = remember { mutableStateOf("") }
            MyApplicationTheme(darkTheme = false) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
//                        SetWMSLayer()
//                        SetTMSLayer()
//                        SetWMTSLayer()
//                        SetWMTSRESTLayer()
//                        AddBasicMarker()
//                        RemoveMarker()
//                        ClearMarker()
//                        AddOption1Marker()
//                        AddOption2Marker()
//                        AddOption3Marker()
//                        AddOptionGeoJsonMarker()
//                        AddPolyline()
//                        AddDashline()
//                        AddPolygon()
//                        AddCircle()
//                        AddDot()
//                        AddDonut()
//                        AddRectangle()
//                        GetGeometryArea()
//                        GetFormatDistancePolyline()
                    }
                    SphereMapView(message)
                }
            }
            if (message.value.isNotBlank()) {
                Toast
                    .makeText(
                        LocalContext.current,
                        message.value,
                        Toast.LENGTH_SHORT
                    )
                    .show()
                message.value = ""
            }

        }
    }

}

@Composable
fun SphereMapView(message: MutableState<String>) {
    val vm = viewModel<SphereMapViewModel>()
    val const = vm.const.observeAsState().value
    val call = vm.call.observeAsState().value
    val objectCall = vm.objectCall.observeAsState().value
    AndroidView(
        factory = {
            SphereMap(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
            }
        },
        update = {
            const?.run {
                SphereMap.API_KEY = get("apiKey")
                SphereMap.PACKAGE_NAME = get("packageName")
                SphereMap.LAYER = SphereMap.SphereStatic("Layers", "HYBRID")
                SphereMap.ZOOM = 10
                SphereMap.ZOOM_RANGE = JSONObject()
                    .put("min", 10)
                    .put("max", 15)
                SphereMap.LOCATION = JSONObject()
                    .put("lon", 100.56)
                    .put("lat", 13.74)
                SphereMap.UI = SphereMap.SphereStatic("UiComponent", "None")
                SphereMap.LAST_VIEW = true
                SphereMap.LANGUAGE = "en"
                it.load()
                vm.const.value = null
            }
            call?.run {
                it.call(
                    get(SphereMapViewModel.METHOD) as String,
                    listOf(get(SphereMapViewModel.ARGS))
                ) {
                    message.value = it
                }
                vm.call.value = null
            }
            objectCall?.run {
                it.objectCall(
                    get(SphereMapViewModel.OBJECT).let { it as JSONObject },
                    get(SphereMapViewModel.METHOD) as String,
                    listOf(get(SphereMapViewModel.ARGS))
                ) {
                    message.value = it
                }
                vm.objectCall.value = null
            }
        }
    )
}

class SphereMapViewModel(app: Application) : AndroidViewModel(app) {

    companion object {

        const val OBJECT = "OBJECT"
        const val METHOD = "METHOD"
        const val ARGS = "ARGS"

    }

    val const = MutableLiveData(
        mapOf(
            "apiKey" to "",
            "packageName" to app.packageName
        )
    )
    val call = MutableLiveData<Map<String, Any>>()
    val objectCall = MutableLiveData<Map<String, Any>>()

}