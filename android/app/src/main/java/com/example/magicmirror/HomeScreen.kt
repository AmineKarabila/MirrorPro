import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import android.widget.ImageView
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.example.magicmirror.R

@Composable
fun HomeScreen(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(
            text = "Magic Mirror",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.padding(30.dp))
        Box(contentAlignment = Alignment.Center) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                val context = LocalContext.current
                // Erste Column für das erste GIF und den Button
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AndroidView(
                        modifier = Modifier
                            .size(130.dp, 130.dp), // Setzt die Größe der ImageView
                        factory = { ctx ->
                            ImageView(ctx).also { imageView ->
                                Glide.with(context)
                                    .load(R.drawable.calender) // Verwendet die direkte ResId
                                    .into(imageView)
                                imageView.setOnClickListener {
                                    navController.navigate("calendar")
                                }
                            }
                        }
                    )
                }

                // Zweite Column für das zweite GIF und den Button
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AndroidView(
                        modifier = Modifier
                            .size(130.dp, 130.dp), // Setzt die Größe der ImageView
                        factory = { ctx ->
                            ImageView(ctx).also { imageView ->
                                Glide.with(context)
                                    .load(R.drawable.youtube) // Verwendet die direkte ResId
                                    .into(imageView)
                                imageView.setOnClickListener {
                                    navController.navigate("youtube")
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
