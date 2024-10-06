package com.pp.weatherandroid.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pp.weatherandroid.R
import com.pp.weatherandroid.ui.branding.WeatherAndroidTheme
import com.pp.weatherandroid.utils.DateUtil.toFormattedDay

@Composable
fun ForecastCard(
    modifier: Modifier = Modifier,
    date: String,
    icon: String,
    condition: String,
    minTemp: String,
    maxTemp: String,
) {
    ElevatedCard(
        modifier = modifier.padding(6.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 10.dp, end = 4.dp),
                text = date.toFormattedDay().orEmpty(),
                style = MaterialTheme.typography.titleMedium
            )
            AsyncImage(
                modifier = Modifier.size(42.dp),
                model = stringResource(R.string.icon_image_url, icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_placeholder),
                placeholder = painterResource(id = R.drawable.ic_placeholder),
            )
            Text(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                text = condition,
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Image(painter = painterResource(id = R.drawable.up_icon), contentDescription = null)
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = maxTemp,
                    style = MaterialTheme.typography.bodySmall,
                )
                Spacer(modifier = Modifier.width(10.dp))
                Image(painter = painterResource(id = R.drawable.down_icon), contentDescription = null)
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = minTemp,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ForecastCardPreview() {
    Surface {
        WeatherAndroidTheme {
            ForecastCard(
                date = "2024-10-05",
                icon = "sample.png",
                minTemp = "1",
                maxTemp = "10",
                condition = "Rainy and Snow"
            )
        }
    }
}