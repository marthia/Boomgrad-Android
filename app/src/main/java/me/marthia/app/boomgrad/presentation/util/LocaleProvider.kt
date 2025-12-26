package me.marthia.app.boomgrad.presentation.util

import android.content.Context
import android.content.res.Configuration
import android.os.LocaleList
import java.util.Locale

object LocaleProvider {
    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
    private const val DEFAULT_LANGUAGE = "fa"

    fun onAttach(
        context: Context
    ): Context {
        val lang = getPersistedData(context)
        return setLocale(context, lang)
    }

    fun getLanguage(context: Context): String {
        return getPersistedData(context)
    }

    fun setLocale(context: Context, language: String): Context {
        persist(context, language)
        return updateResources(context, language)
    }

    private fun getPersistedData(context: Context): String {
        val preferences = context.getSharedPreferences("language", Context.MODE_PRIVATE)
        return preferences.getString(SELECTED_LANGUAGE, DEFAULT_LANGUAGE)!!
    }

    fun persist(context: Context, language: String) {
        val preferences = context.getSharedPreferences("language", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    private fun updateResources(
        context: Context,
        language: String,
    ): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val current = context.resources.configuration.locales.get(0)

        if (current == locale) return context

        val config = Configuration(context.resources.configuration)
        // bring the target locale to the front of the list
        val set = linkedSetOf(locale)

        val defaultLocales = LocaleList.getDefault()
        val all = List<Locale>(defaultLocales.size()) { defaultLocales[it] }
        // append other locales supported by the user
        set.addAll(all)

        config.setLocales(LocaleList(*set.toTypedArray()))
        config.setLayoutDirection(locale)
        // TODO it won't work without this line why?
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        return context.createConfigurationContext(config)
    }
}