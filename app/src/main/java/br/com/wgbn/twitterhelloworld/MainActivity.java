package br.com.wgbn.twitterhelloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

public class MainActivity extends AppCompatActivity {

    // definição das chaves de acesso do twitter
    private static final String TWITTER_KEY = "GKk9lFvbl2xqVyB5nQyrUjr7k";
    private static final String TWITTER_SECRET = "NC85y0Onglf5o7LyEMkkKSMA4Wd8lL5na9MtuIB3TnmIDmQy55";
    private TwitterLoginButton loginButton;
    //private Button twiitar;
    //private TwitterSession sessao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // cria instancia de configuração do twitter
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);

        // adiciona a configuração ao framework
        Fabric.with(this, new Twitter(authConfig), new TweetComposer());

        setContentView(R.layout.activity_main);

        // botão de login do twitter, fornecido com o framework
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);

        // define a função de retorno do botão de login
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // retorna um objeto TwitterSession com a sessão do usuário
                // pode ser usada para futuras requisições à API
                // neste caso não salvei a sessão
            }

            @Override
            public void failure(TwitterException exception) {
                // tratamento de erro ao fazer login
            }
        });

        // botão para criar um novo tweet
        Button twiitar = (Button) findViewById(R.id.twtNovo);
        twiitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwitterSession session = Twitter.getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                Log.i("debug",token+" "+secret);

                // criar uma nova instancia do TweetComposer Builder
                TweetComposer.Builder builder = new TweetComposer.Builder(getApplicationContext())
                        .text("Aqui escrevemos um novo tweet!");
                builder.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // aqui a activity devolve o retorno da activity de autenticação para o TwitterButton tratar
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}
