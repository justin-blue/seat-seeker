package com.example.seatseeker.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.domain.EventLocation
import com.example.seatseeker.SingleEventViewModel
import com.example.seatseeker.views.ErrorDialog
import com.example.seatseeker.views.ProgressIndicator
import java.net.URLEncoder


@Composable
fun SingleEventScreen(
    modifier: Modifier = Modifier,
    singleEventViewModel: SingleEventViewModel = hiltViewModel(),
    onNavigateToVenue: (url: String) -> Unit
) {

    val event by singleEventViewModel.eventsFlow.collectAsState()

    val state by singleEventViewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        singleEventViewModel.loadEvent()
    }

    Column(
        modifier = modifier
    )
    {
        when (state) {
            is SingleEventViewModel.State.Loading -> ProgressIndicator()
            is SingleEventViewModel.State.Error -> ErrorDialog(
                onDialogErrorClosed = {
                    singleEventViewModel.onErrorDialogClosed()
                }
            )

            else -> {
                //no-op
            }
        }

        event?.let {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(vertical = 16.dp),
                elevation = CardDefaults.cardElevation()
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = it.performers?.image,
                    contentDescription = "image",
                    contentScale = ContentScale.Crop
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
            Modifier
                .border(1.dp, Color.Gray)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                event?.performers?.let {
                    Text(
                        text = it.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                }
                event?.type?.let {
                    Text(
                        text = "Category: $it"
                    )
                }
                event?.venue?.nameV2?.let {
                    Text(
                        text = "Venue: $it"
                    )
                }
                event?.venue?.capacity?.let {
                    Text(
                        text = "Capacity: $it"
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Button(
                        onClick = ({
                            event?.venue?.url?.let {
                                onNavigateToVenue(URLEncoder.encode(it))
                            }
                        }),
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text("Buy tickets")
                    }
                    Button(
                        onClick = {
                            event?.venue?.location?.let {
                                goToViewLocation(context, it)
                            }
                        },
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(text = "View location")
                    }
                }
            }
        }

    }

}

private fun goToViewLocation(context: Context, location: EventLocation) {
    val uri = "geo:${location.lat},${location.lon}?z=15"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    context.startActivity(intent)
}

