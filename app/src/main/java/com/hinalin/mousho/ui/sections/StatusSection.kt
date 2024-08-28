package com.hinalin.mousho.ui.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hinalin.mousho.BatteryInfo
import com.hinalin.mousho.ui.components.StatusCard

@Composable
fun StatusSection(
    batteryInfo: BatteryInfo,
    isPortrait: Boolean,
) {
    if (isPortrait) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                StatusCard(
                    title = "Status",
                    value = batteryInfo.status,
                    modifier = Modifier.weight(1f),
                )
                StatusCard(
                    title = "Health",
                    value = batteryInfo.health,
                    modifier = Modifier.weight(1f),
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                StatusCard(
                    title = "Plugged In",
                    value = if (batteryInfo.isPluggedIn) "Yes" else "No",
                    modifier = Modifier.weight(1f),
                )
                StatusCard(
                    title = "Voltage",
                    value = "${String.format("%.1f", batteryInfo.voltage)}V",
                    modifier = Modifier.weight(1f),
                )
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            StatusCard(
                title = "Status",
                value = batteryInfo.status,
                modifier = Modifier.weight(1f),
            )
            StatusCard(
                title = "Health",
                value = batteryInfo.health,
                modifier = Modifier.weight(1f),
            )
            StatusCard(
                title = "Plugged In",
                value = if (batteryInfo.isPluggedIn) "Yes" else "No",
                modifier = Modifier.weight(1f),
            )
            StatusCard(
                title = "Voltage",
                value = "${String.format("%.1f", batteryInfo.voltage)}V",
                modifier = Modifier.weight(1f),
            )
        }
    }
}
