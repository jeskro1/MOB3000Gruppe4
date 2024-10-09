package no.usn.kvisli.listedemocompose

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedCard
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import no.usn.kvisli.listedemocompose.model.Kommune
import no.usn.kvisli.listedemocompose.ui.theme.ListeDemoComposeTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.json.JSONObject

class KommuneListeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var alleKommuner: ArrayList<Kommune> = Kommune.lagKommuneListe(resources)

        setContent {
            ListeDemoComposeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                    topBar = { KommuneTopAppBar()  }
                ) { innerPadding ->
                    KommuneListeDemo(
                        context  = this,
                        kommuner = alleKommuner,
                        modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KommuneTopAppBar()  {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text("Norske kommuner") },
        navigationIcon = {
            IconButton(onClick = { /*  */ }) {
                Icon(Icons.Filled.ArrowBack,
                     contentDescription = "Localized description"
                )
            }
        }
    )
}


@Composable
fun KommuneListeDemo(context: Context, kommuner: List<Kommune>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(kommuner) { kommune ->
            KommuneCard(context=context, kommune=kommune)
                /*
                 * SwipeToDimiss er foreløpig eksperimentell i MD3 og virker ikke ferdig.
                    val dismissState = rememberDismissState()
                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            val color by animateColorAsState(
                                when (dismissState.targetValue) {
                                    DismissValue.DismissedToEnd -> Color.Green
                                    DismissValue.DismissedToStart -> Color.Red
                                    else -> Color.LightGray
                                }
                            )
                            Box(Modifier.fillMaxSize().background(color))
                        },
                        dismissContent = {
                            KommuneCard(context, kommune, modifier)
                        }
                    )
                */
        }
    }
}

@Composable
fun KommuneCard(context: Context, kommune:Kommune, modifier: Modifier = Modifier) {
    OutlinedCard (
        modifier= modifier
            .fillMaxWidth()
            .padding(5.dp)
            // TO DO: Endre form på kortet
            //.clip(RoundedCornerShape(bottomStart = 20.dp, topEnd = 20.dp))
            .background(MaterialTheme.colorScheme.inversePrimary)
            .clickable(
                true,
                onClick = { visWikiSide(context, kommune.kommuneNavn) }
            )
    ) {
        // TO DO: Variabel for utvidelse av kortet
        // var erUtvidet by remember { mutableStateOf(false) }

        /*
        // Animert bakgrunnsfarge
        val kortFarge by animateColorAsState(
            targetValue =
                if (erUtvidet) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.inversePrimary
        )
        */

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.inversePrimary)
                // TO DO Legg til animering av bakgrunn
                // .background(color=kortFarge)
                /*
                // TODO: Animering av utvidelse
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                */
        ) {
            // Rad med kommunevåpen, kommunenummer, navn og fylke
            Row(modifier = Modifier.fillMaxWidth() )
            {
                KommuneVaapen(kommune, context, modifier)
                // Kolonne med tekstinformasjon
                Column {
                    Text(
                        text = "${kommune.kommuneNummer} ${kommune.kommuneNavn}",
                        modifier = Modifier.padding(2.dp),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "Fylke: ${kommune.fylke}",
                        modifier = Modifier.padding(2.dp),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                /*
                // TODO: Legg på knapp for utvidelse av listen
                Spacer(modifier = Modifier.weight(1f))
                KommuneItemButton(
                    erUtvidet = erUtvidet,
                    onClick = { erUtvidet = !erUtvidet }
                )

                 */
            }
            // TO DO: Kommunedetaljer vises bare når kortet utvides
            //if (erUtvidet)
                KommuneDetaljer(kommune)
        }
    }
}

@Composable
fun KommuneVaapen(kommune: Kommune, context: Context, modifier: Modifier = Modifier) {
    val bildeRes = context.resources.getIdentifier(kommune.kommuneVaapen,"drawable", context.packageName )
    // Vis bildet hvis det finnes i res/drawable
    if (bildeRes > 0)
        Image(
            painter = painterResource(bildeRes),
            modifier = Modifier
                .size(80.dp)
                .padding(5.dp)
                // Klipper til en liten form i MD3
                .clip(MaterialTheme.shapes.small),
            contentScale = ContentScale.Fit,
            contentDescription = "Kommunevåpen ${kommune.kommuneNavn}" // decorative element
        )
}

@Composable
private fun KommuneItemButton(
    erUtvidet: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector =
                if (erUtvidet) Icons.Filled.KeyboardArrowUp
                else Icons.Filled.KeyboardArrowDown,
            contentDescription =
            if (erUtvidet) "Lukk" else "Utvid",
            tint = MaterialTheme.colorScheme.secondary
        )

    }
}

@Composable
fun KommuneDetaljer( kommune: Kommune,  modifier: Modifier = Modifier
) {
    // Folketall og areal
    Column(modifier = modifier.fillMaxWidth().padding(5.dp) )  {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Folketall: ${kommune.folkeTall.toString()}",
                modifier = Modifier.padding(2.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Areal: ${kommune.areal} kvkm",
                modifier = Modifier.padding(2.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text(
            text = "Adm.senter: ${kommune.admSenter}",
            modifier = Modifier.padding(2.dp),
            // Bruker standard MD3 typerolle
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

fun visWikiSide(context: Context, kommunenavn: String) {
    val wikiURL = "https://no.wikipedia.org/wiki/" + kommunenavn.replace(" ", "_")
    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiURL))
    //if (webIntent.resolveActivity(context.packageManager) != null)
    context.startActivity(webIntent)
}

@Preview(showBackground = true)
@Composable
fun KommuneCardPreview() {
    ListeDemoComposeTheme {
        KommuneCard(
            context=LocalContext.current,
            kommune=Kommune(
                JSONObject(
                    "{\"Kommunenr\":\"0101\",\"Kommunenavn\":\"Halden\",\"Adm. senter\":\"Halden\",\"Fylke\":\"Viken\",\"Folketall\":30790,\"Areal\":642.34,\"Målform\":\"Bokmål\",\"Ordfører\":\"Thor Edquist\",\"Parti\":\"Høyre\",\"Kommunevaapen\":\"halden\"}"
                )
            )
        )
    }
}


