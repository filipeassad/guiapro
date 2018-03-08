package dev.kosmo.com.br.activitys;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dev.kosmo.com.br.guiapro.R;

public class MainActivity extends Activity {

    private Button btnEntrar;
    private TextView tvCadastre;
    private TextView tvSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        tvCadastre = (TextView) findViewById(R.id.tvCadastre);
        tvSenha = (TextView) findViewById(R.id.tvSenha);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PrincipalActivity.class);
                startActivity(intent);
            }
        });

        tvCadastre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CadastroActivity.class);
                startActivity(intent);
            }
        });

        tvSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SenhaActivity.class);
                startActivity(intent);
            }
        });

    }
}
