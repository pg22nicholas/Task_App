package com.vfs.todoapp_final.models

import android.content.Context
import java.io.*
import java.lang.StringBuilder

class FileStorage {

    companion object {
        val FILENAME = "tasks.json"

        /**
         * Read the contents from tasks json file
         */
        fun read(context: Context): String? {
            return try {
                val fis: FileInputStream = context.openFileInput(FILENAME)
                val isr = InputStreamReader(fis)
                val bufferedReader = BufferedReader(isr)
                val sb = StringBuilder()
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    sb.append(line)
                }
                sb.toString()
            } catch (fileNotFound: FileNotFoundException) {
                null
            } catch (ioException: IOException) {
                null
            }
        }

        /**
         * Create/override task json file
         */
        fun create(context: Context, jsonString: String?): Boolean {

            return try {
                val fos: FileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)
                if (jsonString != null) {
                    fos.write(jsonString.toByteArray())
                }
                fos.close()
                true
            } catch (fileNotFound: FileNotFoundException) {
                false
            } catch (ioException: IOException) {
                false
            }
        }

        /**
         * Check if the task json file exists
         */
        fun isFilePresent(context: Context): Boolean {
            val path: String = context.getFilesDir().getAbsolutePath().toString() + "/" + FILENAME
            val file = File(path)
            return file.exists()
        }

    }

}