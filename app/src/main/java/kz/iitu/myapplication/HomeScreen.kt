package kz.iitu.myapplication

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kz.iitu.myapplication.HomeContract.SideEffect
import kz.iitu.myapplication.HomeContract.UiAction
import kz.iitu.myapplication.HomeContract.UiState
import kotlinx.coroutines.flow.Flow
import kz.iitu.myapplication.mvi.CollectSideEffect

@Composable
fun HomeScreen() {
    val vm = remember { HomeViewModel() }

    /*
    Use this instead of the unpack function if you want

    val uiState by vm.uiState.collectAsState()
    val sideEffect = vm.sideEffect
    val onAction = vm::onAction
    */

    val (uiState, onAction, sideEffect) = vm.unpack()
    HomeScreen(uiState, sideEffect, onAction)
}

@Composable
fun HomeScreen(
    uiState: UiState,
    sideEffect: Flow<SideEffect>,
    onAction: (UiAction) -> Unit,
) {
    val context = LocalContext.current

    CollectSideEffect(sideEffect) {
        when (it) {
            SideEffect.ShowCountCanNotBeNegativeToast -> {
                Toast.makeText(context, "Count can't be less than 0", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold {
        Column(
            modifier = Modifier.padding(it).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Count: ${uiState.count}",
            )
            Row(
                modifier = Modifier.padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Button(onClick = { onAction(UiAction.OnIncreaseCountClick) }) {
                    Text("Increase")
                }
                Button(onClick = { onAction(UiAction.OnDecreaseCountClick) }) {
                    Text("Decrease")
                }
            }
        }
    }
}