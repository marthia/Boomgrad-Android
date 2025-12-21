package me.marthia.app.boomgrad.presentation.attraction.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.JetSnackBackground
import me.marthia.app.boomgrad.presentation.components.JetsnackCard
import me.marthia.app.boomgrad.presentation.components.JetsnackScaffold
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.BaseTheme
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder

@Composable
fun AttractionList(onAttractionSelected: (Long) -> Unit) {
    JetsnackScaffold() {
        AttractionList(Modifier.padding(it), onAttractionSelected = onAttractionSelected)
    }
}


@Composable
fun AttractionList(modifier: Modifier = Modifier, onAttractionSelected: (Long) -> Unit) {

    JetSnackBackground(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(5) {
                AttractionListItem(onAttractionSelected = onAttractionSelected)
            }
        }
    }
}

@Composable
fun AttractionListItem(modifier: Modifier = Modifier, onAttractionSelected: (Long) -> Unit) {
    JetsnackCard(modifier = modifier.clickable {onAttractionSelected(-1)}, elevation = 0.dp, ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    "https://picsum.photos/300/200",
                    contentDescription = "profile",
                    placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder),
                    modifier = Modifier
                        .width(68.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop,
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("برج میلاد", style = MaterialTheme.typography.titleSmall, color = BaseTheme.colors.textSecondary)
                    Text("تهران، تهران", style = MaterialTheme.typography.labelSmall, color = BaseTheme.colors.textHelp)
                }
            }

            IconText(
                leadingIcon = {
                    Icon(painter = painterResource(R.drawable.icon_star_20), contentDescription = "score", tint = Color.Unspecified)
                },
                text = {
                    Text("4.8", style = MaterialTheme.typography.labelLarge, color = BaseTheme.colors.textSecondary)
                },
            )
        }
    }
}


@Preview("default", showBackground = true, showSystemUi = true)
//@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Preview("large font", fontScale = 2f)
@Composable
private fun PreviewCategory() {
    AppTheme {

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

            AttractionList(modifier = Modifier.systemBarsPadding() , {})
        }
    }
}