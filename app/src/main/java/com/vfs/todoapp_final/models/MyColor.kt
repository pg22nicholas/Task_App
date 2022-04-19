package com.vfs.todoapp_final.models

import android.app.Application
import android.content.Context
import com.vfs.todoapp_final.R
import kotlinx.serialization.Serializable

/**
 * Deals with retrieving the colors for tasks and categories
 */
class MyColor {

    /**
     * Stores all possible Hex colors of the category
     */
    companion object {

        private lateinit var context : Application

        fun initiateColors(context : Application) {
            this.context = context
        }

        /**
         * Get the hex value of the category color as an int
         * @return  int color hex value
         */
        fun getCategoryHexColorInt(color : CategoryColors) : Int {

            return when(color) {
                CategoryColors.DEFAULT -> getColorInt(R.color.category_default)
                CategoryColors.RED -> getColorInt(R.color.category_red)
                CategoryColors.BLUE -> getColorInt(R.color.category_blue)
                CategoryColors.YELLOW -> getColorInt(R.color.category_yellow)
                CategoryColors.GREEN -> getColorInt(R.color.category_green)
            }
        }

        /**
         * Get the hex value of the priority color as an int
         * @return  int color hex value
         */
        fun getPriorityHexColorInt(color : PriorityColors) : Int {

            return when(color) {
                PriorityColors.NONE -> getColorInt(R.color.priority_none)
                PriorityColors.LOW -> getColorInt(R.color.priority_low)
                PriorityColors.MEDIUM -> getColorInt(R.color.priority_medium)
                PriorityColors.HIGH -> getColorInt(R.color.priority_high)
            }
        }

        /**
         * Converts the Hex int to a string representation
         * @param color         Color hex to convert
         * @return              String Hex representation
         */
        private fun getHexString(color : Int) : String {
            val hexString : String = Integer.toHexString(color)
            return "#" + hexString.replaceFirst("^ff", "")
        }

        /**
         * Get the color int from color resource id
         * @param resourceId    Color resource ID
         * @return              Color Hex int
         */
        private fun getColorInt(resourceId: Int) : Int {
            return context.resources.getColor(resourceId, context.theme)
        }
    }

    /**
     * Possible category colors
     */
    @Serializable
    enum class CategoryColors {
        DEFAULT,
        RED,
        BLUE,
        YELLOW,
        GREEN
    }

    /**
     * Possible category colors
     */
    @Serializable
    enum class PriorityColors {
        NONE,
        LOW,
        MEDIUM,
        HIGH
    }
}