package medit.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

public class ImageFactory {
	public static int IMAGE_QUALITY = 95;
	public static String bitmapTobase64(Bitmap image) {
		Bitmap immagex = image;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		immagex.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
		return imageEncoded;
	}

	public static String drawableToBase64(Drawable image) {
		return ImageFactory
				.bitmapTobase64(ImageFactory.drawableToBitmap(image));
	}

	public static Bitmap bitmapFromBase64(String input) {
		byte[] decodedByte = Base64.decode(input, 0);
		return BitmapFactory
				.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		} else {

			Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);
			return bitmap;
		}
	}

	public static InputStream bitmapToInputStream(Bitmap bitmap) {
		int size = bitmap.getHeight() * bitmap.getRowBytes();
		ByteBuffer buffer = ByteBuffer.allocate(size);
		bitmap.copyPixelsToBuffer(buffer);
		return new ByteArrayInputStream(buffer.array());
	}

	public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
			boolean filter) {
		if(realImage.getWidth() > maxImageSize || realImage.getHeight() > maxImageSize)
		{
		float ratio = Math.min((float) maxImageSize / realImage.getWidth(),
				(float) maxImageSize / realImage.getHeight());
		int width = Math.round((float) ratio * realImage.getWidth());
		int height = Math.round((float) ratio * realImage.getHeight());

		Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width, height,
				filter);
		return newBitmap;
		} else return realImage;
	}

	public static Bitmap scaleBitmap(Bitmap bitmap, int wantedWidth,
			int wantedHeight) {
		Bitmap output = Bitmap.createBitmap(wantedWidth, wantedHeight,
				Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		Matrix m = new Matrix();
		m.setScale((float) wantedWidth / bitmap.getWidth(),
				(float) wantedHeight / bitmap.getHeight());
		canvas.drawBitmap(bitmap, m, new Paint());

		return output;
	}
}
