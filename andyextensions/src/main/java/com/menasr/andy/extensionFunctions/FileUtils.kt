@file:Suppress("unused")

package com.menasr.andy.extensionFunctions

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.menasr.andy.constantObjects.ConstantUtils
import java.io.*
import java.text.DecimalFormat
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import kotlin.math.log10
import kotlin.math.pow

/**
 * @param size pass size in bytes and it will calculate it,
 * Weather it is byte=B,Kilobyte = KB, Megabyte = MB, GigaByte=GB, TeraByte = TB
 */
fun getFileSize(size: Long): String {
    if (size <= 0)
        return ""
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
    return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups.toDouble())) + " " + units[digitGroups]
}

/**
 * Checks if external storage is available for read and write
 */
fun isExternalStorageWritable(): Boolean {
    val state = Environment.getExternalStorageState()
    return Environment.MEDIA_MOUNTED == state
}

/**
 * Checks if external storage is available to at least read
 */
fun isExternalStorageReadable(): Boolean {
    val state = Environment.getExternalStorageState()
    return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
}

/**
 * Get myme type of file
 *
 * @param url url of file
 * @return application/'fileType' like **application/pdf**
 */
fun getMimeType(url: String): String? {
    var type: String? = null
    val extension = MimeTypeMap.getFileExtensionFromUrl(url)
    if (extension != null) {
        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }
    return type
}

/**
 * Get the size of the directory in bytes format
 *
 * @param file file(folder) to calculate size
 */
fun getDirectorySize(file: File): Long {
    if (file.exists()) {
        var result: Long = 0
        val fileList = file.listFiles()
        for (aFileList in fileList!!) {
            result += if (aFileList.isDirectory) {
                getDirectorySize(aFileList)
            } else {
                aFileList.length()
            }
        }
        return result
    }
    return 0
}

/**
 * Clear/Delete all the contents in file/Directory
 *
 * @param file file/folder
 * @return true on successfull deletion of all content
 * **Make sure file it is not null**
 */
fun clearDirectory(file: File): Boolean {
    if (file.isDirectory)
        for (child in file.listFiles()!!)
            clearDirectory(child)
    return file.delete()
}


/**
 * Copy all the contents of one file/folder to file/folder of another
 *
 * @param file   this file content will be copied to
 * @param target target file/folder
 * @return boolean status on successfull completion
 */
@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun copy(file: File, target: File): Boolean {
    try {
        if (!target.parentFile.exists()) {
            if (!target.parentFile.mkdirs()) return false
        }

        val inputStream = FileInputStream(file)
        val outputStream = FileOutputStream(target)

        val buffer = ByteArray(ConstantUtils.KB)
        val read: Int = inputStream.read(buffer)
        while (read != -1) {
            outputStream.write(buffer, 0, read)
        }

        inputStream.close()
        outputStream.flush()
        outputStream.close()
        return true
    } catch (ignored: IOException) {
        logAll("Error parsing files")
    }

    return false
}

/**
 * Create zip of files
 *
 * @param files no. of files to make a zip
 * @param file  where it is stored
 * @return file/folder path on successfull process
 */
fun createZip(files: List<String>, file: File,bufferSize : Int = 2048): String? {
    try {
        var origin: BufferedInputStream
        val dest = FileOutputStream(file)
        val out = ZipOutputStream(BufferedOutputStream(
            dest))

        val data = ByteArray(bufferSize)
        for (i in files.indices) {
            val fi = FileInputStream(files[i])
            origin = BufferedInputStream(fi, bufferSize)

            val entry = ZipEntry(files[i].substring(
                files[i].lastIndexOf("/") + 1))
            out.putNextEntry(entry)
            val count: Int = origin.read(data, 0, bufferSize)

            while (count != -1) {
                out.write(data, 0, count)
            }
            origin.close()
        }

        out.close()
        return file.toString()
    } catch (ignored: Exception) {
        logAll("Error in creating zip")
    }

    return null
}

/**
 * Methot to get uri from file
 *
 * @param context       context of activity
 * @param applicationId id of application
 * @param file          file/folder
 * @return uri of file/folder
 */
fun getUriFromFile(context: Context, applicationId: String, file: File): Uri? {
    try {
        return FileProvider.getUriForFile(context, "$applicationId.fileProvider", file)
    } catch (ignored: IllegalArgumentException) {
    }

    return null
}

/**
 * Checks if [Environment].MEDIA_MOUNTED is returned by `getExternalStorageState()`
 * and therefore external storage is read- and writable.
 */
fun isExtStorageAvailable(): Boolean {
    return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
}


/**
 * Zip the files.
 *
 * @param srcFiles    The source of files.
 * @param zipFilePath The path of ZIP file.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun zipFiles(srcFiles: Collection<String>,
             zipFilePath: String): Boolean {
    return zipFiles(
        srcFiles,
        zipFilePath,
        null
    )
}

/**
 * Zip the files.
 *
 * @param srcFilePaths The paths of source files.
 * @param zipFilePath  The path of ZIP file.
 * @param comment      The comment.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun zipFiles(srcFilePaths: Collection<String>?,
             zipFilePath: String?,
             comment: String?): Boolean {
    if (srcFilePaths == null || zipFilePath == null) return false
    var zos: ZipOutputStream? = null
    try {
        zos = ZipOutputStream(FileOutputStream(zipFilePath))
        for (srcFile in srcFilePaths) {
            if (!createZipFile(
                    getFileByPath(srcFile)!!,
                    "",
                    zos,
                    comment
                )
            ) return false
        }
        return true
    } finally {
        if (zos != null) {
            zos.finish()
            zos.close()
        }
    }
}

/**
 * Zip the files.
 *
 * @param srcFiles The source of files.
 * @param zipFile  The ZIP file.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun zipFiles(srcFiles: Collection<File>, zipFile: File): Boolean {
    return zipFiles(srcFiles, zipFile, null)
}

/**
 * Zip the files.
 *
 * @param srcFiles The source of files.
 * @param zipFile  The ZIP file.
 * @param comment  The comment.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun zipFiles(srcFiles: Collection<File>?,
             zipFile: File?,
             comment: String?): Boolean {
    if (srcFiles == null || zipFile == null) return false
    var zos: ZipOutputStream? = null
    try {
        zos = ZipOutputStream(FileOutputStream(zipFile))
        for (srcFile in srcFiles) {
            if (!createZipFile(
                    srcFile,
                    "",
                    zos,
                    comment
                )
            ) return false
        }
        return true
    } finally {
        if (zos != null) {
            zos.finish()
            zos.close()
        }
    }
}

/**
 * Zip the file.
 *
 * @param srcFilePath The path of source file.
 * @param zipFilePath The path of ZIP file.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun createZipFile(srcFilePath: String,
                  zipFilePath: String): Boolean {
    return createZipFile(
        getFileByPath(
            srcFilePath
        ), getFileByPath(zipFilePath), null
    )
}

/**
 * Zip the file.
 *
 * @param srcFilePath The path of source file.
 * @param zipFilePath The path of ZIP file.
 * @param comment     The comment.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun createZipFile(srcFilePath: String,
                  zipFilePath: String,
                  comment: String): Boolean {
    return createZipFile(
        getFileByPath(
            srcFilePath
        ), getFileByPath(zipFilePath), comment
    )
}

/**
 * Zip the file.
 *
 * @param srcFile The source of file.
 * @param zipFile The ZIP file.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun createZipFile(srcFile: File,
                  zipFile: File): Boolean {
    return createZipFile(
        srcFile,
        zipFile,
        null
    )
}

/**
 * Zip the file.
 *
 * @param srcFile The source of file.
 * @param zipFile The ZIP file.
 * @param comment The comment.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun createZipFile(srcFile: File?,
                  zipFile: File?,
                  comment: String?): Boolean {
    if (srcFile == null || zipFile == null) return false
    var zos: ZipOutputStream? = null
    try {
        zos = ZipOutputStream(FileOutputStream(zipFile))
        return createZipFile(
            srcFile,
            "",
            zos,
            comment
        )
    } finally {
        zos?.close()
    }
}

@Throws(IOException::class)
private fun createZipFile(srcFile: File,
                          rootPath: String,
                          zos: ZipOutputStream,
                          comment: String?): Boolean {
    val modifiedPath = rootPath + (if (isSpace(
            rootPath
        )
    ) "" else File.separator) + srcFile.name
    if (srcFile.isDirectory) {
        val fileList = srcFile.listFiles()
        if (fileList == null || fileList.isEmpty()) {
            val entry = ZipEntry("$modifiedPath/")
            entry.comment = comment
            zos.putNextEntry(entry)
            zos.closeEntry()
        } else {
            for (file in fileList) {
                if (!createZipFile(
                        file,
                        modifiedPath,
                        zos,
                        comment
                    )
                ) return false
            }
        }
    } else {
        var `is`: InputStream? = null
        try {
            `is` = BufferedInputStream(FileInputStream(srcFile))
            val entry = ZipEntry(modifiedPath)
            entry.comment = comment
            zos.putNextEntry(entry)
            val buffer = ByteArray(ConstantUtils.BUFFER_LEN)
            val len: Int = `is`.read(buffer, 0, ConstantUtils.BUFFER_LEN)
            while (len != -1) {
                zos.write(buffer, 0, len)
            }
            zos.closeEntry()
        } finally {
            `is`?.close()
        }
    }
    return true
}

/**
 * Unzip the file.
 *
 * @param zipFilePath The path of ZIP file.
 * @param destDirPath The path of destination directory.
 * @return the unzipped files
 * @throws IOException if unzip unsuccessfully
 */
@Throws(IOException::class)
fun unzipFile(zipFilePath: String,
              destDirPath: String): List<File>? {
    return unzipFileByKeyword(
        zipFilePath,
        destDirPath,
        null
    )
}

/**
 * Unzip the file.
 *
 * @param zipFile The ZIP file.
 * @param destDir The destination directory.
 * @return the unzipped files
 * @throws IOException if unzip unsuccessfully
 */
@Throws(IOException::class)
fun unzipFile(zipFile: File,
              destDir: File): List<File>? {
    return unzipFileByKeyword(
        zipFile,
        destDir,
        null
    )
}

/**
 * Unzip the file by keyword.
 *
 * @param zipFilePath The path of ZIP file.
 * @param destDirPath The path of destination directory.
 * @param keyword     The keyboard.
 * @return the unzipped files
 * @throws IOException if unzip unsuccessfully
 */
@Throws(IOException::class)
fun unzipFileByKeyword(zipFilePath: String,
                       destDirPath: String,
                       keyword: String?): List<File>? {
    return unzipFileByKeyword(
        getFileByPath(zipFilePath),
        getFileByPath(destDirPath),
        keyword
    )
}

/**
 * Unzip the file by keyword.
 *
 * @param zipFile The ZIP file.
 * @param destDir The destination directory.
 * @param keyword The keyboard.
 * @return the unzipped files
 * @throws IOException if unzip unsuccessfully
 */
@Throws(IOException::class)
fun unzipFileByKeyword(zipFile: File?,
                       destDir: File?,
                       keyword: String?): List<File>? {
    if (zipFile == null || destDir == null) return null
    val files = ArrayList<File>()
    val zf = ZipFile(zipFile)
    val entries = zf.entries()
    if (isSpace(keyword)) {
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement() as ZipEntry
            val entryName = entry.name
            if (entryName.contains("../")) {
                logAll(
                    "ZipUtils",
                    "it's dangerous!"
                )
                return files
            }
            if (!unzipChildFile(
                    destDir,
                    files,
                    zf,
                    entry,
                    entryName
                )
            ) return files
        }
    } else {
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement() as ZipEntry
            val entryName = entry.name
            if (entryName.contains("../")) {
                logAll(
                    "ZipUtils",
                    "it's dangerous!"
                )
                return files
            }
            if (entryName.contains(keyword!!)) {
                if (!unzipChildFile(
                        destDir,
                        files,
                        zf,
                        entry,
                        entryName
                    )
                ) return files
            }
        }
    }
    return files
}

@Throws(IOException::class)
private fun unzipChildFile(destDir: File,
                           files: MutableList<File>,
                           zf: ZipFile,
                           entry: ZipEntry,
                           entryName: String): Boolean {
    val filePath = destDir.toString() + File.separator + entryName
    val file = File(filePath)
    files.add(file)
    if (entry.isDirectory) {
        if (!createOrExistsDir(file)) return false
    } else {
        if (!createOrExistsFile(file)) return false
        var `in`: InputStream? = null
        var out: OutputStream? = null
        try {
            `in` = BufferedInputStream(zf.getInputStream(entry))
            out = BufferedOutputStream(FileOutputStream(file))
            val buffer = ByteArray(ConstantUtils.BUFFER_LEN)
            val len: Int = `in`.read(buffer)
            while (len != -1) {
                out.write(buffer, 0, len)
            }
        } finally {
            `in`?.close()
            out?.close()
        }
    }
    return true
}

/**
 * Return the files' path in ZIP file.
 *
 * @param zipFilePath The path of ZIP file.
 * @return the files' path in ZIP file
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun getFilesPath(zipFilePath: String): List<String>? {
    return getFilesPath(
        getFileByPath(
            zipFilePath
        )
    )
}

/**
 * Return the files' path in ZIP file.
 *
 * @param zipFile The ZIP file.
 * @return the files' path in ZIP file
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun getFilesPath(zipFile: File?): List<String>? {
    if (zipFile == null) return null
    val paths = ArrayList<String>()
    val entries = ZipFile(zipFile).entries()
    while (entries.hasMoreElements()) {
        paths.add((entries.nextElement() as ZipEntry).name)
    }
    return paths
}

/**
 * Return the files' comment in ZIP file.
 *
 * @param zipFilePath The path of ZIP file.
 * @return the files' comment in ZIP file
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun getComments(zipFilePath: String): List<String>? {
    return getComments(
        getFileByPath(
            zipFilePath
        )
    )
}

/**
 * Return the files' comment in ZIP file.
 *
 * @param zipFile The ZIP file.
 * @return the files' comment in ZIP file
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun getComments(zipFile: File?): List<String>? {
    if (zipFile == null) return null
    val comments = ArrayList<String>()
    val entries = ZipFile(zipFile).entries()
    while (entries.hasMoreElements()) {
        val entry = entries.nextElement() as ZipEntry
        comments.add(entry.comment)
    }
    return comments
}

private fun createOrExistsDir(file: File?): Boolean {
    return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
}

private fun createOrExistsFile(file: File?): Boolean {
    if (file == null) return false
    if (file.exists()) return file.isFile
    if (!createOrExistsDir(file.parentFile)) return false
    return try {
        file.createNewFile()
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }

}

private fun getFileByPath(filePath: String): File? {
    return if (isSpace(filePath)) null else File(filePath)
}

private fun isSpace(s: String?): Boolean {
    if (s == null) return true
    var i = 0
    val len = s.length
    while (i < len) {
        if (!Character.isWhitespace(s[i])) {
            return false
        }
        ++i
    }
    return true
}