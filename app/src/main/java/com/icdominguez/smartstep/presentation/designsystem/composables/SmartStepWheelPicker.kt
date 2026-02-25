package com.icdominguez.smartstep.presentation.designsystem.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun <T> SmartStepWheelPicker(
    modifier: Modifier = Modifier,
    items: List<T>,
    itemHeight: Dp = 44.dp,
    visibleCount: Int = 4,
    selectedSlot: Int = 2,
    initialSelectedIndex: Int = 2,
    onSelected: (index: Int, item: T) -> Unit,
    itemContent: @Composable (item: T, isSelected: Boolean) -> Unit
) {
    val bottomSpacers = visibleCount - selectedSlot - 1
    val totalCount = items.size + selectedSlot + bottomSpacers

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = selectedSlot)

    val itemHeightPx = with(LocalDensity.current) { itemHeight.toPx() }
    val viewportHeight = itemHeight * visibleCount

    val selectedLazyIndex by remember {
        derivedStateOf {
            if (totalCount == 0) return@derivedStateOf 0
            val base = listState.firstVisibleItemIndex
            val frac = listState.firstVisibleItemScrollOffset / itemHeightPx
            val raw = base + selectedSlot + if (frac >= 0.5f) 1 else 0
            raw.coerceIn(0, totalCount - 1)
        }
    }

    val selectedIndex by remember {
        derivedStateOf {
            (selectedLazyIndex - selectedSlot)
                .coerceIn(0, (items.lastIndex).coerceAtLeast(0))
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { listState.isScrollInProgress }
            .distinctUntilChanged()
            .filter { isScrolling -> !isScrolling }
            .collectLatest {
                if (items.isEmpty()) return@collectLatest

                val targetLazyIndex = (selectedSlot + selectedIndex)
                    .coerceIn(0, totalCount - 1)

                val targetFirstVisible = (targetLazyIndex - selectedSlot)
                    .coerceIn(0, totalCount - 1)

                if (listState.firstVisibleItemScrollOffset != 0 ||
                    listState.firstVisibleItemIndex != targetFirstVisible
                ) {
                    listState.animateScrollToItem(targetFirstVisible, 0)
                }

                if (items.isNotEmpty()) {
                    onSelected(selectedIndex, items[selectedIndex])
                }
            }
    }

    Box(
        modifier = modifier
            .height(viewportHeight)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight)
                .align(Alignment.TopStart)
                .offset(y = itemHeight * selectedSlot)
                .background(Color(0xFFF0F0F0))
                .zIndex(0f)
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
        ) {
            items(selectedSlot) {
                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth()
                )
            }

            itemsIndexed(items) { index, item ->
                val isSelected = index == selectedIndex

                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    itemContent(item, isSelected)
                }
            }

            items(bottomSpacers) {
                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth()
                )
            }
        }
    }

    LaunchedEffect(initialSelectedIndex) {
        if (items.isNotEmpty()) {
            val targetLazyIndex = selectedSlot + initialSelectedIndex.coerceIn(0, items.lastIndex)
            listState.scrollToItem((targetLazyIndex - selectedSlot).coerceIn(0, totalCount - 1))
        }
    }
}

