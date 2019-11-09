package ni.devotion.micamara

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.flyingmanta.superbalertdialog.ClickListener
import com.flyingmanta.superbalertdialog.DialogTypes
import com.flyingmanta.superbalertdialog.LottieAlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {

    val PICK_IMAGE = 0
    val REQUEST_TAKE_PHOTO = 1
    val CAMERA_JSON = "camera.json"
    val FILE_PROVIDER = "ni.devotion.micamara"
    lateinit var imageUtils:ImageUtils
    lateinit var alertDialog: LottieAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        avatarIV.setOnClickListener {
            setupAlertDialog(CAMERA_JSON,"SELECTOR DE IMAGEN","OBTENER IMAGEN DE") }

    }

    private fun getUri(): Uri {
        val state = Environment.getExternalStorageState()
        return if (!state.equals(Environment.MEDIA_MOUNTED, ignoreCase = true)) MediaStore.Images.Media.INTERNAL_CONTENT_URI else MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }

    private fun uriToImageFile(uri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = this.contentResolver.query(uri, filePathColumn, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val filePath = cursor.getString(columnIndex)
                cursor.close()
                return File(filePath)
            }
            cursor.close()
        }
        return null
    }

    private fun uriToBitmap(uri: Uri): Bitmap {
        return MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        when (requestCode) {
            PICK_IMAGE-> if (resultCode == Activity.RESULT_OK) {
                val uri = imageReturnedIntent?.data
                uri?.let {
                    try {
                        val imageFile = uriToImageFile(it)
                        imageUtils = ImageUtils(applicationContext)
                        imageUtils.setBitmap(uriToBitmap(it))
                        avatarIV.setImageBitmap(imageUtils.getBitmapRotated(imageFile))
                    }catch (exception: NullPointerException){
                        val id = it.lastPathSegment!!.split(":")[1]
                        val imageColumns = arrayOf(MediaStore.Images.Media.DATA)
                        val imageOrderBy: String? = null
                        val uris = getUri()
                        val imageCursor = applicationContext!!.contentResolver.query(uris, imageColumns, MediaStore.Images.Media._ID + "=" + id, null, imageOrderBy)
                        when {
                            imageCursor!!.moveToFirst() -> {
                                val filePath = imageCursor!!.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA))
                                imageUtils = ImageUtils(applicationContext!!)
                                imageUtils.setBitmap(uriToBitmap(Uri.fromFile(File(filePath))))
                                avatarIV.setImageBitmap(imageUtils.getBitmapRotated(File(filePath)))
                            }
                            else -> {}
                        }
                    }
                }
            }
            REQUEST_TAKE_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                imageUtils.setBitmap(MediaStore.Images.Media.getBitmap(applicationContext!!.contentResolver, Uri.fromFile(imageUtils.photoFile)))
                avatarIV.setImageBitmap(imageUtils.getBitmapRotated())
            }
        }
    }

    private fun setupAlertDialog(customAsset: String, title: String, description: String){
        alertDialog = LottieAlertDialog.Builder(this, DialogTypes.TYPE_CUSTOM, customAsset)
            .setTitle(title)
            .setDescription(description)
            .setPositiveText("GALERIA")
            .setPositiveButtonColor(R.color.colorPrimary)
            .setPositiveListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {
                    dispatchPhotoPicker()
                    alertDialog.dismiss()
                }
            })
            .setNegativeText("CAMARA")
            .setNegativeButtonColor(R.color.colorAccent)
            .setNegativeListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {
                    dispatchTakePictureIntent()
                    alertDialog.dismiss()
                }
            })
            .build()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun dispatchPhotoPicker(){
        val getIntent = Intent(Intent.ACTION_GET_CONTENT).setType("image/*")
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).setType("image/*")
        val chooserIntent = Intent.createChooser(getIntent, "SELECCIONAR IMAGEN").putExtra(
            Intent.EXTRA_INITIAL_INTENTS, arrayOf<Intent>(pickIntent))
        startActivityForResult(chooserIntent, PICK_IMAGE)
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(this.packageManager) != null) {
            try {
                imageUtils = ImageUtils(this)
            }catch (ex: IOException) {}
            val photoURI = FileProvider.getUriForFile(this, FILE_PROVIDER, imageUtils.photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
        }
    }
}
