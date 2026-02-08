package me.marthia.app.boomgrad.presentation.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Picture
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.LocaleList
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
import android.view.Window
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.flowOf
import me.marthia.app.boomgrad.domain.model.Attraction
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.Locale

@SuppressLint("NewApi")
@Suppress("DEPRECATION")
fun Context.vibrator() =
    if (AndroidVersionUtils.atLeast(SDK.Android12)) {
        val vibratorManager =
            getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        getSystemService(VIBRATOR_SERVICE) as Vibrator
    }

val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Composable
fun KeepScreenOn(enabled: Boolean = true) {
    AndroidView({ View(it).apply { keepScreenOn = enabled } })
    LaunchedEffect(enabled) {
        Log.d("Keep-Screen-On", "Requested state = $enabled")
    }
}

@Composable
fun rememberMockLazyPagingItems(
    items: List<Attraction>
): LazyPagingItems<Attraction> {
    val mockPagingData = remember(items) {
        flowOf(PagingData.from(items))
    }
    return mockPagingData.collectAsLazyPagingItems()
}

//@Composable
//fun DoNotDim() {
//    val activity = LocalActivity.current
//
//    DisposableEffect(Unit) {
//        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//        onDispose {
//            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//        }
//    }
//}

/**
 * @param integerRes An integer resource ID.
 * @return The integer resource requested.
 * @brief Get an integer resource.
 */
fun Context.getInteger(@IntegerRes integerRes: Int) = resources.getInteger(integerRes)

@Stable
fun Modifier.autoMirrorOnRtl(rtl: Boolean = true): Modifier = composed {
    when (rtl) {
        false -> this
        true -> {
            if (LocalLayoutDirection.current == LayoutDirection.Rtl)
                this.scale(scaleX = -1f, scaleY = 1f)
            else
                this
        }
    }
}

fun String.shareExternal(): Intent {
    val dataToShare = this
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, dataToShare)
        type = "text/plain"
    }
    return Intent.createChooser(sendIntent, null)
}

fun File.shareExternal(): Intent {
    val dataToShare = this
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "video/mp4"
        putExtra(Intent.EXTRA_STREAM, Uri.fromFile(dataToShare))
    }
    return Intent.createChooser(sendIntent, null)
}

fun Uri.shareExternal(mimeType: String): Intent {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        type = mimeType
        putExtra(Intent.EXTRA_STREAM, this@shareExternal)
    }
    return Intent.createChooser(sendIntent, null)
}

fun Picture.toBitmap(): Bitmap {
    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        Bitmap.createBitmap(this)
    } else {
        val bitmap = Bitmap.createBitmap(
            this.width,
            this.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        canvas.drawColor(android.graphics.Color.WHITE)
        canvas.drawPicture(this)
        bitmap
    }
    return bitmap
}

fun Bitmap.shareExternal(context: Context): Intent {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "image/*"
        putExtra(Intent.EXTRA_STREAM, getImageUri(context))
    }
    return Intent.createChooser(sendIntent, null)
}

fun Bitmap.getImageUri(context: Context, title: String? = null): Uri {
    val bytes = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(
        context.contentResolver,
        this,
        title ?: "Temp image",
        null
    )
    return Uri.parse(path.toString())
}




fun Context.getFileName(uri: Uri): String {
    var result: String? = null
    try {
        if (uri.scheme == "content") {
            contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    result =
                        cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != -1) {
                result = cut?.plus(1)?.let { result?.substring(it) }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return result.orEmpty()
}

fun Context.getFileFromUrl(uri: Uri): File {
    var result: String? = null
    try {
        if (uri.scheme == "content") {
            contentResolver.query(
                uri,
                arrayOf(MediaStore.Video.Media.DATA),
                null,
                null,
                null
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    result =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return File(result.orEmpty())
}


/**
 * Bring the activity to the full screen.
 */
internal fun Activity.setFullScreen(fullscreen: Boolean) {
    window.setFullScreen(fullscreen)
}

/**
 * Bring the window to full screen. (Remove the status bar and navigation bar.)
 */
@Suppress("Deprecation")
internal fun Window.setFullScreen(fullscreen: Boolean) {
    if (fullscreen) {
        decorView.systemUiVisibility = (
                SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
    } else {
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}


@Composable
fun debugPlaceholder(@DrawableRes debugPreview: Int) = if (LocalInspectionMode.current) {
    painterResource(id = debugPreview)
} else {
    null
}