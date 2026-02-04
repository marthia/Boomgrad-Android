package me.marthia.app.boomgrad.presentation.tour.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.marthia.app.boomgrad.R
import me.marthia.app.boomgrad.presentation.components.IconText
import me.marthia.app.boomgrad.presentation.components.BackgroundElement
import me.marthia.app.boomgrad.presentation.components.CardElement
import me.marthia.app.boomgrad.presentation.components.TextFieldElement
import me.marthia.app.boomgrad.presentation.profile.component.dashedBorder
import me.marthia.app.boomgrad.presentation.theme.AppTheme
import me.marthia.app.boomgrad.presentation.theme.Theme
import me.marthia.app.boomgrad.presentation.util.debugPlaceholder

@Composable
fun CreateEditTourScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        CreateEditTour()
        Schedules()
        PriceAndCapacity()
        TourImages()
        MiscInfo()
    }
}

@Composable
fun CreateEditTour(modifier: Modifier = Modifier) {
    var cityDropDownExpanded by remember { mutableStateOf(false) }
    var tourTypeExpanded by remember { mutableStateOf(false) }
    var levelDropDownExpanded by remember { mutableStateOf(false) }
    var demographicExpanded by remember { mutableStateOf(false) }


    CardElement(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "اطلاعات پایه",
                style = MaterialTheme.typography.titleMedium,

            )

            TextFieldElement(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                label = {
                    Text(
                        text = "عنوان تور",
                        style = MaterialTheme.typography.bodyMedium,

                    )
                },
                placeholder = {
                    Text("عنوان مورد نظر خود را وارد کنید")
                },
                onValueChange = {},
            )

            TextFieldElement(
                modifier = Modifier.fillMaxWidth(), value = "",
                label = {
                    Text(
                        text = "زیر عنوان",
                        style = MaterialTheme.typography.bodyMedium,

                    )
                },
                placeholder = {
                    Text("متن مورد نظر خود را وارد کنید")
                },
                onValueChange = {},
            )

            TextFieldElement(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    },
                label = {
                    Text(
                        text = "شهر",
                        style = MaterialTheme.typography.bodyMedium,

                    )
                },
                value = "انتخاب کنید",
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = if (cityDropDownExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                },
                onValueChange = {},
            )

            TextFieldElement(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    },
                label = {
                    Text(
                        text = "نوع تور",
                        style = MaterialTheme.typography.bodyMedium,

                    )
                },
                value = "انتخاب کنید",
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = if (tourTypeExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                },
                onValueChange = {},
            )

            TextFieldElement(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                    },
                label = {
                    Text(
                        text = "سطح سختی",
                        style = MaterialTheme.typography.bodyMedium,

                    )
                },
                value = "انتخاب کنید",
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = if (levelDropDownExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                },
                onValueChange = {},
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                TextFieldElement(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {

                        },
                    label = {
                        Text(
                            text = "سطح سختی",
                            style = MaterialTheme.typography.bodyMedium,

                        )
                    },
                    value = "انتخاب کنید",
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = if (demographicExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    },
                    onValueChange = {},
                )

                TextFieldElement(
                    modifier = Modifier.weight(1f), value = "",
                    label = {
                        Text(
                            text = "مدت زمان",
                            style = MaterialTheme.typography.bodyMedium,

                        )
                    },
                    placeholder = {
                        Text("مقدار مورد نظر خود را وارد کنید")
                    },
                    onValueChange = {},
                )
            }

        }
    }

}

@Composable
fun Schedules() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "تاریخ (های) برگزاری تور",
            style = MaterialTheme.typography.labelLarge,

        )

        IconText(text = {
            Text(
                text = "افزودن تاریخ برگزاری",
                style = MaterialTheme.typography.titleMedium,
                color = Theme.colors.materialTheme.primary
            )
        }, leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_due_date_24),
                tint = Theme.colors.materialTheme.primary,
                contentDescription = "due date"
            )
        })
    }

    CardElement(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            TextFieldElement(
                modifier = Modifier.fillMaxWidth(), value = "",
                label = {
                    Text(
                        text = "تاریخ تور*",
                        style = MaterialTheme.typography.bodyMedium,

                    )
                },
                placeholder = {
                    Text("متن مورد نظر خود را وارد کنید")
                },
                onValueChange = {},
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_due_date_24),
                        tint = Theme.colors.materialTheme.primary,
                        contentDescription = "due date"
                    )
                }
            )

            TextFieldElement(
                modifier = Modifier.fillMaxWidth(), value = "",
                label = {
                    Text(
                        text = "زمان شروع*",
                        style = MaterialTheme.typography.bodyMedium,

                    )
                },
                placeholder = {
                    Text("متن مورد نظر خود را وارد کنید")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_due_time_24),
                        tint = Theme.colors.materialTheme.primary,
                        contentDescription = "due date"
                    )
                },
                onValueChange = {},
            )


        }

    }
}

@Composable
fun PriceAndCapacity() {
    CardElement(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "قیمت و ظرفیت",
                style = MaterialTheme.typography.titleMedium,

            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                TextFieldElement(
                    modifier = Modifier.weight(1f), value = "",
                    label = {
                        Text(
                            text = "قیمت (تومان)*",
                            style = MaterialTheme.typography.bodyMedium,

                        )
                    },
                    placeholder = {
                        Text("مقدار مورد نظر خود را وارد کنید")
                    },
                    onValueChange = {},
                )

                TextFieldElement(
                    modifier = Modifier.weight(1f), value = "",
                    label = {
                        Text(
                            text = "ظرفیت*",
                            style = MaterialTheme.typography.bodyMedium,

                        )
                    },
                    placeholder = {
                        Text("مقدار مورد نظر خود را وارد کنید")
                    },
                    onValueChange = {},
                )
            }

            TextFieldElement(
                modifier = Modifier.weight(1f), value = "",
                label = {
                    Text(
                        text = "سیاست لغو",
                        style = MaterialTheme.typography.bodyMedium,

                    )
                },
                placeholder = {
                    Text("مقدار مورد نظر خود را وارد کنید")
                },
                onValueChange = {},
            )

        }
    }
}

@Composable
fun TourImages() {
    CardElement(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "تصاویر تور",
                style = MaterialTheme.typography.titleMedium,

            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp)
                    .dashedBorder(
                        color = Theme.colors.materialTheme.primary,
                        shape = MaterialTheme.shapes.medium
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Icon(painter = painterResource(R.drawable.ic_anchor_upload_48), "Add new tour photo")
                Text(
                    text = "آپلود تصویر جدید",
                    style = MaterialTheme.typography.titleMedium,

                )

                Text(
                    text = "۲/۱۰ - حداکثر ۵ مگابایت",
                    style = MaterialTheme.typography.bodySmall,
                    color = Theme.colors.textHelp
                )
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(5) {
                    AsyncImage(
                        "https://picsum.photos/400",
                        contentDescription = "profile",
                        placeholder = debugPlaceholder(debugPreview = R.drawable.placeholder),
                        modifier = Modifier
                            .size(96.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop,
                    )
                }
            }


        }
    }
}

@Composable
fun MiscInfo(modifier: Modifier = Modifier) {
    CardElement(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "تصاویر تور",
                style = MaterialTheme.typography.titleMedium,

            )

            TextFieldElement(
                modifier = Modifier.fillMaxWidth(), value = "",
                label = {
                    Text(
                        text = "زیر عنوان",
                        style = MaterialTheme.typography.bodyMedium,

                    )
                },
                placeholder = {
                    Text("متن مورد نظر خود را وارد کنید")
                },
                onValueChange = {},
            )

            TextFieldElement(
                modifier = Modifier.fillMaxWidth(), value = "",
                label = {
                    Text(
                        text = "زیر عنوان",
                        style = MaterialTheme.typography.bodyMedium,

                    )
                },
                placeholder = {
                    Text("متن مورد نظر خود را وارد کنید")
                },
                onValueChange = {},
            )


        }
    }
}

@Preview("default", showBackground = true, showSystemUi = true)
@Composable
private fun PreviewCreateEditTour() {
    AppTheme {

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

            BackgroundElement(modifier = Modifier.fillMaxSize()) {
                CreateEditTourScreen()
            }
        }
    }
}