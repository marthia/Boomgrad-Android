package me.marthia.app.boomgrad.data.remote.image

import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import coil.memory.MemoryCache
import me.marthia.app.boomgrad.R

class CoilFactory(
    private val context: Context,
    private val basePath: String
) : ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(context)
            .components {
                add(BaseUrlInterceptor(basePath))
                add(SvgDecoder.Factory())
            }
            // Let Coil handle memory automatically based on device RAM
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25) // 25% of available RAM
                    .build()
            }
            .crossfade(true)
            .allowHardware(true)
            .error(R.drawable.placeholder)
            .build()
    }
}