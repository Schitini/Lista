package com.example.guuh.exemplolista;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
private AnimationDrawable anima; //objeto do tipo animation
private AnimationUtils an01,an02; //objeto que terá as propriedades que utilizaremos para animar
public MediaPlayer player; //objeto do tipo player de musica
public SeekBar skb; // barra para setarmos o tempo
public int num = 0;
private CountDownTimer timer; //objeto do tipo timer
private boolean iniciouDevagar = false; // variável que diz ao timer que já foi setado a animação devagar
private ImageView img;
private Button btGira;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btGira = (Button)findViewById(R.id.btnGirar);
        skb = (SeekBar)findViewById(R.id.skbTempo);
        img = (ImageView)findViewById(R.id.imagemPiao);
        player = MediaPlayer.create(MainActivity.this,R.raw.jinsang);
        botao();
    }

    private void botao(){
        btGira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = skb.getProgress();//pega o tempo que foi colocado no SeekBar
                if (num > 30){
                    giraRapido();//chama o método gira rapido
                    tempo(num*1000);//em milissegundos
                }
                else{
                    giraDevagar();//chama o método gira devagar
                    tempo(num*1000);//em milissegundos
                }
            }
        });
    }
    private void tempo(int seg){
        timer = new CountDownTimer(seg,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                num = skb.getProgress();//pega o progresso atual e atribui na variável num
                num--; //decrementa um
                skb.setProgress(num);//seta o progresso atual conforme o decremento de num
                if(num<40 && iniciouDevagar==false){
                    giraDevagar();
                }
            }
            @Override
            public void onFinish() {
                anima.stop();
            }
        }.start();
    }
    private void giraRapido(){
        tocarSom();//chama o método para iniciar o som
        img.setBackgroundResource(R.drawable.giranumrapido);//seta a lista de imagens da animação
        anima = (AnimationDrawable)img.getBackground();//instancia o objeto animação
        anima.start();//inicia a animação
    }
    private void giraDevagar(){
        tocarSom();
        iniciouDevagar = true;
        img.setBackgroundResource(R.drawable.giranumdevagar);
        anima = (AnimationDrawable)img.getBackground();
        anima.start();
    }
    private void tocarSom(){
        try{
            if(player.isPlaying() && iniciouDevagar == false){
                //dependendo do tempo que você setou e estiver tocando o som
                player.release();//libera os recursos
                player = MediaPlayer.create(MainActivity.this,R.raw.jinsang);
                //Instancia o media player novamente
                iniciouDevagar = true;
                //seta a variável inicia devagar para true
            }
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    player.release();
                }
            });
            player.start();
        }
        catch (Exception e){
            player.release();
        }
    }
}
