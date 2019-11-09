package ni.devotion.micamara

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Environment
import android.os.StrictMode
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import kotlin.math.max

class ImageUtils {
    lateinit var photoFile: File
    lateinit var currentPhotoPath: String
    lateinit var path: String
    lateinit var exif: ExifInterface
    lateinit var matrix: Matrix
    lateinit var bitmaps: Bitmap

    val context: Context
    var fileName = "defaultUser"

    constructor(_context: Context){
        this.context = _context
        photoFile = createImageFile()
        exif = ExifInterface(photoFile.path)
    }

    constructor(_context: Context, _fileName: String){
        this.context = _context
        this.fileName = _fileName
        photoFile = createImageFile()
        exif = ExifInterface(photoFile.path)
    }

    constructor(_context: Context, photoTakeFile: File){
        this.context = _context
        photoFile = createImageFile()
        exif = ExifInterface(photoFile.path)
    }

    private fun createImageFile(): File {
        val imageFileName = "JPEG_${fileName}_"
        try{
            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val image = File.createTempFile(imageFileName,".jpg",storageDir)
            currentPhotoPath = image.absolutePath
            return image
        }catch(exception: IOException){
            throw exception
        }
    }

    fun getFileExtension(file: File): String {
        val name = file.name.substring( if (max(file.name.lastIndexOf('/'), file.name.lastIndexOf('\\')) < 0) 0 else max(file.name.lastIndexOf('/'), file.name.lastIndexOf('\\')) )
        val lastIndexOf = name.lastIndexOf(".")
        return if (lastIndexOf == -1) "" else name.substring(lastIndexOf + 1)
    }

    fun download(link: String) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        val imageFile = createImageFile()
        URL(link).openStream().use { input ->
            FileOutputStream(imageFile).use { output ->
                input.copyTo(output)
            }
        }
        photoFile = imageFile
    }

    fun setBitmap(_bitmap: Bitmap){
        this.bitmaps = _bitmap
    }

    fun getBitmapRotated(photoTakeFile: File? = null): Bitmap {
        photoTakeFile?.let {
            exif = ExifInterface(it.path)
            val rotation = exifToDegrees(getRotation())
            matrix = Matrix()
            if (rotation != 0) {
                matrix.preRotate(exifToDegrees().toFloat())
            }
            return Bitmap.createBitmap(bitmaps, 0,0,bitmaps.width, bitmaps.height, matrix, true)
        }.let {
            exif = ExifInterface(photoFile.path)
            val rotation = exifToDegrees(getRotation())
            if (rotation != 0) {
                matrix = Matrix()
                matrix.preRotate(exifToDegrees().toFloat())
            }
            return Bitmap.createBitmap(bitmaps, 0,0,bitmaps.width, bitmaps.height, matrix, true)
        }
    }

    fun getRotation() = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

    fun exifToDegrees(exifOrientation: Int) = when (exifOrientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> 90
        ExifInterface.ORIENTATION_ROTATE_180 -> 180
        ExifInterface.ORIENTATION_ROTATE_270 -> 270
        else -> 0
    }

    fun exifToDegrees() = when(getRotation()){
        ExifInterface.ORIENTATION_ROTATE_90 -> 90
        ExifInterface.ORIENTATION_ROTATE_180 -> 180
        ExifInterface.ORIENTATION_ROTATE_270 -> 270
        else -> 0
    }

}