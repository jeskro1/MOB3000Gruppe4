package no.usn.kvisli.listedemocompose

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.usn.kvisli.listedemocompose.ui.theme.ListeDemoComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListeDemoComposeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                    topBar = { MainTopAppBar() },
                    bottomBar = {
                        Text(
                            modifier = Modifier.fillMaxSize(). padding(top=10.dp),
                            text="Bottom app bar",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        // TO DO: Implementer bottom app bar i stedet for Text()
                        // MainBottomAppBar()
                    }
                ) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar()
{
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text("Demo av lister med Compose") },
        navigationIcon = {
            IconButton(onClick = { /* "Open nav drawer" */ }) {
                Icon(imageVector=Icons.Filled.Menu,
                     contentDescription = "Tom meny"
                )
            }
        }
    )
}

@Composable
fun MainBottomAppBar()
{
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        content = { Text(
            modifier = Modifier.fillMaxSize(). padding(top=10.dp),
            text="Bottom app bar",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        ) },
    )
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        val localContext = LocalContext.current

        Spacer(modifier = Modifier.padding(10.dp, 20.dp))

        Button(
            modifier = Modifier.padding(5.dp),
            onClick = {
                localContext.startActivity(Intent(localContext, FylkeListeActivity::class.java))
            }
        ) {
            Text(
                text="Vis fylker",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Button(
            modifier = Modifier.padding(5.dp),
            onClick = {
                localContext.startActivity(Intent(localContext, KommuneListeActivity::class.java))
            }
        ) {
            Text(
                text="Vis kommuner",
                style = MaterialTheme.typography.headlineMedium
                //fontWeight = FontWeight.Bold
            )
        }

        Button(
            modifier = Modifier.padding(5.dp),
            enabled = false,
            onClick = {
                Toast.makeText(localContext, "Ikke implementert ennå.", Toast.LENGTH_LONG).show()
                //localContext.startActivity(Intent(localContext, BildeGridActivity::class.java))
            }
        ) {
            Text(
                text="Vis bilder",
                style = MaterialTheme.typography.headlineMedium
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ListeDemoComposeTheme {
        MainScreen()
    }
}