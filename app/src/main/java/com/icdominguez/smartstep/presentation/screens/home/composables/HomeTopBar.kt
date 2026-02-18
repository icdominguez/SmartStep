package com.icdominguez.smartstep.presentation.screens.home.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.R
import com.icdominguez.smartstep.presentation.designsystem.LocalSmartStepTypography
import com.icdominguez.smartstep.presentation.designsystem.SmartStepTheme
import com.icdominguez.smartstep.presentation.designsystem.TextPrimary

@Composable
fun HomeTopBar(
    onShowDrawerMenuClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 6.dp,
                horizontal = 16.dp
            ),
    ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(34.dp)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .clickable(
                        onClick = { onShowDrawerMenuClick() }
                    ),
                painter = painterResource(R.drawable.ic_menu_burger),
                contentDescription = "menu drawer icon"
            )

        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = stringResource(R.string.app_name),
            style = LocalSmartStepTypography.current.titleMedium,
            color = TextPrimary,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeTopBarPreview() {
    SmartStepTheme {
        HomeTopBar(
            onShowDrawerMenuClick = {}
        )
    }
}