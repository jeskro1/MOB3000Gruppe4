package no.usn.kvisli.listedemocompose

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import no.usn.kvisli.listedemocompose.ui.theme.ListeDemoComposeTheme

class FylkeListeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // Navn på norske fylker lest fra String-array i ressursfil
        val alleFylker  = resources.getStringArray(R.array.norske_fylker).asList()

        super.onCreate(savedInstanceState)
        setContent {
            ListeDemoComposeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                    topBar = {  FylkeTopAppBar()  }
                ) { innerPadding ->
                    ListeMedLøkke(context=this, fylkeTabell=alleFylker, modifier=Modifier.padding(innerPadding))
                    //ListeMedLazyColumn( context=this, fylkeTabell=fylkeTabell, modifier=Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FylkeTopAppBar()
{
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text("Norske fylker") },
        navigationIcon = {
            IconButton(onClick = { /* "Open nav drawer" */ }) {
                Icon(Icons.Filled.Menu, contentDescription = "Localized description")
            }
        }
    )
}

@Composable
fun ListeMedLøkke(context: Context, fylkeTabell: List<String>, modifier: Modifier = Modifier) {
    // Lager liste med "vanlig" Column() og en for-løkke.
    // Alle Compose-elementer i listen lages uavhengig av om de får plass på skjermen
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .verticalScroll(rememberScrollState())
    ) {
        for (fylke:String in fylkeTabell) {
            // Lag én rad i listen
            fylkeRad(context, fylke)
        }
    }
}

@Composable
fun ListeMedLazyColumn(context: Context, fylkeTabell: List<String>, modifier: Modifier = Modifier) {
    // Lager liste med LazyColumn() og en iterator.
    // LazyColumn lager nye Compose-elementer i listen etterhvert som de skal vises når brukeren ruller i listen
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        // Iterator som gjennomløper hele fylkeslisten.
        items(fylkeTabell) { fylke ->
            // Lag én rad i listen
            fylkeRad(context, fylke)
        }
    }
}

@Composable
fun fylkeRad(context: Context, fylke: String) {
    // Viser en rad med fylke-navn.
    Text(
        text = fylke,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
            .clickable(
                enabled=true,
                onClick = {
                    //visPopup(context, "Du har trykt på " + fylke)
                    visFylkeWeb(context, fylke)
                }
            ),
        color = Color.Blue,
        fontSize = 28.sp,
        maxLines = 1
    )
    Spacer(Modifier.fillMaxWidth().padding(2.dp) )
    HorizontalDivider(
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .width(1.dp)
    )
}

fun visPopup(context: Context, melding: String) {
    Toast.makeText(context, melding, Toast.LENGTH_SHORT).show()
}


fun visFylkeWeb(context: Context, fylke: String) {
    // Viser Wikipediaside for fylke i nettleser. URL lages basert på fylkets navn
    val wikiURL = "https://no.wikipedia.org/wiki/" + fylke.replace(" ", "_")
    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiURL))
    //if (webIntent.resolveActivity(context.packageManager) != null)
        context.startActivity(webIntent)
}


@Preview(showBackground = true)
@Composable
fun FylkelistePreview() {
    val previewTabell  = listOf("Fylke1","Fylke2","Fylke3")
    ListeDemoComposeTheme {
        ListeMedLøkke(LocalContext.current, previewTabell, Modifier.padding(5.dp))
    }
}