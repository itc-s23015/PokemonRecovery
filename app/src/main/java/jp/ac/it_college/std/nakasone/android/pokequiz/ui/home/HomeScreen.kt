package jp.ac.it_college.std.nakasone.android.pokequiz.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.it_college.std.nakasone.android.pokequiz.R

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onStartClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(48.dp)
        ) {
            Text(
                text = stringResource(R.string.home_title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium
            )
            Button(
                onClick = onStartClick
            ) {
                Text(
                    text = stringResource(R.string.start),
                    style = MaterialTheme.typography.displaySmall
                )
            }
        }
    }

}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}