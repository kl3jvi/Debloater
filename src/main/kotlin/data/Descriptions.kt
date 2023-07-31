package data

import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup

object Descriptions {
    suspend fun getDescription(packageId: String): String = coroutineScope {
        val url = "https://play.google.com/store/apps/details?id=$packageId"

        return@coroutineScope try {
            val document = Jsoup.connect(url).get()

            // Description can be found within <meta> tags, with itemprop "description"
            val metaDescription = document.select("meta[itemprop=description]")

            metaDescription.attr("content")
        } catch (exception: Exception) {
            // Log error message if needed
            "An error occurred while fetching the description: ${exception.message}"
        }
    }
}